package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import android.content.Context;
import android.util.Log;

/**
 * Questa classe rappresenta l'achievement che si basa sul numero di treni su cui
 * l'utente fa "Pin"; si sblocca dopo 10 pin
 *
 * @author Lisa Mazzini
 *
 */
public class PinAchievement1 extends BasicAchievement {
    public PinAchievement1(Context context) {
        super(0L, new Strategy() {
            @Override
            public Long compute(PlainSolution train, Long value) {
                return value + 1L;
            }
            @Override
            public void control(Long value) throws AchievementException {
                if(value == 10L){
                    throw new PinAchievementException(Constants.PIN_ACH);
                }
            }
            @Override
            public String getKey() {
                return "Pin";
            }
        }, context );
    }
}
