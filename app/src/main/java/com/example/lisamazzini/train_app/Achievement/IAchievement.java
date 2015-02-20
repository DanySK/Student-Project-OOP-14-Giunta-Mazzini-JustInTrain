package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.exceptions.AchievementException;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

/**
 * Interfaccia che identifica il comportamento di ogni achievement.
 *
 * @author lisamazzini
 */
public interface IAchievement {
    /**
     * Metodo che aggiorna i dati dell'achievement.
     * @param train il treno da cui prendere i dati
     * @throws AchievementException se si sblocca un achievement
     */
    void addData(PlainSolution train) throws AchievementException;
}
