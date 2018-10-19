package edu.byui.cit.record;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//create the notification manager
		NotificationManager notificationManager = (NotificationManager)context
                .getSystemService(
				Context.NOTIFICATION_SERVICE);

		//create an intent to clear the notification when clicked
		Intent repeating_intent = new Intent(context,
				RepeatingNotification.class);
		repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		//if the intent already exists, simply update it
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
				repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context, "NotificationID")
				//notification is visible while phone is locked
				.setVisibility(Notification.VISIBILITY_PUBLIC)
				//adds ability to click yes and no to the notification
				.addAction(android.R.drawable.arrow_down_float, "Yes", pendingIntent)
				.addAction(android.R.drawable.arrow_up_float, "No", pendingIntent)
				//.addAction("Ignore")
				.setSmallIcon(android.R.drawable.arrow_up_float)
				//title of the notification
				.setContentTitle("Your Goal")
				//set the content text of notification
				.setContentText("Did you complete it?")
				//remove when clicked
				.setAutoCancel(true)
				//set what happens when you click
				.setContentIntent(pendingIntent);
		//place constructed notification in status bar
		notificationManager.notify(100, builder.build());
	}
}
