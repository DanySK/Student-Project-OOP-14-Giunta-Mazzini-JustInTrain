package com.example.lisamazzini.train_app.achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;

/**
 * Interfaccia che identifica il comportamento di ogni achievement
 *
 * @author lisamazzini
 */
public interface IAchievement {
    public void addData(PlainSolution train) throws AchievementException;
}
