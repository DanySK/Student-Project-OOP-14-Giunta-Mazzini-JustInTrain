package com.example.lisamazzini.train_app;


import org.joda.time.DateTime;

/**
 * Created by lisamazzini on 03/02/15.
 */
public class Utilities {


    // fonte: http://stackoverflow.com/questions/9027317/how-to-convert-milliseconds-to-hhmmss-format
    public static String fromMsToTime(Long millis){

        DateTime date = new DateTime(millis);
        date.plusHours(1);
        int hour = date.getHourOfDay();
        int minutes = date.getMinuteOfHour();

        return hour + ":" + minutes;

    }
}
