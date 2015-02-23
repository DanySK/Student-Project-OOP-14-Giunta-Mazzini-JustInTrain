package com.example.lisamazzini.train_app.network.data;

import com.example.lisamazzini.train_app.exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.model.Utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Prima Request da effettuare, per ottenere, dato un numero di treno, le informazioni riguardanti
 * la stazione di origine.
 *
 * @author lisamazzini
 */

public class TrainDataRequest extends AbstractDataRequest {

    private final String trainNumber;

    /**
     * Costruttore.
     * @param pTrainNumber il numero del treno
     */
    public TrainDataRequest(final String pTrainNumber) {
        super(ListWrapper.class);
        this.trainNumber = pTrainNumber;
    }


    @Override
    protected final URL generateURL() throws MalformedURLException {
        return Utilities.generateTrainAutocompleteURL(this.trainNumber);
    }

    @Override
    protected final void check(final List result) throws InvalidTrainNumberException {
        if (result.isEmpty()) {
            throw new InvalidTrainNumberException();
        }
    }
}
