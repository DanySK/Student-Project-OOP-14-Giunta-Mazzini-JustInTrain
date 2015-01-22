package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.JsoupPlannedJourney;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<JourneyListAdapter>{

    private final JsoupPlannedJourney journey;
    private final int timeSlotSelector;
    private final int currentTimeSlot;
    private final String departure;
    private final String arrival;


    public JourneyRequest(JsoupPlannedJourney journey,
                          int timeSlotSelector, int currentTimeSlot,
                          String departure, String arrival) {
        super(JourneyListAdapter.class);
        this.journey = journey;
        this.timeSlotSelector = timeSlotSelector;
        this.currentTimeSlot = currentTimeSlot;
        this.departure = departure;
        this.arrival = arrival;
    }

    @Override
    public JourneyListAdapter loadDataFromNetwork() throws Exception {
        //  TODO fai richiesta a JsoupJourney;
        return null;
    }
}
