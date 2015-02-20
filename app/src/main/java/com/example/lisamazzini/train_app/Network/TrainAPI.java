package com.example.lisamazzini.train_app.Network;

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

    @GET("/andamentoTreno/{codice}/{numero}")
    Treno getTrain(@Path("numero") String numero, @Path("codice") String codice);

}
