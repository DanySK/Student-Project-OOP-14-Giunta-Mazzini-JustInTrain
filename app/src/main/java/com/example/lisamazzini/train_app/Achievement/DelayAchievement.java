package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.DelayAchievementException;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;

/**
 * Created by lisamazzini on 04/02/15.
 */


//Singleton + strategy (?)
public class DelayAchievement implements IAchievement {

    private Integer totalDelay = 0;


    @Override
    public void addData(PlainSolution train) throws AchievementException {
        this.totalDelay += Integer.parseInt(train.getDelay());
        check();
    }

    private void check() throws DelayAchievementException {
        if(this.totalDelay >= 10){
            throw new DelayAchievementException();
        }
    }
}
