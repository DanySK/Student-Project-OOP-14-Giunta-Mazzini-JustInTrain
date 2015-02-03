package com.example.lisamazzini.train_app.Controller;


import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.TimeSlots;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;

import org.joda.time.Minutes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JourneyResultsController2 {

    private final String departure;
    private final String arrival;
    private final String date;
    private final String time;

    private final List<List<Soluzioni>> listOfJourneyList = new ArrayList<>();


    public JourneyResultsController2(String departure, String arrival, String date, String time) {
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
        for (int i = 0; i < Constants.N_TIME_SLOT; i++) {
            listOfJourneyList.add(new LinkedList<Soluzioni>());
        }
    }

    public List<Soluzioni> refillJourneyList(List<Soluzioni> flatJourneyTrainList, List<Soluzioni> journeys) {
        flatJourneyTrainList.clear();
        flatJourneyTrainList.addAll(journeys);
        return flatJourneyTrainList;
    }
}