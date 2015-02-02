package com.example.lisamazzini.train_app.Parser.NewJourneyParser;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface API {

    @GET("/soluzioniViaggioNew/{depId}/{arrId}/{datetime}")
    void getJourneys(@Path("depId") String depId, @Path("arrId") String arrId, @Path("datetime") String datetime, Callback<JourneyResponse> callback);
}
