package com.example.lisamazzini.train_app.Parser;



import retrofit.*;
import retrofit.client.Response;
import retrofit.http.*;


/**
 * Created by lisamazzini on 02/02/15.
 */
public interface InterfacciaAPI {

    @GET("/andamentoTreno/{codice}/{numero}")
    void getTrain(@Path("numero") String numero, @Path("codice") String codice, Callback<NewTrain> cb);

}
