package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

/**
 * Created by lisamazzini on 04/02/15.
 */
public interface IAchievement {
    public void addData(PlainSolution train) throws AchievementException;
}
