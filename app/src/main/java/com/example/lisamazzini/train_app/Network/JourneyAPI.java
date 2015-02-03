package com.example.lisamazzini.train_app.Network;


import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface JourneyAPI {

    @GET("/soluzioniViaggioNew/{depId}/{arrId}/{datetime}")
    void getJourneys(@Path("depId") String depId, @Path("arrId") String arrId, @Path("datetime") String datetime, Callback<Tragitto> callback);
}
