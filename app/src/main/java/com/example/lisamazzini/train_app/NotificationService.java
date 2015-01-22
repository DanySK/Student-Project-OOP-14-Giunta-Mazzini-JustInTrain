package com.example.lisamazzini.train_app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.IOException;

public class NotificationService extends Service {

    private String number;
    private Train currentStateTrain;
//    private RemoteViews view;
    private PendingIntent pIntentRefresh;
    private PendingIntent pIntentClose;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);


    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");
        this.number = intent.getStringExtra("number");
        intentRefresh.putExtra("number", this.number);
        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction("Elimina");
        pIntentRefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentClose = PendingIntent.getBroadcast(this, 1, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);

        spiceManager = new SpiceManager(UncachedSpiceService.class);
        TrainRequest request = new TrainRequest(this.number);
        spiceManager.execute(request, new TrainRequestListener());


        //  view = new RemoteViews(getPackageName(), R.layout.layout_notification);


        Log.d("OOOOOOOOOOOOOOOOOOOO", "On start command" + intent.getStringExtra("number"));
        spiceManager.start(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class TrainRequestListener implements RequestListener<Train> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(NotificationService.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            currentStateTrain = train;

            //view.setTextViewText(R.id.ntNumber, currentStateTrain.getNumber());
            //view.setTextViewText(R.id.ntDelay, Integer.toString(currentStateTrain.getDelay()));
           //view.setTextViewText(R.id.ntStation, currentStateTrain.getLastSeenStation());
            //view.setTextViewText(R.id.ntTime, currentStateTrain.getLastSeenTime());
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Log.d("OOOOOOOOOOOOOOOOOOOO", "On post execute");

            Notification not = builder//.setContent(view)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                    .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                    .setStyle(new NotificationCompat.InboxStyle()
                            .setBigContentTitle("Treno" + currentStateTrain.getNumber())
                            .addLine("Ritardo " + currentStateTrain.getDelay())
                            .addLine("Ultimo avvistamento" + currentStateTrain.getLastSeenStation())
                            .addLine("Ore " + currentStateTrain.getLastSeenTime()))
                    .build();

            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);

        }
    }
}
