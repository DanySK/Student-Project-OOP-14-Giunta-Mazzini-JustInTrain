package com.example.lisamazzini.train_app.Older;

import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;


public class JsoupPlannedJourneyRequest extends SpiceRequest<ListJourney> {

    int radio;
    JsoupPlannedJourney journey = new JsoupPlannedJourney();
    int timeSlot;
    String departure;
    String arrival;

    public JsoupPlannedJourneyRequest(int radio, JsoupPlannedJourney journey, int timeslot, String departure, String arrival) {
        super(ListJourney.class);
        this.radio = radio;
        this.journey = journey;
        this.timeSlot = timeslot;
        this.departure = departure;
        this.arrival = arrival;
    }

//    public JsoupPlannedJourneyRequest(int radio) {
//        super(ListJourney.class);
//        this.radio = radio;
//    }


    @Override
    public ListJourney loadDataFromNetwork() {
        Log.d("CAZZI", "loadDataFromNetwork 1 " + radio);
        if (radio == timeSlot) {
            journey.computeResult(radio, timeSlot, departure, arrival);
        } else {
            journey.computeResult(radio, departure, arrival);
        }
        return journey.getJourneysList();
    }

}
