package com.example.lisamazzini.train_app.Controller.DataRequests;

import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Utilities;

import java.net.*;
import java.util.List;


/**
 * Prima Request da effettuare, per ottenere, dato un numero di treno, le informazioni riguardanti
 * la stazione di origine
 */

public class TrainDataRequest extends AbstractDataRequest {

    private final String trainNumber;

    public TrainDataRequest(String searchQuery){
        super(ListWrapper.class);
        this.trainNumber = searchQuery;
    }


    @Override
    protected URL generateURL() throws MalformedURLException {
        return Utilities.generateTrainAutocompleteURL(this.trainNumber);
    }

    @Override
    protected void check(List result) throws InvalidTrainNumberException {
        if(result.size()==0) {
            throw new InvalidTrainNumberException();
        }
    }
}
