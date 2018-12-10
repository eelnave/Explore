package edu.byui.cit.explore;

import android.Manifest;
import android.app.Activity;
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
import android.widget.*;

import com.google.android.gms.maps.model.Marker;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.PinDAO;


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
	private DrawerLayout mDrawerLayout;
	private DisplayFragment fragDisplay;
	private Fragment fragAbout, fragPinInfo;
	private Marker clickedMarker;

    public void setClickedMarker(Marker clickedMarker) {
        this.clickedMarker = clickedMarker;
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_activity);

		//This is the code that asks the user to allow location services for our application
		ActivityCompat.requestPermissions(this,
				new String[]{ Manifest.permission.ACCESS_FINE_LOCATION },
				1);
		Context ctx = getApplicationContext();

		try {
			// Try to start the LocationTracker early so that the
			// current location will be ready for the MapFragment
			// to move the camera to the current location.
			LocationTracker tracker = LocationTracker.getInstance();
			tracker.start(ctx);
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "1: " + ex.getMessage());
		}

		//This Method is declared below and contains the code necessary for adding a custom toolbar.
        //A custom toolbar is required for adding a hamburger icon to access the drawer.
		createToolbar();

		mDrawerLayout = findViewById(R.id.drawer_layout);
	}

    public void onStart() {
        super.onStart();
        try {
            // In case the LocationTracker was stopped in onStop, try to
            // start it again. If the LocationTracker was successfully
            // started in onCreate, this call will have not effect.
            LocationTracker tracker = LocationTracker.getInstance();
            tracker.start(getApplicationContext());

            // Create the map fragment and place it
            // as the first fragment in this activity.
            fragDisplay = new DisplayFragment();
            final FragmentTransaction trans = getSupportFragmentManager()
                    .beginTransaction();
            trans.add(R.id.fragContainer, fragDisplay);
            trans.commit();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(new HandleNavClick());
        }
        catch (Exception ex) {
            Log.e(MainActivity.TAG, "2: " + ex.getMessage());
        }
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

    // this creates the context menu with a title
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        //getMenuInflater().inflate(R.menu.drawer_view, menu);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    // this method are the case statements of what will happen when an option
    // is selected
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        boolean result = true;
        switch (item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
                if (fragPinInfo == null) {
                    fragPinInfo = new PinInfoFragment();
                }
                replaceCurrentFragmentWith(fragPinInfo);
                break;
            case R.id.delete:
                db().deletePin(clickedMarker.getPosition().latitude, clickedMarker.getPosition().longitude);
                fragDisplay.showAllPins();
                break;
            case R.id.directions:
                Toast.makeText(this, "Get Directions selected", Toast.LENGTH_SHORT).show();
                //  link to google direction
                break;
            default:
                result = super.onContextItemSelected(item);
                break;
        }
        View container = findViewById(R.id.fragContainer);
        unregisterForContextMenu(container);
        return result;
    }

    private PinDAO db(){
        Context ctx = getApplicationContext();
        AppDatabase db = AppDatabase.getInstance(ctx);
        PinDAO dao = db.getPinDAO();
        return dao;
    }

    private final class HandleNavClick
			implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case (R.id.nav_about): {
                        if (fragAbout == null) {
                            fragAbout = new AboutFragment();
                        }
                        replaceCurrentFragmentWith(fragAbout);
                        break;
                    }
                    case (R.id.nav_pin): {
                        if (fragPinInfo == null) {
                            fragPinInfo = new PinInfoFragment();
                        }
                        replaceCurrentFragmentWith(fragPinInfo);
                        break;
                    }
					case (R.id.delete_all):{
						db().clearTable();
						fragDisplay.showAllPins();
					}
                    case (R.id.return_home): {
                        if (fragDisplay == null) {
                            fragDisplay = new DisplayFragment();
                        }
                        replaceCurrentFragmentWith(fragDisplay);
                        break;
                    }
                }
                return true;
        }
    }

    //Toolbars are a tricky thing in android and I'll do my best to explain what I know.
    //The first line of this method finds the XML Element with the id of "toolbar" in our app
    //this happens to be in our main_activity.xml file. The second line sets that toolbar as
    //the toolbar in our app. This was only possible after we changed our app theme to a theme
    //that didn't contain a predetermined toolbar. This change is located at line 4 of styles.xml
    //The third line creates a new ActionBar object using the support action bar we just set.
    //If you're wondering what the difference between an ActionBar and a Toolbar is well so was I
    //this StackOverflow link should prove useful: https://stackoverflow.com/questions/44516512/what-is-exact-difference-between-appbar-toolbar-actionbar-and-when-to-use-th/44516767
    //The fourth line calls the ludicrously named method "setDisplayHomeAsUpEnabled." This method
    //takes a boolean and when set to true it adds a back button at the far left of the toolbar.
    //The idea is that if the user navigates from one page to another and another then this creates
    //a "tree" of visited pages. The default behavior of android is to have a home button that takes
    //the user back to the very top. Setting it as "up" enabled allows the user to go one page at a time
    //using the back button. We use this method so we can hijack the back button, turn it into
    //hamburger icon and add our drawer menu. The fifth line is what changes the icon.

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    //This class handles all fragment transactions for the app. Fragment transactions are what
	//allow us to dynamically swap views in and out of our main FrameLayout. Moving fragments
	//in and out of a single FrameLayout is much less computing power than many entirely new
	//activities being loaded each time. There's a tutorial that goes through the details of what
	//each line of this method does at https://developer.android.com/guide/components/fragments#java

	private void replaceCurrentFragmentWith(Fragment fragment){
		final FragmentTransaction swapper = getSupportFragmentManager()
				.beginTransaction();
		swapper.replace(R.id.fragContainer, fragment);
		swapper.addToBackStack(null);
		swapper.commit();
	}
}