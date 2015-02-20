package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.exceptions.AchievementException;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

/**
 * Questa interfaccia rappresenta il comportamento di un achievement,
 * implementando i suoi metodi si stabilisce il modo in cui i dati vengono aggiornati
 * e il momento in cui verrà sbloccato l'achievement.
 *
 * @author lisamazzini
 */
public interface Strategy {

    /**
     * Metodo che aggiorna il valore.
     * @param train treno da cui prende i dati
     * @param value valore che aggiorna
     * @return valore aggiornato
     */
    Long compute(PlainSolution train, Long value);

    /**
     * Metodo che controlla se l'achievement è stato sbloccato.
     * @param value valore da controllare
     * @throws AchievementException se l'achievement è stato sbloccato
     */
    void control(Long value) throws AchievementException;

    /**
     * Metodo che restituisce la chiave con cui si identifica l'achievement.
     * nelle SharedPreferences
     * @return la chiave (stringa)
     */
    String getKey();
}
