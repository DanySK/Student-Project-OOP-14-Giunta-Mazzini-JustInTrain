package com.example.lisamazzini.train_app.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lisamazzini.train_app.model.Constants;

/**
 * Listener per il broadcast inviato dal NotificationService alla pressione dei pulsanti della notifica
 * che in base alla Action dell'intent ricevuto, riavvia il service oppure lo interrompe.
 *
 * @author lisamazzini
 */
public class ButtonListener extends BroadcastReceiver {


    @Override
    public final void onReceive(final Context context, final Intent intent) {
        final Intent i = new Intent(context, NotificationService.class);

        if (intent.getAction().equals(Constants.ACTION_REFRESH)) {
            i.putExtra(Constants.TRAIN_N_EXTRA, intent.getStringExtra(Constants.TRAIN_N_EXTRA));
            i.putExtra(Constants.ID_ORIGIN_EXTRA, intent.getStringExtra(Constants.ID_ORIGIN_EXTRA));
            i.putExtra(Constants.DEPARTURE_TIME_EXTRA, intent.getStringExtra(Constants.DEPARTURE_TIME_EXTRA));
            context.startService(i);
        }
        if (intent.getAction().equals(Constants.ACTION_DELETE)) {
            context.stopService(i);
        }
    }
}

