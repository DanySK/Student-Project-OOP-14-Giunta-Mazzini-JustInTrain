package com.example.lisamazzini.train_app.Network;



import com.example.lisamazzini.train_app.Model.NewTrain;

import retrofit.http.*;


/**
 * Created by lisamazzini on 02/02/15.
 */
public interface InterfacciaAPI {

    @GET("/andamentoTreno/{codice}/{numero}")
    NewTrain getTrain(@Path("numero") String numero, @Path("codice") String codice);

}
