package com.example.lisamazzini.train_app.network.data;

import com.example.lisamazzini.train_app.exceptions.InvalidInputException;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.model.Utilities;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Classe astratta per fare richieste di tipo "autocomplete", ovvero quando si chiedono al server
 * informazioni preliminari riguardo il numero treno (quindi se ci sono più treni con lo stesso numero, e gli id della stazione di partenza)
 * o una stazione (e quindi il suo id).
 * Implementa la load data from network, che è uguale per tutte le richieste di questo tipo.
 *
 * @author albertogiunta
 */
public abstract class AbstractDataRequest extends SpiceRequest<ListWrapper> {

    /**
     * Costuttore.
     * @param clazz listWrapper di string
     */
    public AbstractDataRequest(final Class<ListWrapper> clazz) {
        super(clazz);
    }

    /**
     * Non chiamare questo metodo esplicitamente, viene chiamato automaticamente da robospice.
     *
     * @return ListWrapper di stringhe (ogni elemento è una riga stazione|codice
     * @throws IOException IOException
     * @throws InvalidInputException InvalidInputException
     */
    public final ListWrapper loadDataFromNetwork() throws IOException, InvalidInputException {
        final ListWrapper result = Utilities.fetchData(generateURL());
        check(result.getList());
        return result;
    }

    /**
     * Genera l'url con cui poi fare le ricerche di tipo "autocomplete".
     *
     * @return URL: url con cui effettuare le ricerche
     * @throws MalformedURLException MalformedURLException
     */
    protected abstract URL generateURL() throws MalformedURLException;

    /**
     * Metodo che controlla che la Request sia andata a buon fine; se il risultato preso
     * dalla pagina sottoforma di lista di stringhe è vuoto, significa che la pagina era vuota
     * quindi il numero inserito non corrisponde a nessun treno (idem per stazioni).
     *
     * @param result List<String> contenenete i risultati della loadDataFromNetwork
     * @throws com.example.lisamazzini.train_app.exceptions.InvalidTrainNumberException InvalidTrainNumberException
     */
    protected abstract void check(List<String> result) throws InvalidInputException;

}