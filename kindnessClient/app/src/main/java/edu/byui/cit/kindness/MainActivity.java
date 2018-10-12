package edu.byui.cit.kindness;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
 * 1. Add dropdown lists at the top of the map to filter by date
 * 		-Date filters should be: last hour, last 24 hours, last week, last month, last year, all time
 * 2. change icons to enum category in Report.java
 * 3. fix ChildFragment so when we return to map reports are still there
 */

//known issue: when you open a fragment (ReportedFragment, HowTo, Privacy)
//and return to DisplayFragment, the reports are no longer on the map. Something
//is wrong in the fragment lifecycle and with the indexes have a null pointer exception

public final class MainActivity extends AppCompatActivity {

	public static final String TAG = "KindnessTag";

	public static final String
			REPORTS_KEY = "reports",
			CATEGORIES_KEY = "categories";

	private Fragment fragHowTo, fragPrivacy, fragAbout, fragReport;


	public MainActivity() {
		super();
	}


	@Override
	protected void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);

		ActivityCompat.requestPermissions(this,
				new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
				1);
		Context ctx = getApplicationContext();
		try {
			// Try to start the LocationTracker early so that the
			// current location will be ready for the MapFragment
			// to move the camera to the current location.
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.start(ctx);
		}
		catch (PermissionException | ServiceException | ProviderException | LocationException ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}

		FirebaseApp.initializeApp(ctx);

		setContentView(R.layout.main_activity);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		FloatingActionButton fab = findViewById(R.id.fabAdd);
		fab.setOnClickListener(new ReportHandler());

		if (savedInstState == null) {
			// Create the map fragment and place it
			// as the first fragment in this activity.
			Fragment frag = new DisplayFragment();
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
			trans.add(R.id.fragContainer, frag);
			trans.commit();
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
	public void onStart() {
		super.onStart();
		try {
			// In case the LocationTracker was stopped in onStop, try to
			// start it again. If the LocationTracker was successfully
			// started in onCreate, this call will have not effect.
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.start(getApplicationContext());
		}
		catch (PermissionException | ServiceException | ProviderException | LocationException ex) {
			// Do nothing
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, ex.getLocalizedMessage());
		}
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
				if (fragPrivacy == null || fragPrivacy.isDetached()) {
					fragPrivacy = new PrivacyFragment();
				}
				switchFragment(fragPrivacy);
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


	private final class ReportHandler implements View.OnClickListener {
		@Override
		public void onClick(View view) {
			try {
				// Try to start the LocationTracker again. If it was
				// successfully started in onCreate, this call will
				// have no effect. If it wasn't successfully started
				// in onCreate and can't be started here, show an
				// AlertDialog and don't switch to the ReportFragment.
				LocationTracker tracker = LocationTracker.getInstance();
				tracker.start(getApplicationContext());

				if (fragReport == null || fragReport.isDetached()) {
					fragReport = new ReportFragment();
				}
				switchFragment(fragReport);
			}
			catch (PermissionException | ServiceException | ProviderException | LocationException ex) {
				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
				showAlertDialog(R.string.locationError, R.string.locationErrMsg);
			}
			catch (Exception ex) {
				Log.e(MainActivity.TAG, ex.getLocalizedMessage());
				showAlertDialog(R.string.locationError, R.string.unknownErrMsg);
			}
		}
	}

	private void showAlertDialog(int titleID, int messageID) {
		AlertDialog.Builder builder =
				new AlertDialog.Builder(MainActivity.this,
						android.R.style.Theme_Material_Dialog_Alert);
		builder.setTitle(titleID)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(messageID)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// When this button is clicked,
								// just close the dialog box.
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}


	@Override
	public void onStop() {
		try {
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.stop();
		}
		finally {
			super.onStop();
		}
	}


	final void switchFragment(Fragment fragment) {
		// Replace whatever is in the fragContainer view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans =
				getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
