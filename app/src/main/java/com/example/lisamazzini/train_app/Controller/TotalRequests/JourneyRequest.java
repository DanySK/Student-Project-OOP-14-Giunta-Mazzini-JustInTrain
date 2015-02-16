package com.example.lisamazzini.train_app.Controller.TotalRequests;

import com.example.lisamazzini.train_app.Exceptions.NoSolutionsAvailableException;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Classe che modella la richiesta dei dati di una tratta (quindi lista di treni disponibili, con orari di arrivo e partenza e dettagli tratta)
 * NON fornisce informazioni riguardo ai dettagli di ciascun treno (ad es. ritardi), per quello fare riferimento
 * alla journeyTrainRequest.
 *
 * @author albertogiunta
 */
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
        if (tragitto.getSoluzioni().size() == 0) {
            throw new NoSolutionsAvailableException();
        }
        return tragitto;
    }

}