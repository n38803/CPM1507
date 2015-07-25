package com.parse.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by shaunthompson on 7/24/15.
 */
public class CheckConnectionService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (cm == null)
            return;
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) {
            // Send here
            //Toast.makeText(context, "Network Available", Toast.LENGTH_LONG).show();
        } else {
            // Do nothing or notify user somehow
            Toast.makeText(context, "Lost Data Connection", Toast.LENGTH_LONG).show();
        }

    }
}