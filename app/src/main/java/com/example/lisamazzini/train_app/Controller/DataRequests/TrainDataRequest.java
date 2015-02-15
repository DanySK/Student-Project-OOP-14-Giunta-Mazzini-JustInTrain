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


    /**
     * Metodo che genera l'URL a cui connettersi per ottenere le informazioni
     * @return
     * @throws MalformedURLException
     */
    @Override
    protected URL generateURL() throws MalformedURLException {
        return Utilities.generateTrainAutocompleteURL(this.trainNumber);
    }

    /**
     * Metodo che controlla che la Request sia andata a buon fine; se il risultato preso
     * dalla pagina sottoforma di lista di stringhe è vuoto, significa che la pagina era vuota
     * quindi il numero inserito non corrisponde a nessun treno.
     * @param result
     * @throws InvalidTrainNumberException
     */
    @Override
    protected void check(List result) throws InvalidTrainNumberException {
        if(result.size()==0) {
            throw new InvalidTrainNumberException();
        }
    }
}
