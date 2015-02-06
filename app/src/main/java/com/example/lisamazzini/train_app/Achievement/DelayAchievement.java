package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.DelayAchievementException;
import com.example.lisamazzini.train_app.Model.NewTrain;

/**
 * Created by lisamazzini on 04/02/15.
 */


//Singleton + strategy (?)
public class DelayAchievement implements IAchievement {

    private Long totalDelay = 0L;
    DelayAchievement delayAchievement = new DelayAchievement();

    public DelayAchievement getInstance(){
        return this.delayAchievement;
    }

    @Override
    public void addData(NewTrain train) throws AchievementException {
        this.totalDelay += train.getRitardo();
        check();
    }

    private void check() throws DelayAchievementException {
        if(this.totalDelay >= 100L){
            throw new DelayAchievementException();
        }
    }
}
