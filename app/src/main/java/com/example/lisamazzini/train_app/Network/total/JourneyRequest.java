package com.example.lisamazzini.train_app.network.total;

import com.example.lisamazzini.train_app.Exceptions.NoSolutionsAvailableException;
import com.example.lisamazzini.train_app.model.tragitto.Tragitto;
import com.example.lisamazzini.train_app.network.JourneyRestClient;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Classe che modella la richiesta dei dati di una tratta (quindi lista di treni disponibili, con orari di arrivo e partenza e dettagli tratta)
 * NON fornisce informazioni riguardo ai dettagli di ciascun treno (ad es. ritardi), per quello fare riferimento
 * alla journeyTrainRequest.
 *
 * @author albertogiunta
 */
public class JourneyRequest extends SpiceRequest<Tragitto> {

    private static final int EMPTY = 0;

    private final String departureID;
    private final String arrivalID;
    private final String requestedTime;


    /**
     * Costruttore.
     * @param pDepartureID id della stazione di partenza
     * @param pArrivalID id della stazione di arrivo
     * @param pRequestedTime orario di ricerca
     */
    public JourneyRequest(final String pDepartureID, final String pArrivalID, final String pRequestedTime) {
        super(Tragitto.class);
        this.departureID = pDepartureID;
        this.arrivalID = pArrivalID;
        this.requestedTime = pRequestedTime;
    }

    @Override
    public final Tragitto loadDataFromNetwork() throws NoSolutionsAvailableException {
        final Tragitto tragitto = JourneyRestClient.get().getJourneys(departureID, arrivalID, requestedTime);
        if (tragitto.getSoluzioni().size() == EMPTY) {
            throw new NoSolutionsAvailableException();
        }
        return tragitto;
    }

}