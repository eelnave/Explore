package edu.byui.cit.kindness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class KindnessMap extends InfoFragment implements OnMapReadyCallback{

	View view;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}



	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {

		view = inflater.inflate(R.layout.map, container, false);

//		mapView = (MapView) mapView.findViewById(R.id.map);
//		if(mapView != null){
//			mapView.onCreate(null);
//			mapView.onResume();
//			mapView.getMapAsync(this);
//		}
		return view;
	}

	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		MapView mapView = (MapView) view.findViewById(R.id.map);
		if(mapView != null){
			mapView.onCreate(null);
			mapView.onResume();
			mapView.getMapAsync(this);
		}
	}


	@Override
	public void onMapReady(GoogleMap googleMap) {
		GoogleMap mMap = googleMap;
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		// Add a marker in Sydney and move the camera
		LatLng sydney = new LatLng(-34, 151);
		mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
		mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}


}
