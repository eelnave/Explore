package edu.byui.cit.kindness;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;

abstract class LocationServices implements LocationListener {
	private FusedLocationProviderClient mFusedLocationProviderClient;
}



