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

//			FloatingActionButton fab = findViewById(R.id.newGoalFAB);
//			fab.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					//make the jump to the new goal screen
//					//switching to fragments
//					CITFragment frag = new add_newGoal();
//					switchFragment(frag);
////					Intent intent = new Intent(MainActivity.this,
////							add_customgoal.class);
////					startActivity(intent);
//				}
//			});

//			This is our experiment in using a fragment.
//			Button deleteJunk = findViewById(R.id.deleteGoal);
//			deleteJunk.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					//make the jump to the new goal screen
//					//start the fragment
//// Create a new Fragment to be placed in the activity layout
//					addNewGoal addNewGoalFragment = new addNewGoal();
//
//					// In case this activity was started with special instructions from an
//					// Intent, pass the Intent's extras to the fragment as arguments
//					addNewGoalFragment.setArguments(getIntent().getExtras());
//
//					// Add the fragment to the 'fragment_container' FrameLayout
//					getSupportFragmentManager().beginTransaction()
//							.add(R.id.headlines_fragment, addNewGoalFragment).commit();
//
//
////					Intent testIntent = new Intent(MainActivity.this,
////							addNewGoal.class);
////					startActivity(testIntent);
//				}
//			});
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
			if (savedInstanceState == null){
				CITFragment frag = new home_lander();
				FragmentTransaction trans = getFragmentManager().beginTransaction();
				trans.add(R.id.fragContainer, frag);
				trans.commit();
			}

			//test with filler data

			ArrayList<String> theGoals = new ArrayList<>();
			theGoals.add("Read Scriptures");
			theGoals.add("Teach my kid to read");
			theGoals.add("Work on android apps");
			theGoals.add("Help Old Man Jenkins with his lawn");

			//this object represents the listView on the screen
			ListView mainListView = findViewById(R.id.toDoList);
			ArrayAdapter<String> theAdapter = new ArrayAdapter<>(
					this, R.layout.simplerow, theGoals);
			// TODO: THIS IS THE THING THAT IS CAUSING THE CRASH
			mainListView.setAdapter(theAdapter);
		}
		catch (Exception ex) {
			Log.e("Record", ex.toString());
		}
	}

	//code to handle our fragments
	public void switchFragment(CITFragment fragment){
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
	}
}
