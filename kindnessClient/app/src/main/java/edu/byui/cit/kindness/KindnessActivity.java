package edu.byui.cit.kindness;

import android.Manifest;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public final class KindnessActivity extends AppCompatActivity {
	public static final String TAG = "Kindness";

	private KindnessMap kindMap;
	private InfoFragment about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kindness_activity);

		ActivityCompat.requestPermissions(KindnessActivity.this, new String[] {
				Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

		if (savedInstanceState == null) {
			MainFragment mainFragment = new MainFragment();
			FragmentTransaction trans = getFragmentManager().beginTransaction();
			//this id will be whatever it is in the XML
			trans.add(R.id.fragContainer, mainFragment);
			trans.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
//		Log.v(TAG, getClass().getSimpleName() + ".onCreateOptionsMenu()");

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
			case R.id.about:
				if (about == null || about.isDetached()) {
					about = new About();
				}

				// Replace whatever is in the fragment_container
				// view with the About fragment.
				switchFragment(about);


				// Return true to indicate that this
				// method handled the item selected event.
				return true;
			case R.id.kind_map:
				startActivity(kindMap);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void switchFragment(InfoFragment fragment) {
		System.out.println(fragment.toString());
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		//this id will be whatever it is in the XML
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}

	private void startActivity(KindnessMap kindMap) {
	}

}
