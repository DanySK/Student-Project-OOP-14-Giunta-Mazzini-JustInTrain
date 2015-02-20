package com.example.lisamazzini.train_app.achievement;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

/**
 * Questa classe rappresenta l'implementazione generica di un Achievement;
 * Utilizza delle SharedPreferences per leggere e aggiornare i dati relativi all'achievement e
 * un oggetto Strategy per identificare come aggiornare i dati e quando sbloccare l'achievement.
 *
 * @author Lisa Mazzini
 */

public class BasicAchievement implements IAchievement {

    private Long value;
    private final Strategy strategy;
    private final SharedPreferences data;
    private final SharedPreferences.Editor editor;

    /**
     * Costruttore
     *
     * @param value valore da aggiornare
     * @param strategy oggetto Strategy che ne descrive il funzionamento
     * @param context il Context necessario per prendere le SharedPreferences
     */
    public BasicAchievement(final Long value, final Strategy strategy, final Context context){
        this.value = value;
        this.strategy = strategy;
        data = context.getSharedPreferences(Constants.ACH_DATA_FILE, Context.MODE_APPEND);
        editor = data.edit();
        editor.apply();
    }

    /**
     * Metodo che aggiorna i dati dell'achievement
     *
     *
     * @param train il treno da cui prendere i dati
     * @throws AchievementException se si sblocca un achievement
     */
    @Override
    public void addData(final PlainSolution train) throws AchievementException {

        // prendo il valore dal file
        this.value = data.getLong(strategy.getKey(), 0L);

        //aggiorno il valore
        this.value = strategy.compute(train, value);

        //rimetto il valore aggiornato nel file
        editor.putLong(strategy.getKey(), this.value);
        editor.apply();

        //controllo di aver raggiunto l'obbiettivo
        strategy.control(value);

    }
}
