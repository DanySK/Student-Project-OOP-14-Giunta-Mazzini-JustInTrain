package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.DelayAchievementException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import android.content.Context;

/**
 * Classe che rappresenta l'achievement "Ritardatario level 1", che si basa
 * sui minuti di ritardo accumulati e si sblocca dopo 60 minuti;
 *
 * @author Lisa Mazzini
 */
public class DelayAchievement1 extends BasicAchievement {


    public DelayAchievement1(Context context){
        super(0L, new Strategy() {
            @Override
            public Long compute(PlainSolution train, Long value) {
                return value + Long.parseLong(train.getDelay());
            }

            @Override
            public void control(Long value) throws DelayAchievementException {
                if(value > 60L){
                    throw new DelayAchievementException(Constants.DELAY_ACH);
                }
            }
            @Override
            public String getKey() {
                return "Delay";
            }
        }, context);
    }
}
