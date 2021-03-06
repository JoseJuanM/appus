package com.mis.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TripDetailActivity act = (TripDetailActivity) getActivity();
        String latitude = act.getLatitude();
        String longitude = act.getLongitude();

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
//                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(@NonNull LatLng latLng) {
                        LatLng latLng1 = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        //LatLng latLng2 = new LatLng(Double.parseDouble(longitude), Double.parseDouble(latitude));

                        MarkerOptions markerOptions = new MarkerOptions();
                        //MarkerOptions markerOptionsCurrent = new MarkerOptions();
                        //markerOptions.position()
                        markerOptions.position(latLng1);
                        markerOptions.title(latLng1.latitude + " : " + latLng1.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 10));
                        googleMap.addMarker(markerOptions);
//                    }
//                });
            }
        });
        return view;
    }
}