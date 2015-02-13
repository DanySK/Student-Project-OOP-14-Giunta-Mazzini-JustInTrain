package com.example.lisamazzini.train_app.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 *
 *
 * Created by Lisa Mazzini
 */
public class ButtonListener extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);

        if(intent.getAction().equals("Aggiorna")){
            i.putExtra("number", intent.getStringExtra("number"));
            i.putExtra("idOrigine", intent.getStringExtra("idOrigine"));
            i.putExtra("oraPartenza", intent.getStringExtra("oraPartenza"));
            context.startService(i);
        }

        if(intent.getAction().equals("Elimina")){
            context.stopService(i);
        }
    }
}

