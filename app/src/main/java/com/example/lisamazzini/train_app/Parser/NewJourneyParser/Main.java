package com.example.lisamazzini.train_app.Parser.NewJourneyParser;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main {


    public static void main(String[] args) {
        RestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00", new Callback<JourneyResponse>() {
            @Override
            public void success(JourneyResponse journeyResponse, Response response) {
                System.out.println(journeyResponse.getSoluzioni().get(0).getDurata());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("erore");
            }
        });
    }
}
