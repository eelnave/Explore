package edu.byui.cit.kindness;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public final class MainActivity extends AppCompatActivity {
	public static final String TAG = "Kindness";
	private static final String FIRST_TIME_KEY = "FirstTime";

	private CITFragment howto, about;


	@Override
	protected void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);
		setContentView(R.layout.main_activity);

		ActivityCompat.requestPermissions(MainActivity.this, new String[] {
				Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

		ActionBar actBar = getSupportActionBar();
		actBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstState == null) {
			MainFragment mainFragment = new MainFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.add(R.id.fragContainer, mainFragment);
			trans.commit();

			SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
			if (prefs.getBoolean(FIRST_TIME_KEY, true)) {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(FIRST_TIME_KEY, false);
				editor.apply();

				if (howto == null || howto.isDetached()) {
					howto = new HowTo();
				}
				switchFragment(howto);
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
			case R.id.howto:
				if (howto == null || howto.isDetached()) {
					howto = new HowTo();
				}

				// Replace whatever is in the fragment_container
				// view with the HowTo fragment.
				switchFragment(howto);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;
			case R.id.about:
				if (about == null || about.isDetached()) {
					about = new About();
				}
				switchFragment(about);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	final void switchFragment(CITFragment fragment) {
		System.out.println(fragment.toString());
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}
}
