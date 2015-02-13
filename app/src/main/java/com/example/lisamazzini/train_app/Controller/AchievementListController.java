package com.example.lisamazzini.train_app.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe che funge da Controller per l'AchievementListFragment; gli achievement vengono collezionati
 * come semplice lista di stringhe, che viene aggiornata in base agli achievement sbloccati segnati all'interno
 * delle SharedPreferences
 *
 * @author Lisa Mazzini
 */
public class AchievementListController  {
    private SharedPreferences data;
    List<String> achievements = new LinkedList<>();

    public AchievementListController(Context context){
        data = context.getSharedPreferences("ACHIEVEMENT_STORE", Context.MODE_APPEND);
    }

    /**
     * Metodo che aggiorna la lista, scorrendo le informazioni salvate nelle SharedPreferences
     * @return la lista aggiornata
     */
    public List<String> computeAchievement(){
        for(String s : data.getAll().keySet()) {
            switch (s) {
                case Constants.DELAY_ACH:
                    achievements.add("Ritardatario! (10 minuti)");
                    break;
                case Constants.PIN_ACH:
                    achievements.add("Pinnatore seriale!");
                    break;
                default:
                    break;
            }
        }
        return achievements;
    }


}
