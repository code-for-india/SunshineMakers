package com.hack.swachhshauchalaya;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class FindLoosMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    LatLng gurgon = new LatLng(28.4700, 77.0300);
    LatLng punjab = new LatLng(30.7900, 76.7800);
    LatLng bihar = new LatLng(25.3700, 85.1300);
    LatLng lucknow = new LatLng(26.8000, 80.9000);
    List<String> location = new ArrayList<String>();
    List<LatLng> latLong = new ArrayList<LatLng>();
    List<String> ratingArray = new ArrayList<String>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_loos_map);
        Firebase myFirebaseRef = new Firebase("https://rbhack2016.firebaseio.com/-KDCfFeEVwm9-rtCnG7E");
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Long rating = (Long) snapshot.child("rating").getValue();
                    ratingArray.add("Rating: " + rating);
                    String name = (String) snapshot.getKey();
                    location.add(name);
                    latLong.add(new LatLng((Double) snapshot.child("lat").getValue(),
                            (Double) snapshot.child("long").getValue()));
                }
                for (int i = 0; i < location.size(); i++) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latLong.get(i))
                            .title(location.get(i))
                            .snippet(ratingArray.get(i))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.toilet))).setTitle(location.get(i));

                }
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong.get(0), 12f));
                mMap.setOnInfoWindowClickListener(FindLoosMapActivity.this);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mMap.setMyLocationEnabled(true);
            return;
        }


    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, LoosDetails.class);
        intent.putExtra("click",marker.getTitle());
        startActivity(intent);
    }
}
