package edu.byui.cit.kindness;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;

import java.util.zip.Inflater;


public class KindnessMap extends FragmentActivity implements OnMapReadyCallback{

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
//	View view;

//	@Override
//	public void onCreate(Bundle savedInstanceState){
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	protected View createView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstState) {
//		View view = inflater.inflate(R.layout.map, container, false);
//		return view;
//	}


//	public void onViewCreated(View view, Bundle savedInstanceState){
//		super.onViewCreated(view, savedInstanceState);
//		MapView mapView = (MapView) view.findViewById(R.id.map);
//		if(mapView != null){
//			mapView.onCreate(null);
//			mapView.onResume();
//			mapView.getMapAsync(this);
//		}
//	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}


}
