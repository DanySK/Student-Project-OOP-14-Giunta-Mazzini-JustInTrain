package com.example.lisamazzini.train_app.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.TextConstants;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe che funge da Controller per l'AchievementListFragment; gli achievement vengono collezionati
 * come semplice lista di stringhe, che viene aggiornata in base agli achievement sbloccati segnati all'interno
 * delle SharedPreferences.
 *
 * @author lisamazzini
 */
public class AchievementListController  {

    private final SharedPreferences data;
    private final List<String> achievements = new LinkedList<>();

    /**
     * Costruttore.
     * @param context per le sharedpreferences
     */
    public AchievementListController(final Context context) {
        data = context.getSharedPreferences(Constants.ACH_STORE_FILE, Context.MODE_APPEND);
    }

    /**
     * Metodo che aggiorna la lista, scorrendo le informazioni salvate nelle SharedPreferences.
     * @return la lista aggiornata
     */
    public final List<String> computeAchievement() {
        for (final String s : data.getAll().keySet()) {
            switch (s) {
                case TextConstants.DELAY_ACH:
                    achievements.add(TextConstants.ACH_DELAY_LV_1);
                    break;
                case TextConstants.PIN_ACH:
                    achievements.add(TextConstants.ACH_PINNER_LV_1);
                    break;
                default:
                    break;
            }
        }
        return new LinkedList<>(achievements);
    }
}
