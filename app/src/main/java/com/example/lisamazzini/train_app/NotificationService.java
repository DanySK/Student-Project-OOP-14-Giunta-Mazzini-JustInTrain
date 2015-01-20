package com.example.lisamazzini.train_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;

public class NotificationService extends Service {

    private String number;
    private Train currentStateTrain;
    private NotificationCompat.Builder notificationBuilder;
    private RemoteViews view;
   // private final NotificationManager manager;
    private  Notification not;
    private PendingIntent pIntent1;
    private PendingIntent pIntent2;


    @Override
    public void onDestroy(){
      //  manager.cancel(1);
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        Intent intent2 = new Intent(this, NotificationService.class);
        pIntent1 = PendingIntent.getService(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntent2 = PendingIntent.getBroadcast(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        view = new RemoteViews(getPackageName(), R.layout.layout_notification);
        not = builder.setContent(view)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntent1)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntent2)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("vaffanculo"))
                        .build();


        this.number = intent.getStringExtra("number");
        Log.d("OOOOOOOOOOOOOOOOOOOO", "On start command");

        ScrapingTask scraper = new ScrapingTask(this.number);
        scraper.execute();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void refresh(){

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

            view.setTextViewText(R.id.ntNumber, currentStateTrain.getNumber());
            view.setTextViewText(R.id.ntDelay, Integer.toString(currentStateTrain.getDelay()));
            view.setTextViewText(R.id.ntStation, currentStateTrain.getLastSeenStation());
            view.setTextViewText(R.id.ntTime, currentStateTrain.getLastSeenTime());
            Log.d("OOOOOOOOOOOOOOOOOOOO", "On post execute");

            startForeground(1, not);

        }
    }
}
