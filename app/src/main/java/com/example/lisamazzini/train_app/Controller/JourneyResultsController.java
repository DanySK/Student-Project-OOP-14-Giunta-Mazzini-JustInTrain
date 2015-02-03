//package com.example.lisamazzini.train_app.Controller;
//
//import com.example.lisamazzini.train_app.Model.Constants;
//import com.example.lisamazzini.train_app.Model.JourneyTrain;
//import com.example.lisamazzini.train_app.Model.TimeSlots;
//import com.example.lisamazzini.train_app.Parser.OldParsers.JourneyResultsParser;
//
//import org.joda.time.Minutes;
//
//import java.util.List;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.text.ParseException;
//
///**
// * Controller class for JourneyResultsActivity.
// * Contains methods which
// * select the current timeslot,
// * make requests to the parser,
// * helps getting data and refilling the listview dataset.
// */
//
//public class JourneyResultsController {
//
//    private CyclicCounter timeSlotSelector;
//    private int currentTimeSlot;
//    private boolean parsedCurrentTimeSlot;
//    private final String departure;
//    private final String arrival;
//    private final List<List<JourneyTrain>> listOfJourneyList = new ArrayList<>();
//
//
//    public JourneyResultsController(String departure, String arrival) {
//        try {
//            this.timeSlotSelector = new CyclicCounter(setCurrentTimeSlot());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < Constants.N_TIME_SLOT; i++) {
//            listOfJourneyList.add(new LinkedList<JourneyTrain>());
//        }
//        this.departure = departure;
//        this.arrival = arrival;
//    }
//
//    public JourneyRequest iterateTimeSlots() {
//        JourneyRequest request = new JourneyRequest(new JourneyResultsParser(this.departure, this.arrival, this.timeSlotSelector.value(), this.currentTimeSlot, "24", "01", "2015"));
//        timeSlotSelector.increment();
//        return request;
//    }
//
//
//    private int setCurrentTimeSlot() throws ParseException {
//        if (nowIsMinorThan(TimeSlots.AFTERNOON)) {                                  // before 13:00
//            if (nowIsMinorThan(TimeSlots.MORNING)) {
//                return this.currentTimeSlot = TimeSlots.EARLY_MORNING.getIndex();   // before 6:00
//            } else {
//                return this.currentTimeSlot = TimeSlots.MORNING.getIndex();         // after 6:00
//            }
//        } else {                                                                    // after or at 13:00
//            if (nowIsMinorThan(TimeSlots.EVENING)) {
//                return this.currentTimeSlot = TimeSlots.AFTERNOON.getIndex();       // before 18:00
//            } else if (!nowIsMinorThan(TimeSlots.NIGHT)) {
//                return this.currentTimeSlot = TimeSlots.NIGHT.getIndex();           // after 22:00
//            } else {
//                return this.currentTimeSlot = TimeSlots.EVENING.getIndex();         // between 18:00 and 22:00
//            }
//        }
//    }
//
//    private boolean nowIsMinorThan(TimeSlots other) {
//        return Minutes.minutesBetween(TimeSlots.NOW.getDateTime(), other.getDateTime()).getMinutes() > 0;
//    }
//
//    public boolean newDataIsPresent(List<JourneyTrain> list) {
//        return list.size() > 0;
//    }
//
//    public List<JourneyTrain> refillJourneyList(List<JourneyTrain> flatJourneyTrainList, List<JourneyTrain> journeys, int timeSlot) {
//        listOfJourneyList.get(timeSlot).addAll(journeys);
//        if (timeSlot == currentTimeSlot-1) {
//            parsedCurrentTimeSlot = true;
//        }
//
//        if (parsedCurrentTimeSlot) {
//            flatJourneyTrainList.clear();
//            for (List<JourneyTrain> l : listOfJourneyList) {
//                flatJourneyTrainList.addAll(l);
//            }
//        }
//        return flatJourneyTrainList;
//    }
//
//    public boolean newDataisInsertedBefore(JourneyListWrapper journeys) {
//        return this.currentTimeSlot > journeys.getTimeSlot()+1;
//    }
//
//    public JourneyTrain getFirstTakeableTrain(JourneyListWrapper journeys) {
//        return this.currentTimeSlot == journeys.getTimeSlot()+1 ? journeys.getList().get(0) : null;
//    }
//
//    private class CyclicCounter {
//        private int counter;
//        private final int max;
//
//        public CyclicCounter(int startValue) {
//            this.counter = startValue;
//            this.max = startValue;
//        }
//
//        public void increment() {
//            if (this.counter == Constants.N_TIME_SLOT) {
//                this.counter = this.max - 1;
//            } else if (this.counter < this.max) {
//                this.counter--;
//            } else {
//                this.counter++;
//            }
//        }
//
//        public int value() {
//            return this.counter;
//        }
//    }
//}
//
//
//
