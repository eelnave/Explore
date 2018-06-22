package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
//import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import edu.byui.cit.widget.CITFragment;

//import android.arch.persistence.room.Room;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Maintenance";

	private ChooseVehicle fragChoose;
	private AddVehicle fragAdd;
	private About fragAbout;
	private Help fragHelp;
	private MaintenanceFrag fragAct;


// create variable FloatingActionButton
	private FloatingActionButton fab;


	@Override
	protected void onCreate(Bundle savedInstState) {
		super.onCreate(savedInstState);
		setContentView(R.layout.main_activity);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actBar = getSupportActionBar();
		actBar.setDisplayHomeAsUpEnabled(true);

		if (savedInstState == null) {
			// Create a fragment that contains all the vehicles
			// and place it as the first fragment in this maintenance_activity.
			fragChoose = new ChooseVehicle();
			FragmentTransaction trans =
					getFragmentManager().beginTransaction();
			trans.add(R.id.fragContainer, fragChoose);
			trans.commit();
		}

		//create floating action button and set on click listener
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, AddVehicle.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items
		// to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent maintenance_activity in AndroidManifest.xml.
		switch (item.getItemId()) {
			case android.R.id.home:
				// Respond to the user pressing the "back/up" button on the
				// action bar in the same way as if the user pressed the
				// left-facing triangle icon on the main android toolbar.
				onBackPressed();
				return true;
			case R.id.actAdd:
				if (fragAdd == null || fragAdd.isDetached()) {
					fragAdd = new AddVehicle();
				}

				// Replace whatever is in the
				// fragContainer with the Add fragment.
				switchFragment(fragAdd);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;

			case R.id.actHelp:
                if (fragHelp == null || fragHelp.isDetached()) {
                    fragHelp = new Help();
                }
                switchFragment(fragHelp);

				return true;

			case R.id.actAbout:
				if (fragAbout == null || fragAbout.isDetached()) {
					fragAbout = new About();
				}
				switchFragment(fragAbout);
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
