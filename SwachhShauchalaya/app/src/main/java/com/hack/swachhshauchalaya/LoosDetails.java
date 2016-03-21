package com.hack.swachhshauchalaya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LoosDetails extends AppCompatActivity implements OnMapReadyCallback {
    Long cleanliness_factor;
    Long rating;
    Double lat;
    Double longl;
    Long current_status;
    String AClounge;
    String shower;
    String childcare;
    String disability_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loos_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.v("QWE", getIntent().getStringExtra("click"));
        Firebase myFirebaseRef = new Firebase("https://rbhack2016.firebaseio.com/-KDCfFeEVwm9-rtCnG7E");
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child(getIntent().getStringExtra("click"));
                cleanliness_factor = (Long) snapshot.child("cleanliness_factor").getValue();
                rating = (Long) snapshot.child("rating").getValue();
                lat = (Double) snapshot.child("lat").getValue();
                longl = (Double) snapshot.child("long").getValue();
                current_status = (Long) snapshot.child("current_status").getValue();
                ((TextView) findViewById(R.id.name)).setText("Name: " + getIntent().getStringExtra("click"));
                ((TextView) findViewById(R.id.address)).setText("Address: " + snapshot.child("address").getValue().toString());
                if (current_status == 0)
                    ((TextView) findViewById(R.id.cleanning)).setText("Cleanliness Level: Bad");
                else if (current_status == 1)
                    ((TextView) findViewById(R.id.cleanning)).setText("Cleanliness Level: Medium");
                else
                    ((TextView) findViewById(R.id.cleanning)).setText("Cleanliness Level: Good");

                ((TextView) findViewById(R.id.rating)).setText("Rating: " + rating);
                if (current_status == 0)
                    ((TextView) findViewById(R.id.status)).setText("Status: Closed");
                else if (current_status == 1)
                    ((TextView) findViewById(R.id.status)).setText("Status: Open");
                else
                    ((TextView) findViewById(R.id.status)).setText("Status: Under Maintenance");

                DataSnapshot snapshot1 = snapshot.child("facilities");
                Log.v("qwe" ,""+ snapshot1.child("AClounge").getValue());
                if (snapshot1.child("AClounge").getValue().toString().equalsIgnoreCase("yes"))
                    ((TextView) findViewById(R.id.ac)).setText("AC: Available");
                else if (snapshot1.child("AClounge").getValue().toString().equalsIgnoreCase("no"))
                    ((TextView) findViewById(R.id.ac)).setText("AC: Not Available");
                if (snapshot1.child("shower").getValue().toString().equalsIgnoreCase("yes"))
                    ((TextView) findViewById(R.id.shower)).setText("Shower: Available");
                else if (snapshot1.child("shower").getValue().toString().equalsIgnoreCase("no"))
                    ((TextView) findViewById(R.id.shower)).setText("Shower: Not Available");
                if (snapshot1.child("childcare").getValue().toString().equalsIgnoreCase("yes"))
                    ((TextView) findViewById(R.id.child)).setText("Child: Available");
                else if (snapshot1.child("childcare").getValue().toString().equalsIgnoreCase("no"))
                    ((TextView) findViewById(R.id.child)).setText("Child: Not Available");
                if (snapshot1.child("disability_enabled").getValue().toString().equalsIgnoreCase("yes"))
                    ((TextView) findViewById(R.id.diability)).setText("Disability: Available");
                else if (snapshot1.child("disability_enabled").getValue().toString().equalsIgnoreCase("no"))
                    ((TextView) findViewById(R.id.diability)).setText("Disability: Not Available");
                ((TextView) findViewById(R.id.clock)).setText("Time: 18Hrs");
                ((TextView) findViewById(R.id.seated)).setText("Seating: Available");
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat,longl))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,longl), 18f));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=" + lat + "," + longl));
                startActivity(intent);
            }
        });
    }
    private GoogleMap mMap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
