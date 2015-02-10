package com.example.lisamazzini.train_app.Achievement;

import com.example.lisamazzini.train_app.Exceptions.AchievementException;
import com.example.lisamazzini.train_app.Exceptions.PinAchievementException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import android.content.Context;
import android.util.Log;

/**
 * Created by lisamazzini on 08/02/15.
 */
public class PinAchievement1 extends BasicAchievement {
    public PinAchievement1(Context context) {
        super(0L, new Strategy() {
            @Override
            public Long compute(PlainSolution train, Long value) {
                Log.d("Pin", "ppppoonnnnn" + value);
                return value + 1L;
            }
            @Override
            public void control(Long value) throws AchievementException {
                if(value == 5L){
                    Log.d("Pin", "ppppinnnnn");
                    throw new PinAchievementException(Constants.PIN_ACH);
                }
            }
            @Override
            public String getKey() {
                return "Pin";
            }
        }, context );
    }
}
