package edu.byui.cit.kindness;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


public class GPSTracker implements LocationListener{
	private Context context;
	public GPSTracker(Context c){
		context = c;
	}


	public Location getLocation(){
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
			Toast.makeText(context, "Permission not granted",Toast.LENGTH_SHORT).show();
			return null;
		}
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if(isGPSEnabled){
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 500, this);
			Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			return l;
		} else {
			Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
		}
		return null;
	}



	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {

	}

	@Override
	public void onProviderEnabled(String s) {

	}

	@Override
	public void onProviderDisabled(String s) {

	}
}
