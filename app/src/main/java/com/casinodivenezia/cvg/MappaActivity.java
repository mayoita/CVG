package com.casinodivenezia.cvg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MappaActivity  extends AppCompatActivity
        implements OnMapReadyCallback {
    static final LatLng CaNoghera = new LatLng(45.520532, 12.358032);
    static final LatLng CaVendramin = new LatLng(45.44284, 12.32988);
    private LatLngBounds.Builder builder;
    String office;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_mappa);

        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent i = getIntent();
        office = i.getStringExtra("Sede");
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        builder = new LatLngBounds.Builder();
        if (office.equals("CN")){
            Marker noghera = googleMap.addMarker(new MarkerOptions()
                    .position(CaNoghera)
                    .snippet("Ca'Noghera")
                    .title("Ca'Noghera")
                    .rotation((float) 180.0)

                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.canogherathumbnail2)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CaNoghera,15));
        } else if (office.equals("VE")){
            Marker vendramin = googleMap.addMarker(new MarkerOptions()
                    .position(CaVendramin)
                    .title("Ca' Vendramin Calergi")
                    .snippet("Ca'Vendramin Calergi")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.veneziathumbnail2)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CaVendramin,15));

        } else {
            Marker noghera = googleMap.addMarker(new MarkerOptions()
                    .position(CaNoghera)
                    .snippet("Ca'Noghera")
                    .title("Ca'Noghera")
                    .rotation((float) 180.0)

                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.canogherathumbnail2)));
            builder.include(noghera.getPosition());
            Marker vendramin = googleMap.addMarker(new MarkerOptions()
                    .position(CaVendramin)
                    .title("Ca' Vendramin Calergi")
                    .snippet("Ca'Vendramin Calergi")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.veneziathumbnail2)));
            builder.include(vendramin.getPosition());


            googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {

                @Override
                public void onCameraIdle() {
                    // Move camera.

                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
                    // Remove listener to prevent position reset on camera move.
                    googleMap.setOnCameraChangeListener(null);
                }

                // @Override
                //   public void onCameraChange(CameraPosition arg0) {
                // Move camera.
                //     googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));
                // Remove listener to prevent position reset on camera move.
                //     googleMap.setOnCameraChangeListener(null);
                // }
            });
        }
        return;
    }



}
