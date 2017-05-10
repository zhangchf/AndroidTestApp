package com.example.zhangchf.mytestapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangchf on 09/05/2017.
 */

public class AlarmService extends Service {
    private static final String TAG = AlarmService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand, intent=" + intent);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "onTaskRemoved, rootIntent=" + rootIntent);
        scheduleNotification();
    }

    private void scheduleNotification() {
        Log.i(TAG, "broadcast schedule notification");
        Intent i = new Intent(AlarmReceiver.ACTION_SCHEDULE_NOTIFICATION);
        sendBroadcast(i);
    }
}
