package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import edu.byui.cit.widget.CITFragment;


public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Maintenance";

	@Override
	protected void onCreate(Bundle savedInstState) {
		// Call onCreate in the parent class.
		super.onCreate(savedInstState);

		// Inflate the main_activity xml layout and set it as the content
		// view for this activity so that the user will see it.
		setContentView(R.layout.main_activity);

		// Setup the action bar at the top of the window.
		Toolbar bar = findViewById(R.id.toolbar);
		setSupportActionBar(bar);
//		bar.setNavigationOnClickListener();
		ActionBar actBar = getSupportActionBar();
		actBar.setDisplayHomeAsUpEnabled(true);
//		actBar.setDisplayShowTitleEnabled(false);

		if (savedInstState == null) {
			// Create a fragment that contains all the folders
			// and place it as the first fragment in this activity.
			CITFragment frag = new ChooseVehicle();

			FragmentTransaction trans = getFragmentManager()
					.beginTransaction();
			trans.add(R.id.fragContainer, frag);
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
//		Log.v(TAG, getClass().getSimpleName() + ".onOptionsItemSelected()");
		switch (item.getItemId()) {
			case android.R.id.home:
				// Respond to the user pressing the "back/up" button on the
				// action bar in the same way as if the user pressed the
				// left-facing triangle icon on the main android toolbar.
				onBackPressed();
				return true;
			case R.id.actAbout:
//				if (about == null || about.isDetached()) {
//					about = new About();
//					about.setDescrip(about.getPrefsPrefix());
//				}
//
//				// Replace whatever is in the fragContainer
//				// with the About fragment.
//				switchFragment(about);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	public void switchFragment(CITFragment fragment) {
		// Replace whatever is in the fragContainer view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
	}


	@Override
	public void onBackPressed() {
		// Hide the virtual keyboard.
		InputMethodManager imm = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(
				findViewById(android.R.id.content).getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

		super.onBackPressed();
	}
}
