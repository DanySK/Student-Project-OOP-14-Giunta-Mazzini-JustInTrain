package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

/**
 * Questa interfaccia rappresenta il comportamento di un achievement,
 * implementando i suoi metodi si stabilisce il modo in cui i dati vengono aggiornati
 * e quando verrà sbloccato l'achievement
 *
 *
 */
public interface Strategy {

    /**
     * Metodo che aggiorna il valore
     * @param train treno da cui prende i dati
     * @param value valore che aggiorna
     * @return valore aggiornato
     */
    public Long compute(PlainSolution train, Long value);

    /**
     * Metodo che controlla se l'achievement è stato sbloccato
     * @param value valore da controllare
     * @throws AchievementException se l'achievement è stato sbloccato
     */
    public void control(Long value) throws AchievementException;

    /**
     * Metodo che restituisce la chiave con cui si identifica l'achievement
     * nelle SharedPreferences
     * @return la chiave (stringa)
     */
    public String getKey();
}
