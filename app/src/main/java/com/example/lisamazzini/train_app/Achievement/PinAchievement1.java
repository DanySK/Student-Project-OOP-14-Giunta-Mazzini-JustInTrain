package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.exceptions.AchievementException;
import com.example.lisamazzini.train_app.exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;
import android.content.Context;

/**
 * Questa classe rappresenta l'achievement che si basa sul numero di treni su cui
 * l'utente fa "Pin"; si sblocca dopo 10 pin.
 *
 * @author Lisa Mazzini
 */
public class PinAchievement1 extends BasicAchievement {

    private static final long LIMIT = 10L;
    /**
     * Costruttore che chiama solo la super() passando il valore iniziale, lo Strategy
     * che ne descrive il comportamento e il context per le SharedPreferences.
     * @param context per le shared preferences
     */
    public PinAchievement1(final Context context) {
        super(0L, new Strategy() {
            @Override
            public long compute(final PlainSolution train, final long value) {
                return value + 1L;
            }
            @Override
            public void control(final long value) throws AchievementException {
                if (value == LIMIT) {
                    throw new PinAchievementException(Constants.PIN_ACH);
                }
            }
            @Override
            public String getKey() {
                return "Pin";
            }
        }, context);
    }
}
