package edu.byui.cit.kindness;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;


final class LocationTracker implements LocationListener {
	private static final int INTERVAL = 15 * 60 * 1000;  // 15 minutes
	private static final float DISTANCE = 500;  // 500 meters

	private static LocationTracker singleton;

	static LocationTracker getInstance() {
		if (singleton == null) {
			singleton = new LocationTracker();
		}
		return singleton;
	}


	private final Criteria criteria;
	private LocationManager locMgr;
	private boolean started;
	private Location location;


	private LocationTracker() {
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
	}


	void start(Context context) {
		String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
		//		String permission = Manifest.permission.ACCESS_FINE_LOCATION;
		if (ContextCompat.checkSelfPermission(context, permission) !=
				PackageManager.PERMISSION_GRANTED) {
			throw new PermissionException(
					"Location permission " + permission + " not granted");
		}

		if (locMgr == null) {
			String service = Context.LOCATION_SERVICE;
			locMgr = (LocationManager)context.getSystemService(service);
			if (locMgr == null) {
				throw new ServiceException(service + " not found");
			}
		}

		if (!started) {
			//		String provider = LocationManager.NETWORK_PROVIDER;
			//		String provider = LocationManager.GPS_PROVIDER;
			//		boolean enabled = locMgr.isProviderEnabled(provider);
			//		if (!enabled) {
			//			throw new ProviderException(provider + " is not enabled");
			//		}
			String provider = locMgr.getBestProvider(criteria, true);
			if (provider == null || provider.isEmpty()) {
				locMgr = null;
				throw new ProviderException("Location provider is not enabled");
			}

			locMgr.requestLocationUpdates(provider, INTERVAL, DISTANCE, this);
			started = true;

			location = locMgr.getLastKnownLocation(provider);
		}
	}


	Location getLocation(Context context) {
		if (locMgr == null || !started || location == null) {
			start(context);
		}
		if (location == null) {
			throw new LocationException("Last location unknown");
		}
		return location;
	}


	@Override
	public void onLocationChanged(Location loc) {
		location = loc;
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


	void stop() {
		if (locMgr != null) {
			locMgr.removeUpdates(this);
		}
		started = false;
		locMgr = null;
	}
}
