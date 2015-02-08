package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import android.content.Context;
/**
 * Created by lisamazzini on 08/02/15.
 */
public class PinAchievement1 extends BasicAchievement {
    public PinAchievement1(Context context) {
        super(0L, new Strategy() {
            @Override
            public Long compute(PlainSolution train, Long value) {
                return value + 1;
            }

            @Override
            public void control(Long value) throws AchievementException {
                if(value == 4L){
                    throw new PinAchievementException("Hai pinnato 5 treni!!");
                }
            }

            @Override
            public String getKey() {
                return "Pin";
            }
        }, context );
    }
}
