package com.example.lisamazzini.train_app.Controller;

import android.content.Context;
import com.example.lisamazzini.train_app.Achievement.DelayAchievement1;
import com.example.lisamazzini.train_app.Achievement.IAchievement;
import com.example.lisamazzini.train_app.Achievement.PinAchievement1;
import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class AchievementController {

    private final List<IAchievement> achievements = new LinkedList<>();
    private final Context context;

    public AchievementController(Context context){
        this.context = context;
        achievements.add(new DelayAchievement1(context));
        achievements.add(new PinAchievement1(context));
    }

    public void addAchievement(IAchievement achievement){
        achievements.add(achievement);
    }

    public void updateAchievements(PlainSolution train) throws AchievementException {
        for(IAchievement a : achievements){
            a.addData(train);
        }
    }

}
