package edu.byui.cit.kindness;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;


public class GPSTracker implements LocationListener {
	private Context context;

	public GPSTracker(Context c) {
		context = c;
	}


	public Location getLocation() {
//		String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
		String permission = Manifest.permission.ACCESS_FINE_LOCATION;
		if (ContextCompat.checkSelfPermission(context, permission) !=
				PackageManager.PERMISSION_GRANTED) {
			throw new PermissionException("GPS permission not granted");
		}

		String service = Context.LOCATION_SERVICE;
		LocationManager locMgr = (LocationManager)
				context.getSystemService(service);
		if (locMgr == null) {
			throw new ServiceException(service + " not found");
		}

//		String provider = LocationManager.NETWORK_PROVIDER;
		String provider = LocationManager.GPS_PROVIDER;
		boolean enabled = locMgr.isProviderEnabled(provider);
		if (!enabled) {
			throw new ProviderException(provider + " is not enabled");
		}

		locMgr.requestLocationUpdates(provider, 6000, 500, this);
		Location loc = locMgr.getLastKnownLocation(provider);
		if (loc == null) {
			throw new LocationException("last location unknown");
		}
		return loc;
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
