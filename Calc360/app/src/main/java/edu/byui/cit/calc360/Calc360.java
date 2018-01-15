package edu.byui.cit.calc360;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import java.lang.reflect.Field;

import edu.byui.cit.calculators.About;
import edu.byui.cit.calculators.FiveFunction;
import edu.byui.cit.units.World;


public final class Calc360 extends AppCompatActivity {
	public static final String
			TAG = "Calc360",

			// Keys to store the user preferences.
			KEY_SALES_TAX_RATE = "salesTaxRate",
			KEY_INCOME_TAX_RATE = "incomeTaxRate",
			KEY_ANGLE_UNITS = "angleUnits";

	private OmniFragment about;
	private OmniFragment fivefunc;
//	OnTouchListener swipeHandler;

	public Calc360() {
		super();
		Descriptors.initialize();
	}

	@Override
	protected void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);

//		Log.v(TAG, getClass().getSimpleName() + ".onCreateOptionsMenu()");
		setContentView(R.layout.calc360);

		Toolbar bar = findViewById(R.id.toolbar);
		setSupportActionBar(bar);
//		bar.setNavigationOnClickListener();
		ActionBar actBar = getSupportActionBar();
		actBar.setDisplayHomeAsUpEnabled(true);
//		actBar.setDisplayShowTitleEnabled(false);

		if (savedInstState == null) {
			// Create a fragment that contains all the folders
			// and place it as the first fragment in this activity.
			GroupFragment frag = new GroupFragment();
			frag.setDescripID(0);

			FragmentTransaction trans = getFragmentManager().beginTransaction();
			trans.add(R.id.fragContainer, frag);
			trans.commit();
		}

		// Initialize the physical properties and their units.
		World.getInstance().initialize(this);
	}

//	@Override
//	public void onStart() {
//		super.onStart();
//		Log.v(TAG, getClass().getSimpleName() + ".onStart()");
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		Log.v(TAG, getClass().getSimpleName() + ".onResume()");
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle savedInstState) {
//		super.onSaveInstanceState(savedInstState);
//		Log.v(TAG, getClass().getSimpleName() + ".onSaveInstanceState(" + (savedInstState == null ? "null" : savedInstState.size()) + ")");
//	}

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
				if (about == null || about.isDetached()) {
					about = new About();
					about.setDescripID(1);
				}

				// Replace whatever is in the fragment_container
				// view with the About fragment.
				switchFragment(about);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;
			case R.id.actFive:
				if (fivefunc == null || fivefunc.isDetached()) {
					fivefunc = new FiveFunction();
					fivefunc.setDescripID(1021);
				}
				switchFragment(fivefunc);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void switchFragment(OmniFragment fragment) {
		// Replace whatever is in the fragment_container view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment);
		trans.addToBackStack(null);
		trans.commit();
	}

//	@Override
//	public void onPause() {
//		super.onPause();
//		Log.v(TAG, getClass().getSimpleName() + ".onPause()");
//	}
//
//	@Override
//	public void onStop() {
//		super.onStop();
//		Log.v(TAG, getClass().getSimpleName() + ".onStop()");
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		Log.v(TAG, getClass().getSimpleName() + ".onDestroy()");
//	}


	// Uses reflection to get an id from R.id, R.array, R.plurals, etc.
	public static int getID(Class clss, String name)
		throws NoSuchFieldException, IllegalAccessException {
		Field field = clss.getDeclaredField(name);
		return field.getInt(null);
	}
}
