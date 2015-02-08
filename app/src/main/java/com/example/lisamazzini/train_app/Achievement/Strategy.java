package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

/**
 * Created by lisamazzini on 08/02/15.
 */
public interface Strategy {

    public Long compute(PlainSolution train, Long value);

    public void control(Long value) throws AchievementException;

    public String getKey();
}
