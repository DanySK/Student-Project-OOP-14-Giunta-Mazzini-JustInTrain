package com.example.lisamazzini.train_app.network.data;

import com.example.lisamazzini.train_app.exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.model.Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Prima Request da effettuare, per ottenere il codice di una data stazione
 * o eventualmente tutte le stazioni corrispondenti alla stringa data.
 *
 * @author albertogiunta
 */
public class JourneyDataRequest extends AbstractDataRequest {

    private final String station;

    /**
     * Costruttore.
     * @param pStation la stazione con cui effettuare la richiesta
     */
    public JourneyDataRequest(final String pStation) {
        super(ListWrapper.class);
        this.station = pStation;
    }

    @Override
    protected final URL generateURL() throws MalformedURLException {
        return Utilities.generateStationAutocompleteURL(this.station);
    }

    @Override
    protected final void check(final List result) throws InvalidStationException {
        if (result.isEmpty()) {
            throw new InvalidStationException();
        }
    }
}