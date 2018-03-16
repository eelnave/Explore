package edu.byui.cit.kindness;

import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public final class MainActivity extends AppCompatActivity {
	public static final String TAG = "Kindness";

	private KindnessMap kindMap;
	private InfoFragment about;
	private Button[] icon = new Button[6];
	private int[] btnIds = {R.id.emotional, R.id.food, R.id.labor, R.id.travel, R.id.money, };




	// Get the location and Category when a user clicks on of the categories


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ActivityCompat.requestPermissions(MainActivity.this, new String[]{
				Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
		for(int i = 0; i < 6; i++){
			icon[i] = findViewById(btnIds[i]);
			icon[i].setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					GPSTracker gps = new GPSTracker(getApplicationContext());
					Location loc = gps.getLocation();
					if(loc != null){
						double lat = loc.getLatitude();
						double lon = loc.getLongitude();

						Toast.makeText(getApplicationContext(), "LAT: " + lat + " Lon: " + lon, Toast.LENGTH_LONG).show();
					}
				}
			});
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

	private void switchFragment(InfoFragment about) {
	}

	private void startActivity(KindnessMap kindMap) {
	}

}
