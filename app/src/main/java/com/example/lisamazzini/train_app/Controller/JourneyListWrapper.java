package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.JourneyTrain;

import java.util.ArrayList;
import java.util.List;

public class JourneyListWrapper extends ArrayList<JourneyTrain> {

    private ArrayList<JourneyTrain> journeyTrain;
    private int timeSlot;

    public JourneyListWrapper(ArrayList<JourneyTrain> list, int timeSlot) {
        this.journeyTrain = list;
        this.timeSlot = timeSlot;
    }

    public ArrayList<JourneyTrain> getList() {
        return this.journeyTrain;
    }

    public int getTimeSlot() {
        return this.timeSlot-1;
    }

}
