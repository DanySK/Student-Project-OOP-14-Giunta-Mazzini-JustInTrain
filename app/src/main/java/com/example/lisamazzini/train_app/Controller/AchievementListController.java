package com.example.lisamazzini.train_app.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 10/02/15.
 */
public class AchievementListController  {
    private SharedPreferences data;
    List<String> achievements = new LinkedList<>();


    public AchievementListController(Context context){
        data = context.getSharedPreferences("ACHIEVEMENT_STORE", Context.MODE_APPEND);
    }

    public List<String> computeAchievement(){
        for(String s : data.getAll().keySet()) {
            switch (s) {
                case Constants.DELAY_ACH:
                    Log.d("Ritardo", "sbloccato");
                    achievements.add("Ritardatario! (10 minuti)");
                    break;
                case Constants.PIN_ACH:
                    Log.d("Pin", "sbloccato");
                    achievements.add("Pinnatore seriale");
                    break;
                default:
                    break;
            }
        }
        return achievements;
    }


}
