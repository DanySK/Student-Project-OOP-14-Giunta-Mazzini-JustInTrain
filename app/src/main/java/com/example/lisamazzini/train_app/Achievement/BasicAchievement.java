package com.example.lisamazzini.train_app.Achievement;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisamazzini on 04/02/15.
 */

//Singleton + strategy (?)
public class BasicAchievement implements IAchievement {

    private Long value;
    private final Strategy strategy;
    private final SharedPreferences data;
    private final SharedPreferences.Editor editor;


    public BasicAchievement(Long value, Strategy strategy, Context context){
        this.value = value;
        this.strategy = strategy;
        data = context.getSharedPreferences("ACHIEVEMENT_DATA", Context.MODE_APPEND);
        editor = data.edit();
    }

    @Override
    public void addData(PlainSolution train) throws AchievementException {
        // prendo il valore dal file
        this.value = data.getLong(strategy.getKey(), 0L);
        //aggiorno il valore
        this.value = strategy.compute(train, value);
        //rimetto il valore aggiornato nel file
        editor.putLong(strategy.getKey(), this.value);
        editor.apply();
        //controllo di aver raggiunto l'obbiettivo
        strategy.control(value);
        Log.d("i looooooog", "ho finito di fare addData");

    }
}
