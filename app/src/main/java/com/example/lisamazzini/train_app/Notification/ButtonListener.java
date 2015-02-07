package com.example.lisamazzini.train_app.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ButtonListener extends BroadcastReceiver {

       /* public ButtonListener(){
            super();
       }*/

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);

        if(intent.getAction().equals("Aggiorna")){
            Log.d("-----------", "aggiornata" );
            i.putExtra("number", intent.getStringExtra("number"));
            i.putExtra("idOrigine", intent.getStringExtra("idOrigine"));
            context.startService(i);
        }

        if(intent.getAction().equals("Elimina")){
            Log.d("---------", "eliminata");
            context.stopService(i);
        }
    }
}

