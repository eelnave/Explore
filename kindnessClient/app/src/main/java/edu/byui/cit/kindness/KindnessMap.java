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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class KindnessMap extends FragmentActivity implements OnMapReadyCallback{
//	private DatabaseReference kindnessDB;
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
		FirebaseDatabase.getInstance().getReference().child("report").addListenerForSingleValueEvent(

				new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot dataSnapshot) {


						GenericTypeIndicator<List<Report>> t = new GenericTypeIndicator<List<Report>>() {};
						List<Report> reports = dataSnapshot.getValue(t);
						for(int i = 0; i < reports.size(); i++) {
							System.out.println(reports.get(i).getLatitude());
							System.out.println(reports.get(i).getLongitude());

							LatLng current = new LatLng(reports.get(i).getLatitude(), reports.get(i).getLongitude());
							mMap.addMarker(new MarkerOptions().position(current).icon(
									BitmapDescriptorFactory.fromResource(R.drawable.mapicon)));
						}
						CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(new LatLng(reports.get(reports.size()-1).getLatitude(), reports.get(reports.size()-1).getLongitude()), 1);
						mMap.animateCamera(yourLocation);
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {

					}
				});
		double lat = 0, lon = 0;
		//getApplicationContext();
		GPSTracker gps = new GPSTracker(getApplicationContext());
		Location loc = gps.getLocation();
		if (loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			Toast.makeText(getApplicationContext(), "LAT: " + lat + " Lon: " + lon, Toast.LENGTH_LONG).show();
		}

//		LatLng current = new LatLng(lat, lon);
//		mMap.addMarker(new MarkerOptions().position(current).icon(
//				BitmapDescriptorFactory.fromResource(R.drawable.mapicon)));



	}


}
