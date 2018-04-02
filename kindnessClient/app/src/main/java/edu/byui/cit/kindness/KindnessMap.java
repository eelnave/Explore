package edu.byui.cit.kindness;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class KindnessMap extends FragmentActivity implements OnMapReadyCallback{

	public GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}



	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		double lat = 0, lon = 0;
		//getApplicationContext();
		GPSTracker gps = new GPSTracker(getApplicationContext());
		Location loc = gps.getLocation();
		if (loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			Toast.makeText(getApplicationContext(), "LAT: " + lat + " Lon: " + lon, Toast.LENGTH_LONG).show();
		}

		LatLng current = new LatLng(lat, lon);
		mMap.addMarker(new MarkerOptions().position(current).icon(
				BitmapDescriptorFactory.fromResource(R.drawable.mapicon)));
		CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(current, 1);
		mMap.animateCamera(yourLocation);

	}


}
