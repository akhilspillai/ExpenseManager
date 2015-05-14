package com.trip.expensemanager.listeners;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.trip.expensemanager.database.LocalDB;
import com.trip.expensemanager.services.SyncIntentService;
import com.trip.utils.Global;

public class ConnectivityChangeListener extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (Global.isConnected(context)) {
            long lngUserId = new LocalDB(context).retrieve();
            if (lngUserId != 0L && !SyncIntentService.isInstanceCreated()) {
                Intent serviceIntent = new Intent(context, SyncIntentService.class);
                context.startService(serviceIntent);
            }
        }
    }
}