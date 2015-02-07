package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<Tragitto> {

    private final String departureID;
    private final String arrivalID;


    public JourneyRequest(String departureID, String arrivalID) {
        super(Tragitto.class);
        this.departureID = departureID;
        this.arrivalID = arrivalID;
    }

    @Override
    public Tragitto loadDataFromNetwork() throws Exception {
        Tragitto tragitto = JourneyRestClient.get().getJourneys(departureID, arrivalID, "2015-02-07T00:00:00");


        return tragitto;
    }

}
