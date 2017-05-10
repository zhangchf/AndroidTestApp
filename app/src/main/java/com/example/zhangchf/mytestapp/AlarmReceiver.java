package com.example.zhangchf.mytestapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by zhangchf on 09/05/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public static final String ACTION_SCHEDULE_NOTIFICATION = "com.example.zhangchf.mytestapp.SCHEDULE_NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive, intent=" + intent);
        if (ACTION_SCHEDULE_NOTIFICATION.equals(intent.getAction())) {
            scheduleNotification(context);
        } else {
            sendNotification(context);
        }
    }

    private void scheduleNotification(Context context) {
        Log.i(TAG, "scheduleNotification");
/*        Intent serviceIntent = new Intent(this, NotificationIntentService.class);
        serviceIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent pi = PendingIntent.getService(this, 1000, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);*/

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, -1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10 * 1000, pi);
    }

    private void sendNotification(Context context) {
        Log.i(TAG, "post notification");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[] {"Line 1", "Line 2", "Message Line 3", "Message Line 4", "Message Line 5", "Long Message Line 6"};

        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Event tracker details:");

        // Moves events into the expanded layout
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);


        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, UITestActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(UITestActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.addAction(R.mipmap.ic_launcher, "Open", resultPendingIntent);
        mBuilder.setPriority(Notification.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_ALL);
        mBuilder.setAutoCancel(true);

        Notification notification = mBuilder.build();

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1000, notification);
    }
}
