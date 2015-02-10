package com.example.lisamazzini.train_app;


import android.util.Log;

import com.example.lisamazzini.train_app.Model.Fermate;
import com.example.lisamazzini.train_app.Model.NewTrain;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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

    public static String getProgress(NewTrain train){
        Long delta = 0L;
        Long intermediateDelta = 0L;
        List<Fermate> visited = new LinkedList<>();

        for(Fermate f : train.getFermate()){
            if(f.getActualFermataType() != 0){
                visited.add(f);
            }
        }

        if(visited.size() < 5) {
            for (int i = visited.size() - 2; i >= 0; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" A intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }else{
            for (int i = visited.size()-2; i >= visited.size()-6; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" B intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }

        Log.d("delta", "" + delta);

        if(delta > 2L){
            return "In rallentamento";
        }
        if(delta < -2L){
            return "In recupero";
        }

        return "Costante";
    }
}
