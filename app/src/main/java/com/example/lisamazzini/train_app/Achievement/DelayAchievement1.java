package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.exceptions.DelayAchievementException;
import com.example.lisamazzini.train_app.model.TextConstants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

import android.content.Context;

/**
 * Classe che rappresenta l'achievement "Ritardatario level 1", che si basa
 * sui minuti di ritardo accumulati e si sblocca dopo 60 minuti.
 *
 * @author Lisa Mazzini
 */
public class DelayAchievement1 extends BasicAchievement {

    private static final long LIMIT = 60L;
    /**
     * Costruttore che chiama solo la super() passando il valore iniziale, lo Strategy
     * che ne descrive il comportamento e il context per le SharedPreferences.
     * @param context per le shared preferences
     */
    public DelayAchievement1(final Context context) {
        super(0L, new Strategy() {
            @Override
            public long compute(final PlainSolution train, final long value) {
                return value + Long.parseLong(train.getDelay());
            }

            @Override
            public void control(final long value) throws DelayAchievementException {
                if (value > LIMIT) {
                    throw new DelayAchievementException(TextConstants.DELAY_ACH);
                }
            }
            @Override
            public String getKey() {
                return "Delay";
            }
        }, context);
    }
}
