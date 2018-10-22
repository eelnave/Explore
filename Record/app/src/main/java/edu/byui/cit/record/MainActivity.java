package edu.byui.cit.record;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

import edu.byui.cit.widget.CITFragment;


/*
	The idea behind this app is to give the user a simple way of creating a
	goal and then to track it. Some features we envisioned are to send the
	user a notification that reminds them to work on that goal and an easy
	way to hold themselves accountable. Another feature we intended to add
	was a graph/chart that the user can use to visually see their progress.

	This is what is in the app:
		Homelander is the main page that is where app starts at.
		Functioning room database,
		the app is split using fragments,
		a functional UI,
		Toasts that show the user what they recorded,
		and user input.

	TODO
		implement basic functionality for minimum viable product
		comment code to make it readable
		Add a table to record user progress towards goals,
		Figure out notifications,
		figure out how to add user input to database(not hardcoded values),
		figure out graphing data.
		finish about page

 */

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Record";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.main_activity);

			//Daily notification time, intent, and alarm manager
			Calendar calendar = Calendar.getInstance();
			//    calendar.set(Calendar.HOUR_OF_DAY, 18);
			//    calendar.set(Calendar.MINUTE, 30);
			calendar.add(Calendar.SECOND, 5);

			Intent intent = new Intent(getApplicationContext(),
					NotificationReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					getApplicationContext(), 100, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			//the alarm manager is used to run code while app is closed
			AlarmManager alarmManager = (AlarmManager)getSystemService(
					ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
					pendingIntent);

			//testing for noticifations

				//manually make a notification when app is opened
				sendBroadcast(intent);

			//end testing for notifications


			//populate the main screen area with current goals

			//go ahead and launch the main lander fragment
			// VERY IMPORTANT! This is how we switch to the main fragment
			if (savedInstanceState == null) {
				CITFragment frag = new home_lander();
				FragmentTransaction trans = getFragmentManager()
						.beginTransaction();
				trans.add(R.id.fragContainer, frag);
				trans.commit();
			}

			//test with filler data

		}
		catch (Exception ex) {
			Log.e(TAG, ex.toString());
		}
	}


	//code to handle our fragments
	void switchFragment(CITFragment fragment, Bundle bundle) {
		fragment.setArguments(bundle);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
	}
}
