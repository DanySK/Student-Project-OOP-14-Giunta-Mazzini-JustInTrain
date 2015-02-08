package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<Tragitto> {

    private final String departureID;
    private final String arrivalID;
    private final String requestedTime;


    public JourneyRequest(String departureID, String arrivalID, String requestedTime) {
        super(Tragitto.class);
        this.departureID = departureID;
        this.arrivalID = arrivalID;
        this.requestedTime = requestedTime;
    }

    @Override
    public Tragitto loadDataFromNetwork() throws Exception {
        Tragitto tragitto = JourneyRestClient.get().getJourneys(departureID, arrivalID, requestedTime);
        return tragitto;
    }

}
