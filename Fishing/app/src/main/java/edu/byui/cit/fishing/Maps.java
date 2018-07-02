package edu.byui.cit.fishing;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,

     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng LowerHenry = new LatLng(43.872358, -111.865320);
        mMap.addMarker(new MarkerOptions().position(LowerHenry).title("Marker in Lower Henry's Fork"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LowerHenry));

        LatLng UpperHenry = new LatLng(43.950704, -111.708949);
        mMap.addMarker(new MarkerOptions().position(UpperHenry).title("Marker in Upper Henry's Fork"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UpperHenry));

        LatLng SnakeRiver = new LatLng(43.596371, -111.478234);
        mMap.addMarker(new MarkerOptions().position(SnakeRiver).title("Marker in Snake River"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SnakeRiver));

        LatLng TetonRiver = new LatLng(43.883224, -111.792983);
        mMap.addMarker(new MarkerOptions().position(TetonRiver).title("Marker in Teton River"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TetonRiver));

        LatLng WarmRiver = new LatLng(44.114769, -111.323809);
        mMap.addMarker(new MarkerOptions().position(WarmRiver).title("Marker in Warm River"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(WarmRiver));

        LatLng FallRiver = new LatLng(44.065085, -111.210307);
        mMap.addMarker(new MarkerOptions().position(FallRiver).title("Marker in Fall River"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(FallRiver));

    }
}
