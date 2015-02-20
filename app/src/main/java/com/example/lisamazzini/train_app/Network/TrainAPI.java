package com.example.lisamazzini.train_app.network;

import com.example.lisamazzini.train_app.model.treno.Treno;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interfaccia per la richiesta http per ottenere informazioni riguardo un treno.
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public interface TrainAPI {

    /**
     * Fa una richiesta in GET a un servizio REST in base al relativo url.
     * @param codice codice della stazione di origine del treno
     * @param numero numero del treno
     * @return il risultato della richiesta
     */
    @GET("/andamentoTreno/{codice}/{numero}")

    /**
     * Metodo per eseguire la richiesta con i parametri dati.
     */
    Treno getTrain(@Path("numero") String numero, @Path("codice") String codice);

}
