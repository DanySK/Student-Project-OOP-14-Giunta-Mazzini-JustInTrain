package com.example.lisamazzini.train_app.Parser;

import retrofit.Callback;
import retrofit.http.GET;

public interface API {

    @GET("/soluzioniViaggioNew/7104/5066/2015-02-01T00:00:00")
    void getJourneys(Callback<JourneyResponse> callback);
}
