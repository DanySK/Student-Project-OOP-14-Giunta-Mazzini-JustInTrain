package com.example.lisamazzini.train_app.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Achievement.DelayAchievement1;
import com.example.lisamazzini.train_app.Achievement.IAchievement;
import com.example.lisamazzini.train_app.Achievement.PinAchievement1;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.DelayAchievementException;
import com.example.lisamazzini.train_app.Exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class AchievementController {

    private final Map<String, IAchievement> achievements = new HashMap<>();
    private Context context;
    private final SharedPreferences data;
    private final SharedPreferences.Editor editor;


    public AchievementController(Context context){
        this.context = context;
        achievements.put("Delay", new DelayAchievement1(context));
        achievements.put("Pin", new PinAchievement1(context));
        data = context.getSharedPreferences("ACHIEVEMENT_STORE", Context.MODE_APPEND);
        editor = data.edit();
    }


//    public void addAchievement(IAchievement achievement){
//        achievements.put(achievement);
//    }

    public void updateAchievements(PlainSolution train) throws AchievementException {
        try {
            for (IAchievement a : achievements.values()) {
                Log.d("i looooooog", "aggiorno i dati");
                a.addData(train);
            }
        }catch (AchievementException e){
            if(data.contains(e.getMessage())){
                Log.d("i looooooog", "aggiorno i dati");
                //do nothing
                return;
            }else{
                editor.putString(e.getMessage(), e.getMessage());
                editor.apply();
                throw e;
            }
        }
    }

    private void remove(AchievementException e){
        if(e instanceof DelayAchievementException){
            Log.d("i looooooog", "ho raggiunto i minuti sono nelremove");
            achievements.remove("Delay");
            return;
        }
        if(e instanceof PinAchievementException){
            Log.d("i looooooog", "ho raggiunto i pin sono nelremove");
            achievements.remove("Pin");
            return;
        }
    }


}
