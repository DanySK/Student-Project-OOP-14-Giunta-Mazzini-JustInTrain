package com.example.lisamazzini.train_app;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;

public class NotificationService extends Service {

    private String number;
    private Train currentStateTrain;
//    private RemoteViews view;
    private PendingIntent pIntentRefresh;
    private PendingIntent pIntentClose;


    @Override
    public void onDestroy(){
        stopForeground(true);
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

      //  view = new RemoteViews(getPackageName(), R.layout.layout_notification);


        Log.d("OOOOOOOOOOOOOOOOOOOO", "On start command" + intent.getStringExtra("number"));
        ScrapingTask scraper = new ScrapingTask(this.number);
        scraper.execute();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private class ScrapingTask extends AsyncTask<Void, Void, Train> {

        private final JsoupTrainDetails scraperTrain;
        private Train train;
        private String number;

        protected ScrapingTask(String trainNumber){
            Log.d("OOOOOOOOOOOOOOOOOOOO", "" + trainNumber);

            this.number = trainNumber;
            this.scraperTrain = new JsoupTrainDetails(this.number);
        }

        @Override
        protected Train doInBackground(Void... params) {
            try {
                Log.d("OOOOOOOOOOOOOOOOOOOO", "doinbackground");

                train = scraperTrain.computeResult();

            } catch (IOException e) {
            }
            return train;
        }

        @Override
        protected void onPostExecute(Train train){
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
