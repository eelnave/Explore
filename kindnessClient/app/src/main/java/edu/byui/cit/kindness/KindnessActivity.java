package edu.byui.cit.kindness;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.FirebaseApp;

import edu.byui.cit.exception.LocationException;
import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;

/* TODO:
 * 1. Add a privacy fragment that explains to the user, the data that Kindness collects.
 * 2. Rewrite the how to fragment so that it matches the latest changes to this app.
 * 3. Change the icon on the floating action button to be a plus symbol instead of an envelope.
 * 4. Create and add a different map icon for each category
 * 5. Change MapFragment.onMapReady to draw the correct map icon for each report instead of the heart icon.
 * 6. Modify ReportedFragment, ReportFragment, or MapFragment so that the app draws an icon for a report immediately after the user submits a report.
 * 6. Add dropdown lists at the top of the map to filter by category and date
 * 7. Date filters should be: last hour, last 24 hours, last week, last month, last year, all time
 * 8. Add missing strings to the translated strings.xml files
 */

public final class KindnessActivity extends AppCompatActivity {
	public static final String TAG = "Kindness";
	private static final String FIRST_TIME_KEY = "FirstTime";
	public static final String
			REPORTS_KEY = "reports",
			CATEGORIES_KEY = "categories";

	private Fragment fragHowTo, fragAbout, fragReport;


	@Override
	protected void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);
		setContentView(R.layout.kindness_activity);

		ActivityCompat.requestPermissions(this,
				new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
				1);
		FirebaseApp.initializeApp(this);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fabAdd);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (fragReport == null || fragReport.isDetached()) {
					fragReport = new ReportFragment();
				}
				switchFragment(fragReport);
			}
		});

		if (savedInstState == null) {
			// Create a fragment that contains all the vehicles
			// and place it as the first fragment in this activity.
			MapFragment fragMap = new MapFragment();
			FragmentTransaction trans =
					getSupportFragmentManager().beginTransaction();
			trans.add(R.id.fragContainer, fragMap);
			trans.commit();

			// If this is the first time that this app has
			// been run on the current device, open the
			// tutorial that explains how to use this app.
			SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
			if (prefs.getBoolean(FIRST_TIME_KEY, true)) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(FIRST_TIME_KEY, false);
				editor.apply();

				if (fragHowTo == null || fragHowTo.isDetached()) {
					fragHowTo = new HowToFragment();
				}
				switchFragment(fragHowTo);
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Inflate our menu from the resources by using the menu inflater.
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case android.R.id.home:
				// Respond to the user pressing the "back/up" button on the
				// action bar in the same way as if the user pressed the
				// left-facing triangle icon on the main android toolbar.
				onBackPressed();
				return true;
			case R.id.actHowTo:
				if (fragHowTo == null || fragHowTo.isDetached()) {
					fragHowTo = new HowToFragment();
				}

				// Replace whatever is in the fragment container
				// view with the HowTo fragment.
				switchFragment(fragHowTo);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;

			case R.id.actPrivacy:
				return true;

			case R.id.actAbout:
				if (fragAbout == null || fragAbout.isDetached()) {
					fragAbout = new AboutFragment();
				}
				switchFragment(fragAbout);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onResume() {
		try {
			super.onResume();
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.start(this);
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
		catch (Exception ex) {
			Log.e(KindnessActivity.TAG, ex.getLocalizedMessage());
		}
	}

	@Override
	public void onPause() {
		try {
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.stop();
		}
		finally {
			super.onPause();
		}
	}


	final void switchFragment(Fragment fragment) {
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans =
				getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
