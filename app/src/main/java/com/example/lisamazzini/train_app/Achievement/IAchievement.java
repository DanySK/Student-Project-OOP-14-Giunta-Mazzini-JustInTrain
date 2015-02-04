package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Parser.NewTrain;

/**
 * Created by lisamazzini on 04/02/15.
 */
public interface IAchievement {
    public void addData(NewTrain train) throws AchievementException;
}
