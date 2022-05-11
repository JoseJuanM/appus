
package com.mis.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mis.myapplication.entity.Trip;
import com.squareup.picasso.Picasso;

public class TripDetailActivity extends AppCompatActivity {

    Trip trip;
    TextView title, textViewPrice, textViewDepart, textViewArrival, latitude, longitude;
    ImageView image;
    ImageButton imgButton;
    Button btnBuy, btnMap;
    //private Double ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);

        Fragment fragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

        trip = (Trip) getIntent().getSerializableExtra("Trip");

        title = findViewById(R.id.title);
        textViewPrice = findViewById(R.id.txtPrecio);
        textViewDepart = findViewById(R.id.textViewDepart);
        textViewArrival = findViewById(R.id.textViewArrival);
        image = findViewById(R.id.image);
        imgButton = findViewById(R.id.imgButton);
        btnBuy = findViewById(R.id.buyTicket);
        btnMap = findViewById(R.id.btnMap);
        longitude = findViewById(R.id.txtLongitude);
        latitude = findViewById(R.id.txtLatitude);
        title.setText(trip.getTitle());
        textViewPrice.setText(trip.getPrice() + "€");
        textViewDepart.setText(trip.getStartDate());
        textViewArrival.setText(trip.getEndDate());
        latitude.setText(trip.getLatitude());
        longitude.setText(trip.getLongitude());

        //Loading Image Of Trip
        if(!trip.getImageLink().isEmpty())
            Picasso.get().load(trip.getImageLink()).placeholder(R.drawable.ic_placeholder).into(image);


        if(trip.isSelected()){
            imgButton.setImageResource(R.drawable.ic_baseline_star_24);
        }
        else{
            imgButton.setImageResource(R.drawable.ic_star);
        }

        btnBuy.setText("Comprar vuelo ( "+trip.getPrice()+" € )");

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TripDetailActivity.this, login_activity.class));
            }
        });

        btnMap.setOnClickListener(view -> {
            startActivity(new Intent(TripDetailActivity.this, MapActivity.class));
        });
    }



    public String getLatitude() {
        return latitude.getText().toString();
    }

    public String getLongitude(){
        return longitude.getText().toString();
    }
}