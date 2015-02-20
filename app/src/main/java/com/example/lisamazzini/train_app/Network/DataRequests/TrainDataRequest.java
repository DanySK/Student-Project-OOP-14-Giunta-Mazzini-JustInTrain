package com.example.lisamazzini.train_app.Network.DataRequests;

import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Prima Request da effettuare, per ottenere, dato un numero di treno, le informazioni riguardanti
 * la stazione di origine
 *
 * @author lisamazzini
 */

public class TrainDataRequest extends AbstractDataRequest {

    private final String trainNumber;

    public TrainDataRequest(final String searchQuery){
        super(ListWrapper.class);
        this.trainNumber = searchQuery;
    }


    @Override
    protected URL generateURL() throws MalformedURLException, MalformedURLException {
        return Utilities.generateTrainAutocompleteURL(this.trainNumber);
    }

    @Override
    protected void check(final List result) throws InvalidTrainNumberException {
        if(result.size()==0) {
            throw new InvalidTrainNumberException();
        }
    }
}
