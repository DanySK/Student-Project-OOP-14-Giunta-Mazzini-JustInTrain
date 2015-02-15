package com.example.lisamazzini.train_app.Controller.DataRequests;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Utilities;

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

    public JourneyDataRequest(String station){
        super(ListWrapper.class);
        this.station = station;
    }

    protected URL generateURL() throws MalformedURLException {
        return Utilities.generateStationAutocompleteURL(this.station);
    }

    protected void check(List result) throws InvalidStationException {
        if(result.size()==0) {
            throw new InvalidStationException();
        }
    }
}