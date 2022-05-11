package com.mis.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mis.myapplication.entity.Trip;

import java.util.ArrayList;
import java.util.List;

public class FlightsAvailableActivity extends AppCompatActivity {

    //Todas las vistas
    RecyclerView recyclerView;
    List<Trip> tripList=new ArrayList<>();
    List<Trip> tripListFiltered;
    TripsAdapter tripsAdapter;
    SwitchCompat switchColumnas;
    TextView Filter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_available);

        //inicializando vistas
        switchColumnas=findViewById(R.id.switchColumnas);
        Filter=findViewById(R.id.Filter);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //declaraing adapter for recyclerview
        tripsAdapter=new TripsAdapter(tripList,this);

        //setting adapter on recyclerview
        recyclerView.setAdapter(tripsAdapter);


        //adding check change listener for switch
        switchColumnas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked)
                {
                    recyclerView.setLayoutManager(new GridLayoutManager(FlightsAvailableActivity.this,2));
                }
                else
                {
                    recyclerView.setLayoutManager(new LinearLayoutManager(FlightsAvailableActivity.this));
                }
            }
        });

        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(FlightsAvailableActivity.this,FilterActivity.class),001);
            }
        });

        loadtrips();
    }

    private void loadtrips() {
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                tripList.clear();

                for(DataSnapshot child:snapshot.getChildren())
                {
                    Trip trip=child.getValue(Trip.class);
                    tripList.add(trip);
                }
                tripsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==001 && resultCode==RESULT_OK)
        {
            String startDate=data.getStringExtra("filter_startdate");
            String endDate=data.getStringExtra("filter_enddate");
            String price=data.getStringExtra("filter_priceMin");

            filter(startDate,endDate,price);
        }
    }

    private void filter(String startDate, String endDate, String price)
    {

        tripListFiltered = new ArrayList<>();
        for(Trip trip:tripList)
        {
            boolean flightAvailable=true;

            if(!startDate.isEmpty())
            {
                if(!trip.getStartDate().equals(startDate))
                {
                    flightAvailable=false;
                }
            }
            if(!endDate.isEmpty())
            {
                if(!trip.getEndDate().equals(endDate))
                {
                    flightAvailable=false;
                }
            }
            if(!price.isEmpty())
            {
                double _price = Double.parseDouble(price);
                if(trip.getPrice()<_price)
                {
                    flightAvailable = false;
                }
            }
            if(flightAvailable)
                tripListFiltered.add(trip);

        }

        tripsAdapter = new TripsAdapter(tripListFiltered,this);
        recyclerView.setAdapter(tripsAdapter);

    }
}