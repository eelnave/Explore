package edu.byui.cit.calc360;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import edu.byui.cit.calculators.About;
import edu.byui.cit.calculators.FiveFunction;
import edu.byui.cit.units.World;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.WidgetWrapper;


public final class Calc360 extends AppCompatActivity {
	public static final String TAG = "Calc360";

	// Keys to store the user preferences.
	private static final String KEY_PREFIX = TAG;
	public static final String
			KEY_VERSION_CODE = KEY_PREFIX + ".versionCode",
			KEY_ANGLE_UNITS = KEY_PREFIX + ".angleUnits",
			KEY_SHOW_HELP = ".showHelp";
	private static final int MIN_PREFS_VERSION = 41;

	private LinearLayout layExplain;
	private TextWrapper txtExplain;
	private FrameLayout fragContain;

	private CITFragment about;
	private CITFragment fiveFunc;
//	OnTouchListener swipeHandler;

	public Calc360() {
		super();
		Descriptors.initialize();
	}


	@Override
	protected void onCreate(Bundle savedInstState) {
//		Log.v(TAG, getClass().getSimpleName() + ".onCreate("
//				+ savedInstState.size() + ")");
		super.onCreate(savedInstState);

		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		if (prefs.getInt(KEY_VERSION_CODE, 0) < MIN_PREFS_VERSION) {
			// Clean the user preferences file.
			SharedPreferences.Editor editor = prefs.edit();
			editor.clear();
			editor.putInt(KEY_VERSION_CODE, MIN_PREFS_VERSION);
			editor.apply();
		}

		setContentView(R.layout.calc360);

		Toolbar bar = findViewById(R.id.toolbar);
		setSupportActionBar(bar);
//		bar.setNavigationOnClickListener();
		ActionBar actBar = getSupportActionBar();
		actBar.setDisplayHomeAsUpEnabled(true);
//		actBar.setDisplayShowTitleEnabled(false);

		layExplain = findViewById(R.id.layExplain);
		txtExplain = new TextWrapper(layExplain, R.id.txtExplain);
		fragContain = findViewById(R.id.fragContainer);
		new ButtonWrapper(layExplain, R.id.btnHide, new HideHandler());

		if (savedInstState == null) {
			// Create a fragment that contains all the folders
			// and place it as the first fragment in this activity.
			GroupFragment frag = new GroupFragment();
			frag.setDescriptor(R.id.Calc360);

			FragmentTransaction trans =
					getFragmentManager().beginTransaction();
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
//		Log.v(TAG, getClass().getSimpleName() + ".onSaveInstanceState(" +
// (savedInstState == null ? "null" : savedInstState.size()) + ")");
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
			case R.id.actFive:
				if (fiveFunc == null || fiveFunc.isDetached()) {
					fiveFunc = new FiveFunction();
					fiveFunc.setDescriptor(R.id.FiveFunction);
				}
				switchFragment(fiveFunc);
				return true;
			case R.id.actExplain:
				int calcID = fragContain.getChildAt(0).getId();
				Descriptor descrip = Descriptors.getDescriptor(calcID);
				Resources res = getResources();
				String explain = descrip.getExplanation(res);
				if (explain != null) {
					String key = descrip.getPrefsPrefix(res) + KEY_SHOW_HELP;
					SharedPreferences prefs =
							getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = prefs.edit();
					editor.putBoolean(key, true);
					editor.apply();

					showExplanation(explain);
				}
				return true;
			case R.id.actAbout:
				if (about == null || about.isDetached()) {
					about = new About();
					about.setDescriptor(R.id.About);
				}

				// Replace whatever is in the fragment_container
				// view with the About fragment.
				switchFragment(about);

				// Return true to indicate that this
				// method handled the item selected event.
				return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/** Handles a click on the hide help (Got it!) button. */
	private class HideHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			hideExplanation();

			int calcID = fragContain.getChildAt(0).getId();
			Descriptor descrip = Descriptors.getDescriptor(calcID);
			String key = descrip.getPrefsPrefix(getResources()) + KEY_SHOW_HELP;
			SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putBoolean(key, false);
			editor.apply();
		}
	}

	final void showExplanation(String explain) {
		txtExplain.setText(explain);
		layExplain.setVisibility(View.VISIBLE);
	}

	final void hideExplanation() {
		layExplain.setVisibility(View.GONE);
	}


	final void switchFragment(CITFragment fragment) {
		// Replace whatever is in the fragment_container view with
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

//	@Override
//	public void onPause() {
//		Log.v(TAG, getClass().getSimpleName() + ".onPause()");
//		super.onPause();
//	}
//
//	@Override
//	public void onStop() {
//		Log.v(TAG, getClass().getSimpleName() + ".onStop()");
//		super.onStop();
//	}
//
//	@Override
//	public void onDestroy() {
//		Log.v(TAG, getClass().getSimpleName() + ".onDestroy()");
//		super.onDestroy();
//	}
//
//	static void logBundle(Bundle savedInstState) {
//		Log.v(Calc360.TAG, savedInstState == null ? "null" : savedInstState.toString());
//	}
//
//	static void logPreferences(SharedPreferences prefs) {
//		Log.i(TAG, "All user preferences");
//		Map<String, ?> all = prefs.getAll();
//		for (String key : all.keySet()) {
//			Log.i(TAG, key + ": " + all.get(key).toString());
//		}
//	}


	// Uses reflection to get an id from R.id, R.array, R.plurals, etc.
	public static int getID(Class clss, String name)
			throws NoSuchFieldException, IllegalAccessException {
		Field field = clss.getDeclaredField(name);
		return field.getInt(null);
	}

	// Uses android functionality to get an
	// ID from R.id, R.array, R.plurals, etc.
	public static int getID(Context ctx, String type, String name) {
		Resources res = ctx.getResources();
		String pkg = ctx.getPackageName();
		return res.getIdentifier(name, type, pkg);
	}
}
