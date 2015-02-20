package com.example.lisamazzini.train_app.network;


import com.example.lisamazzini.train_app.model.tragitto.Tragitto;

import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Interfaccia per la richiesta http per ottenere informazioni riguardo una tratta.
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public interface JourneyAPI {

    /**
     * Fa una richiesta in GET a un servizio REST in base al relativo url.
     * @param depId id di partenza
     * @param arrId id di arrivo
     * @param datetime orario di ricerca
     * @return il risultato della richiesta
     */
    @GET("/soluzioniViaggioNew/{depId}/{arrId}/{datetime}")

    /**
     * Metodo per eseguire la richiesta con i parametri dati.
     */
    Tragitto getJourneys(@Path("depId") String depId, @Path("arrId") String arrId, @Path("datetime") String datetime);
}
