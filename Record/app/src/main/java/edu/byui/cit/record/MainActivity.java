package edu.byui.cit.record;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Record";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		FloatingActionButton fab = findViewById(R.id.newGoalFAB);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//make the jump to the new goal screen
				Intent intent = new Intent(MainActivity.this, add_customgoal.class);
				startActivity(intent);
			}
		});
		//Daily notification time, intent, and alarm manager
		Calendar calendar = Calendar.getInstance();
//    calendar.set(Calendar.HOUR_OF_DAY, 18);
//    calendar.set(Calendar.MINUTE, 30);
		calendar.add(Calendar.SECOND, 5);

		Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
	}
}
