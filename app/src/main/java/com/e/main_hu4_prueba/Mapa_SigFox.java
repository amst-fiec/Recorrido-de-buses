package com.e.main_hu4_prueba;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Mapa_SigFox extends FragmentActivity implements OnMapReadyCallback {
    private Button btncerrar;
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    public DatabaseReference db_reference;
    public double latVal;
    public double longVal;
    String latVal1 ;
    String longVal1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa__sig_fox);

        mAuth= FirebaseAuth.getInstance();
        btncerrar= (Button) findViewById(R.id.btncerrar);
        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Mapa_SigFox.this, init_app_as.class));
                finish();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear();
        UiSettings uiSettings= mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(Mapa_SigFox.this, "NO mMap.setMyLocationEnabled(true)", Toast.LENGTH_SHORT).show();
        }

        latVal=-2.1629;
        longVal=-79.9389;
        db_reference = FirebaseDatabase.getInstance().getReference().child("Dispotivo");
        db_reference.addValueEventListener(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                latVal1 = String.valueOf(snapshot.child("Latitud").getValue());
                longVal1 = String.valueOf(snapshot.child("Longitud").getValue());

            }
            if (latVal1.equals("null") || longVal1.equals("null")){
                latVal=-2.177533;
                longVal=-79.899504;
            }
            else if (latVal1.equals(" ") || longVal1.equals(" ")){
                latVal=-2.1629;
                longVal=-79.9389;
            }
            else{
                latVal=Double.parseDouble(latVal1);
                longVal=Double.parseDouble(longVal1);
            }}
            @Override
            public void onCancelled(DatabaseError error) { System.out.println(error.toException());
            } });


        LatLng ubicacion = new LatLng(latVal,longVal);
        CameraPosition cameraPosition = CameraPosition.builder().target(ubicacion)
                .zoom(18)
                .bearing(0)
                .tilt(45)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));


    }
}
