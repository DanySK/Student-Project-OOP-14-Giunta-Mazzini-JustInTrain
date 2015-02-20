package com.example.lisamazzini.train_app.Network;


import com.example.lisamazzini.train_app.model.tragitto.Tragitto;

import retrofit.http.GET;
import retrofit.http.Path;

public interface JourneyAPI {
    @GET("/soluzioniViaggioNew/{depId}/{arrId}/{datetime}")
    Tragitto getJourneys(@Path("depId") String depId, @Path("arrId") String arrId, @Path("datetime") String datetime);
}
