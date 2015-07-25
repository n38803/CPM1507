package com.parse.starter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * Created by shaunthompson on 7/24/15.
 */
public class CheckParseService extends Service {

    private Handler mPeriodicEventHandler;

    private final int PERIODIC_EVENT_TIMEOUT = 15000;

    @Override
    public void onCreate() {
        super.onCreate();

        mPeriodicEventHandler = new Handler();
        mPeriodicEventHandler.postDelayed(doPeriodicTask, PERIODIC_EVENT_TIMEOUT);
    }

    private Runnable doPeriodicTask = new Runnable()
    {
        public void run()
        {
            // getting the static instance of activity
            UserActivity activity = UserActivity.instance;

            // call update method to refresh listview
            if (activity != null) {
                activity.UpdateListView();
                // update every 15 seconds
                Toast.makeText(getApplicationContext(),
                        "Information Synced.", Toast.LENGTH_LONG).show();
            }



            mPeriodicEventHandler.postDelayed(doPeriodicTask, PERIODIC_EVENT_TIMEOUT);
        }
    };

    @Override
    public void onDestroy() {

        mPeriodicEventHandler.removeCallbacks(doPeriodicTask);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
