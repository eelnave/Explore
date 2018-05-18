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
import android.util.Log;

import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;


final class LocationTracker implements LocationListener {
	private static LocationTracker singleton;

	static LocationTracker getInstance() {
		if (singleton == null) {
			singleton = new LocationTracker();
		}
		return singleton;
	}


	private final Criteria criteria;
	private LocationManager locMgr;
	private Location location;


	private LocationTracker() {
		criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(false);
//		criteria.setHorizontalAccuracy(Criteria.ACCURACY_LOW);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setSpeedRequired(false);
	}


	void start(Context context) {
		if (locMgr == null) {
			String permission = Manifest.permission.ACCESS_COARSE_LOCATION;
			//		String permission = Manifest.permission.ACCESS_FINE_LOCATION;
			if (ContextCompat.checkSelfPermission(context, permission) !=
					PackageManager.PERMISSION_GRANTED) {
				throw new PermissionException(
						"GPS permission " + permission + " not granted");
			}

			String service = Context.LOCATION_SERVICE;
			locMgr = (LocationManager)context.getSystemService(service);
			if (locMgr == null) {
				throw new ServiceException(service + " not found");
			}

			//		String provider = LocationManager.NETWORK_PROVIDER;
			//		String provider = LocationManager.GPS_PROVIDER;
			//		boolean enabled = locMgr.isProviderEnabled(provider);
			//		if (!enabled) {
			//			throw new ProviderException(provider + " is not enabled");
			//		}
			String provider = locMgr.getBestProvider(criteria, true);
			Log.i(KindnessActivity.TAG, "best provider " + provider);
			if (provider == null || provider.isEmpty()) {
				locMgr = null;
				throw new ProviderException("Location provider is not enabled");
			}

			location = locMgr.getLastKnownLocation(provider);
			if (location == null) {
				location = new Location(provider);
			}
			locMgr.requestLocationUpdates(provider, 5 * 60 * 1000, 500, this);
		}
	}


	synchronized Location getLocation() {
		return location;
	}


	@Override
	public synchronized void onLocationChanged(Location loc) {
		location = loc;
		Log.i(KindnessActivity.TAG, "location updated " + loc.getTime()
				+ ", " + loc.getLatitude() + ", " + loc.getLongitude());
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
			locMgr = null;
		}
	}
}
