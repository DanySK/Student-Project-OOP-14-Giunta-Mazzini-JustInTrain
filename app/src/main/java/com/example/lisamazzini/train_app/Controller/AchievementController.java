package com.example.lisamazzini.train_app.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.achievement.DelayAchievement1;
import com.example.lisamazzini.train_app.achievement.IAchievement;
import com.example.lisamazzini.train_app.achievement.PinAchievement1;
import com.example.lisamazzini.train_app.exceptions.AchievementException;
import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

import java.util.HashMap;
import java.util.Map;


/**
 * Classe che gestisce l'aggiornamento e lo sblocco di achievement; si appoggia su delle SharedPreferences su cui
 * salva gli achievement che vengono sbloccati. Nel momento in cui un achievement viene sbloccato si controlla che non
 * sia già stato sbloccato in passato; se è così, non accade nulla, altrimenti viene lanciata un'eccezione che farà
 * apparire un Toast nell'applicazione e segnerà l'achievement fra quelli sbloccati
 *
 * @author Lisa Mazzini
 */
public class AchievementController {

    private final Map<String, IAchievement> achievements = new HashMap<>();
    private final SharedPreferences data;
    private final SharedPreferences.Editor editor;


    /**
     * Costruttore.
     * @param context Context per le sharedpreferences
     */
    public AchievementController(final Context context) {
        achievements.put("Delay", new DelayAchievement1(context));
        achievements.put("Pin", new PinAchievement1(context));
        data = context.getSharedPreferences(Constants.ACH_STORE_FILE, Context.MODE_APPEND);
        editor = data.edit();
        editor.apply();;
    }

    /**
     * Metodo che ad ogni azione di "pin" aggiorna tutti gli achievement e, allo sblocco di uno di essi,
     * controlla se è già presente fra quelli sbloccati.
     * @param train treno da cui estrarre i dati
     * @throws AchievementException se un nuovo achievement viene sbloccato
     */
    public final void updateAchievements(final PlainSolution train) throws AchievementException {
        try {
            for (IAchievement a : achievements.values()) {
                a.addData(train);
            }
        } catch (AchievementException e) {
            if (data.contains(e.getMessage())) {
                return;
            } else {
                editor.putString(e.getMessage(), e.getMessage());
                editor.apply();
                throw e;
            }
        }
    }
}
