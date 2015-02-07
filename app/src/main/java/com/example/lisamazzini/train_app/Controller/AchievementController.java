package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Achievement.IAchievement;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class AchievementController {

    List<IAchievement> achievements = new LinkedList<>();

    public void addAchievement(IAchievement achievement){
        achievements.add(achievement);
    }

    public void updateAchievements(PlainSolution train) throws AchievementException {
        for(IAchievement a : achievements){
            a.addData(train);
        }
    }

}
