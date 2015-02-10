package com.example.lisamazzini.train_app;


import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;

/**
 * Created by Lisa Mazzini
 */
public class Utilities {


    public static String fromMsToTime(Long millis){
        if(millis == null){
            return "--";
        }
        DateTime date = new DateTime(millis);
        date.plusHours(1);
        return DateTimeFormat.forPattern("HH:mm").print(date);
    }

    public static String[] splitString(String data){
        final String[] result = new String[3];
        result[0] = data.split("\\|")[1].split("-")[0];    //numero
        result[1] = data.split("\\|")[1].split("-")[1];    //codice
        result[2] = data.split("\\|")[0].split("-")[1];    //nome
        return result;
    }

    //Questo
    public static MutableDateTime getDate(String time){
        DateTime now = new DateTime(Calendar.getInstance().getTime());
        String[] arrTime = time.split(":");
        MutableDateTime date = now.toMutableDateTime();
        date.setDate(Calendar.getInstance().getTimeInMillis());
        date.setTime(Integer.parseInt(arrTime[0]), Integer.parseInt(arrTime[1]), 0, 0);
        return date;
    }
}
