package edu.byui.cit.explore;

import android.Manifest;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import edu.byui.cit.exception.LocationException;
import edu.byui.cit.exception.PermissionException;
import edu.byui.cit.exception.ProviderException;
import edu.byui.cit.exception.ServiceException;


/* TODO
 * 1. Copy the following xml layout files and their corresponding Java
 *    files from the kindnessClient app into this app:
 *    about_frag, display_frag, main_activity, mistake_frag
 *
 * 2. Copy the action_menu xml file from kindnessClient into this app.
 *    Remove the privacy item from the menu in this app but don't remove it
 *    from the kindnessClient.
 *
 * 3. Look at the Room database in the Record app. Implement a Room database
 *    with one table named Pin with these columns:
 *        iconName, latitude, longitude, timestamp, notes
 *
 * 4. Brainstorm and review a list of icons that users will choose from,
 *    including Christmas tree, firewood, bathroom, lookout, boulders,
 *    berries, fish, moose, antelope, elk, deer, wolf, bear, tent,
 *    hiking, bicycling, horse
 * 5. Look at the icons in kindnessClient. Choose an icon from logomakr.com
 *    for each icons in the list (see above). Create Android icons from the
 *    logomakr icons and import them into this app.
 * 6. Create this app in the Google play store:
 *    https://play.google.com/apps/publish
 */

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Explore";
	private Fragment fragHowTo, fragPrivacy, fragAbout, fragReport;
    private DrawerLayout mDrawerLayout;
	Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
		ActivityCompat.requestPermissions(this,
				new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
				1);
		Context ctx = getApplicationContext();

//		TextView textView = findViewById(R.id.text_view);

//		registerForContextMenu(textView);

		try {
			// Try to start the LocationTracker early so that the
			// current location will be ready for the MapFragment
			// to move the camera to the current location.
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.start(ctx);
		}
		catch (PermissionException | ServiceException | ProviderException | LocationException ex) {
			Log.e(MainActivity.TAG, "1: " + ex.getMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "1: " + ex.getMessage());
		}

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        //create context menu to add pin
//		button = (Button) findViewById(R.id.button);
        public void contextMenu{
            final PopupMenu popupMenu = new PopupMenu(MainActivity.this, button);
            popupMenu.getMenuInflater().inflate(R.menu.create_pin, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(MainActivity.this,"" + item.getTitle(),Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }

    // this creates the context menu with a title
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Choose your option");
    	getMenuInflater().inflate(R.menu.context_menu, menu);

//		can create context mnu options based on the id of what was selected
//		switch (v.getId)
	}

	// this method are the case statements of what will happen when an option is selected
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//    	switch (item.getItemId()) {
//			case R.id.Edit:
//				Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
//				// add edit stuff here
//				return true;
//			case R.id.Delete:
//				Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
//				// add delete stuff here
//				return true;
//			case R.id.Share:
//				Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
//				// add share stuff here
//				return true;
//				default:
//					return super.onContextItemSelected(item);
//		}
//		return super.onContextItemSelected(item);
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

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
			Log.e(MainActivity.TAG, "2: " + ex.getMessage());
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "2: " + ex.getMessage());
		}
			// Create the map fragment and place it
			// as the first fragment in this activity.
			Fragment frag = new DisplayFragment();
			FragmentTransaction trans = getSupportFragmentManager()
					.beginTransaction();
		trans.add(R.id.fragContainer, frag);
		trans.commit();
	}
}
