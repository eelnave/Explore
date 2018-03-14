package edu.byui.cit.kindness;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class KindnessMap extends FragmentActivity implements OnMapReadyCallback {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
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
		GoogleMap mMap = googleMap;

		GPSTracker gps = new GPSTracker(getApplicationContext());
		Location loc = gps.getLocation();
		double lat = loc.getLatitude();
		double lon = loc.getLongitude();

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//		LatLng rexburg = new LatLng(lat, lon);
//		mMap.addMarker(new MarkerOptions().position(rexburg).title("Marker in Rexburg"));
//		mMap.moveCamera(CameraUpdateFactory.newLatLng(rexburg));
	}
}
