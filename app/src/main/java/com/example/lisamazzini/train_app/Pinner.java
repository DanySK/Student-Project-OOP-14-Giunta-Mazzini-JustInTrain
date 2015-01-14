/*package com.example.lisamazzini.train_app;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

/**
 * Created by lisamazzini on 13/01/15.

public class Pinner {

    private String number;
    private Train currentStateTrain;
    private NotificationCompat.Builder notificationBuilder;
    //SINGLETON? no perchè magari ne fa unoper tre

    public void pinTrain(String trainNumber){
        this.number = trainNumber;
        setNotification();

    }


    private void setNotification(){

        JsoupTrainDetails scraper = new JsoupTrainDetails(this.number);
        try {
            currentStateTrain = scraper.computeResult();
            notificationBuilder = new NotificationCompat.Builder(TrainFavouriteListActivity.);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}*/
