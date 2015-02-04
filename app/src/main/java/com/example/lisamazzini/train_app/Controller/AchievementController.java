package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Achievement.IAchievement;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Parser.NewTrain;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class AchievementController {

    List<IAchievement> achievements = new LinkedList<>();

    public void updateAchievements(NewTrain train) throws AchievementException {
        for(IAchievement a : achievements){
            a.addData(train);
        }
    }

}
