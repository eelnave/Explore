package edu.byui.cit.record;

import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.byui.cit.widget.CITFragment;


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

			AlarmManager alarmManager = (AlarmManager)getSystemService(
					ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
					pendingIntent);

			//populate the main screen area with current goals

			//go ahead and launch the main lander fragment
			// VERY IMPORTANT!!! THis is how we switch to the main fragment
			if (savedInstanceState == null){
				CITFragment frag = new home_lander();
				FragmentTransaction trans = getFragmentManager().beginTransaction();
				trans.add(R.id.fragContainer, frag);
				trans.commit();
			}

			//test with filler data

		}
		catch (Exception ex) {
			Log.e("Record", ex.toString());
		}
	}

	//code to handle our fragments
	public void switchFragment(CITFragment fragment, Bundle bundle){
		fragment.setArguments(bundle);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
	}
}
