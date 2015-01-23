package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Older.JsoupPlannedJourney;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<JourneyListWrapper>{

    private final JsoupPlannedJourney journey;
    private final int timeSlotSelector;
    private final int currentTimeSlot;
    private final String departure;
    private final String arrival;


    public JourneyRequest(JsoupPlannedJourney journey,
                          int timeSlotSelector, int currentTimeSlot,
                          String departure, String arrival) {
        super(JourneyListWrapper.class);
        this.journey = journey;
        this.timeSlotSelector = timeSlotSelector;
        this.currentTimeSlot = currentTimeSlot;
        this.departure = departure;
        this.arrival = arrival;
    }

    @Override
    public JourneyListWrapper loadDataFromNetwork() throws Exception {
        //  TODO fai richiesta a JsoupJourney;
        return null;
    }
}
