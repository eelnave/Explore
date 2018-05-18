package edu.byui.cit.kindness;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.SupportActivity;
import android.util.Log;

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

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;


public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
	private GoogleMap mMap;


	public MapFragment() {
		super();
		// Get notified when the map is ready to be used.
		this.getMapAsync(this);
	}


	@Override
	public void onResume() {
		super.onResume();
		SupportActivity act = getActivity();
		act.setTitle(getString(R.string.appName));
	}


	/**
	 * Manipulates the map once available.
	 * This callback is triggered when the map is ready to be used.
	 * This is where we can add markers or lines, add listeners or move the
	 * camera. In this case,
	 * we just add a marker near Sydney, Australia.
	 * If Google Play services is not installed on the device, the user will
	 * be prompted to install
	 * it inside the SupportMapFragment. This method will only be triggered
	 * once the user has
	 * installed Google Play services and returned to the app.
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		try {
			mMap = googleMap;

			FirebaseDatabase database = FirebaseDatabase.getInstance();
			DatabaseReference myRef = database.getReference(
					KindnessActivity.REPORTS_KEY);

			// Read from the database
			myRef.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					GenericTypeIndicator<HashMap<String, Report>> type =
							new GenericTypeIndicator<HashMap<String, Report>>() {

							};
					HashMap<String, Report> reports = dataSnapshot.getValue(
							type);
					if (reports != null) {
						for (Report value : reports.values()) {
							MarkerOptions opts = new MarkerOptions();
							opts.position(new LatLng(value.getLatitude(),
									value.getLongitude()));
							opts.icon(BitmapDescriptorFactory.fromResource(
									R.drawable.mapicon));
							mMap.addMarker(opts);
						}
					}
				}

				@Override
				public void onCancelled(DatabaseError error) {
					// Failed to read value
					Log.e(KindnessActivity.TAG,
							"DB error: " + error.toString());
				}
			});

			// Move the camera to the user's location.
			Context ctx = getActivity().getApplicationContext();
			GPSTracker gps = new GPSTracker(ctx);
			Location loc = gps.getLocation();
			LatLng latlng = new LatLng(loc.getLatitude(), loc.getLongitude());
			mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
		}
		catch (PermissionException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
		catch (ServiceException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
		catch (ProviderException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
		catch (LocationException ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
	}
}
