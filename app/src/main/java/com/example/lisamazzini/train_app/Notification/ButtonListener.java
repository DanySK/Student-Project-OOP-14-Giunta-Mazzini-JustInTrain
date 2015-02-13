package com.example.lisamazzini.train_app.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;

/**
 * Listener per il broadcast inviato dal NotificationService alla pressione dei pulsanti della notifica
 * che in base alla Action dell'intent ricevuto, riavvia il service oppure lo interrompe.
 *
 * Created by Lisa Mazzini
 */
public class ButtonListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);

        if(intent.getAction().equals(Constants.ACTION_REFRESH)){
            i.putExtra(Constants.TRAIN_N_EXTRA, intent.getStringExtra(Constants.TRAIN_N_EXTRA));
            i.putExtra(Constants.ID_ORIGIN_EXTRA, intent.getStringExtra(Constants.ID_ORIGIN_EXTRA));
            i.putExtra(Constants.DEPARTURE_TIME_EXTRA, intent.getStringExtra(Constants.DEPARTURE_TIME_EXTRA));
            context.startService(i);
        }
        if(intent.getAction().equals(Constants.ACTION_DELETE)){
            context.stopService(i);
        }
    }
}

