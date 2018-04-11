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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;




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

		FirebaseDatabase database = FirebaseDatabase.getInstance();
		DatabaseReference myRef = database.getReference("report");

		// Read from the database
		myRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				GenericTypeIndicator<HashMap<String, Report>> t = new GenericTypeIndicator<HashMap<String, Report>>() {};
				HashMap<String, Report> reports = dataSnapshot.getValue(t);

				for(HashMap.Entry<String, Report> entry : reports.entrySet()) {
					Report value = entry.getValue();
					mMap.addMarker(new MarkerOptions().position(new LatLng(value.getLatitude(), value.getLongitude())).icon(
							BitmapDescriptorFactory.fromResource(R.drawable.mapicon)));
				}
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
			}
		});

		// Add a marker in Sydney and move the camera
		/*LatLng sydney = new LatLng(-34, 151);
		MarkerOptions mk = new MarkerOptions();
		mk.position(sydney).title("Sydney!");
		mk.icon(BitmapDescriptorFactory.fromResource(R.drawable.money));
		mMap.addMarker(mk);/*

		/*mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
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
		CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(current, 1);
		mMap.animateCamera(yourLocation);
	}


}
