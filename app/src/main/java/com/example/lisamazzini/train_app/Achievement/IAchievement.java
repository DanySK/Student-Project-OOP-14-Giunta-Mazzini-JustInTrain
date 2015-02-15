package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

/**
 * Interfaccia che identifica il comportamento di ogni achievement
 *
 * @author lisamazzini
 */
public interface IAchievement {
    public void addData(PlainSolution train) throws AchievementException;
}
