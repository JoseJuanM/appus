package com.mis.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mis.myapplication.entity.Trip;

import java.util.ArrayList;
import java.util.List;

public class SelectedFlightsActivity extends AppCompatActivity {

    //Todas las vistas
    RecyclerView recyclerView;
    List<Trip> tripList = new ArrayList<>();
    List<Trip> tripListFiltered;
    TripsAdapter tripsAdapter;
    SwitchCompat switchColumns;
    TextView filter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights_available);

        switchColumns = findViewById(R.id.switchColumnas);
        filter = findViewById(R.id.Filter);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripsAdapter = new TripsAdapter(tripList,this);
        recyclerView.setAdapter(tripsAdapter);

        switchColumns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    recyclerView.setLayoutManager(new GridLayoutManager(SelectedFlightsActivity.this,2));
                }
                else{
                    recyclerView.setLayoutManager(new LinearLayoutManager(SelectedFlightsActivity.this));
                }
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectedFlightsActivity.this,FilterActivity.class),001);
            }
        });

        loadtrips();
    }

    private void loadtrips() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        //Loading Data From Firebase Database
        FirebaseDatabase.getInstance().getReference("Trips").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

                tripList.clear();

                for(DataSnapshot child:snapshot.getChildren())
                {
                    Trip trip=child.getValue(Trip.class);
                    if(trip.isSelected())
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
        if(requestCode == 001 && resultCode == RESULT_OK)
        {
            String startDate=data.getStringExtra("filter_startdate");
            String endDate=data.getStringExtra("filter_enddate");
            String price=data.getStringExtra("filter_priceMin");

            filter(startDate, endDate, price);
        }
    }

    private void filter(String startDate, String endDate, String price)
    {
        tripListFiltered = new ArrayList<>();
        for(Trip trip:tripList)
        {
            boolean shouldAdd = true;

            if(!startDate.isEmpty()){
                if(!trip.getStartDate().equals(startDate)){
                    shouldAdd=false;
                }
            }

            if(!endDate.isEmpty()){
                if(!trip.getEndDate().equals(endDate)){
                    shouldAdd=false;
                }
            }

            if(!price.isEmpty()){
                double Price=Double.parseDouble(price);

                if(trip.getPrice()<Price){
                    shouldAdd=false;
                }
            }

            if(shouldAdd)
                tripListFiltered.add(trip);
        }

        tripsAdapter=new TripsAdapter(tripListFiltered,this);
        recyclerView.setAdapter(tripsAdapter);
    }
}