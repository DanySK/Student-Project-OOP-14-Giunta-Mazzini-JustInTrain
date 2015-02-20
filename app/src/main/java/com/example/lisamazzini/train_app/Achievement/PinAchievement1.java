package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;
import android.content.Context;

/**
 * Questa classe rappresenta l'achievement che si basa sul numero di treni su cui
 * l'utente fa "Pin"; si sblocca dopo 10 pin
 *
 * @author Lisa Mazzini
 */
public class PinAchievement1 extends BasicAchievement {
    public PinAchievement1(final Context context) {
        super(0L, new Strategy() {
            @Override
            public Long compute(final PlainSolution train, final Long value) {
                return value + 1L;
            }
            @Override
            public void control(final Long value) throws AchievementException {
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
