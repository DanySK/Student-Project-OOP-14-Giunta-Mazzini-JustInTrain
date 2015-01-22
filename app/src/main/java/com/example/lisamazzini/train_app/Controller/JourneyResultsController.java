package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Journey;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.JsoupPlannedJourney;
import com.example.lisamazzini.train_app.JsoupPlannedJourneyRequest;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.text.ParseException;

/**
 * Controller class for JourneyResultsActivity.
 * Contains methods which select the current timeslot and make requests
 * to the parser.
 */

public class JourneyResultsController {

    private CyclicCounter timeSlotSelector;
    private int currentTimeSlot;
    private final String departure;
    private final String arrival;
    private final List<Journey> journeyList = new LinkedList<>();
    private final List<List<Journey>> listOfJourneyList = new ArrayList<>(Constants.N_TIME_SLOT);


    public JourneyResultsController(String departure, String arrival) {
        try {
            this.timeSlotSelector = new CyclicCounter(setCurrentTimeSlot(), Constants.N_TIME_SLOT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listOfJourneyList.size(); i++) {
            listOfJourneyList.add(new LinkedList<Journey>());
        }
        this.departure = departure;
        this.arrival = arrival;
    }

    public JsoupPlannedJourneyRequest iterateTimeSlots() {
        JsoupPlannedJourneyRequest request = new JsoupPlannedJourneyRequest(this.timeSlotSelector.value(), new JsoupPlannedJourney(),
                                                                            this.currentTimeSlot, this.departure, this.arrival);
        timeSlotSelector.increment();
        return request;
    }


    private int setCurrentTimeSlot() throws ParseException {
        // dicotomic search on timeslots
        if (TimeSlots.NOW.isMinorThan(TimeSlots.AFTERNOON)) {               // before 13:00
            if (TimeSlots.NOW.isMinorThan(TimeSlots.MORNING)) {
                return this.currentTimeSlot = TimeSlots.EARLY_MORNING.getIndex();  // before 6:00
            } else {
                return this.currentTimeSlot = TimeSlots.MORNING.getIndex();        // after 6:00
            }
        } else {                                                            // after or at 13:00
            if (TimeSlots.NOW.isMinorThan(TimeSlots.EVENING)) {
                return this.currentTimeSlot = TimeSlots.AFTERNOON.getIndex();      // before 18:00
            } else if (!TimeSlots.NOW.isMinorThan(TimeSlots.NIGHT)) {
                return this.currentTimeSlot = TimeSlots.NIGHT.getIndex();          // after 22:00
            } else {
                return this.currentTimeSlot = TimeSlots.EVENING.getIndex();        // between 18:00 and 22:00
            }
        }
    }

    private class CyclicCounter {
        private int counter;
        private final int max;

        public CyclicCounter(int startValue,int max) {
            this.counter = startValue;
            this.max = max;
        }

        public void increment() {
            if (this.counter == this.max) {
                this.counter = this.max - 1;
            } else if (this.counter < this.max) {
                this.counter--;
            } else {
                this.counter++;
            }
        }

        public int value() {
            return this.counter;
        }
    }
}

//  TODO adder fai controller separato con strategy

