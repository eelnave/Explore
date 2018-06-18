package edu.byui.cit.kindness;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.TreeMap;

import edu.byui.cit.exception.LocationException;


public final class MapFragment extends SupportMapFragment
		 {


//	@Override
//	public void onStart() {
//		super.onStart();
//		Activity act = getActivity();
//		act.setTitle(getString(R.string.appName));
//
//		if (mMap == null) {
//			// Get notified when the map is ready to be used.
//			this.getMapAsync(this);
//		}
//	}



}
