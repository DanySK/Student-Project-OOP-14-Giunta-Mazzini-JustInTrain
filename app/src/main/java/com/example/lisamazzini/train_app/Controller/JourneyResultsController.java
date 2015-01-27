package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.JourneyTrain;
import com.example.lisamazzini.train_app.Model.TimeSlots;
import com.example.lisamazzini.train_app.Parser.JourneyResultsParser;

import org.joda.time.Minutes;
import org.joda.time.Period;

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
    private final List<JourneyTrain> journeyList = new LinkedList<>();
    private final List<List<JourneyTrain>> listOfJourneyList = new ArrayList<>(Constants.N_TIME_SLOT);


    public JourneyResultsController(String departure, String arrival) {
        try {
            this.timeSlotSelector = new CyclicCounter(setCurrentTimeSlot());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // TEST metto il current al primo radio buton
//        this.timeSlotSelector = new CyclicCounter(3);
//        this.currentTimeSlot = 3;



        for (int i = 0; i < listOfJourneyList.size(); i++) {
            listOfJourneyList.add(new LinkedList<JourneyTrain>());
        }
        this.departure = departure;
        this.arrival = arrival;
    }

    public JourneyRequest iterateTimeSlots() {
        Log.d("cazzi", "TIMESLOTSELECONT = " + this.timeSlotSelector.value());
        JourneyRequest request = new JourneyRequest(new JourneyResultsParser(this.departure, this.arrival, this.timeSlotSelector.value(), this.currentTimeSlot, "24", "01", "2015"));
        timeSlotSelector.increment();
        return request;
    }


    private int setCurrentTimeSlot() throws ParseException {
        // dicotomic search on timeslots
        if (nowIsMinorThan(TimeSlots.AFTERNOON)) {                       // before 13:00
            if (nowIsMinorThan(TimeSlots.MORNING)) {
                return this.currentTimeSlot = TimeSlots.EARLY_MORNING.getIndex();   // before 6:00
            } else {
                return this.currentTimeSlot = TimeSlots.MORNING.getIndex();         // after 6:00
            }
        } else {                                                                    // after or at 13:00
            if (nowIsMinorThan(TimeSlots.EVENING)) {
                return this.currentTimeSlot = TimeSlots.AFTERNOON.getIndex();       // before 18:00
            } else if (!nowIsMinorThan(TimeSlots.NIGHT)) {
                return this.currentTimeSlot = TimeSlots.NIGHT.getIndex();           // after 22:00
            } else {
                return this.currentTimeSlot = TimeSlots.EVENING.getIndex();         // between 18:00 and 22:00
            }
        }
    }

    private boolean nowIsMinorThan(TimeSlots other) {
        return Minutes.minutesBetween(TimeSlots.NOW.getDateTime(), other.getDateTime()).getMinutes() > 0;
    }

    // TODO è provvisorio, poi non servirà perchè qui ci sarà un metodo che lo fa al posto dell'activity
    public int getCurrentTimeSlot() {
        return this.currentTimeSlot;
    }

    private class CyclicCounter {
        private int counter;
        private final int max;

        public CyclicCounter(int startValue) {
            this.counter = startValue;
            this.max = startValue;
        }

        public void increment() {
            if (this.counter == Constants.N_TIME_SLOT) {
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



