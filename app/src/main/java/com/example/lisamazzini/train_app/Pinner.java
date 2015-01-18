package com.example.lisamazzini.train_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by lisamazzini on 13/01/15.
*/
public class Pinner {

    private String number;
    private Train currentStateTrain;
    private NotificationCompat.Builder notificationBuilder;
    private Context context;
    private int mInterval = 120000;
    private Handler mHandler = new Handler();
    private RemoteViews view;
    private final NotificationManager manager;
    private final  Notification not;

    public Pinner (Context context){
        this.context = context;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        view = new RemoteViews(context.getPackageName(), R.layout.layout_notification);
        not = builder.setContent(view).setSmallIcon(R.drawable.ic_launcher).setOngoing(true).build();
        manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
    }


    public void pinTrain(String trainNumber){
        this.number = trainNumber;
        setNotification();
    }

    public void unpinTrain(){
        mHandler.removeCallbacks(mUpdater);
        manager.cancel(1);
    }

    Runnable mUpdater = new Runnable() {
        @Override
        public void run() {

            ScrapingTask scraper = new ScrapingTask(number);
            scraper.execute();
            mHandler.postDelayed(mUpdater, mInterval);


        }
    };


    private void setNotification(){
        mUpdater.run();
    }


    private class ScrapingTask extends AsyncTask<Void, Void, Train> {

        private final JsoupTrainDetails scraperTrain;
        private Train train;
        private String number;

        protected ScrapingTask(String trainNumber){
            this.number = trainNumber;
            this.scraperTrain = new JsoupTrainDetails(this.number);
        }

        @Override
        protected Train doInBackground(Void... params) {
            try {
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


            manager.notify(1, not);

        }
}


}
