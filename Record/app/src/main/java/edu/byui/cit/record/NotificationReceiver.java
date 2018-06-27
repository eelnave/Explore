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
		NotificationManager notificationManager = (NotificationManager)context
                .getSystemService(
				Context.NOTIFICATION_SERVICE);

		Intent repeating_intent = new Intent(context,
				RepeatingNotification.class);
		repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
				repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context, "NotificationID")
				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.addAction(android.R.drawable.arrow_down_float, "Yes", pendingIntent)
				.addAction(android.R.drawable.arrow_up_float, "No", pendingIntent)
				//.addAction("Ignore")
				.setSmallIcon(android.R.drawable.arrow_up_float)
				.setContentTitle("Your Goal")
				.setContentText("Did you complete it?")
				.setAutoCancel(true)
				.setContentIntent(pendingIntent);

		notificationManager.notify(100, builder.build());
	}
}
