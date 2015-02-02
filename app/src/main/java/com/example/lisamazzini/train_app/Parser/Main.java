package com.example.lisamazzini.train_app.Parser;

import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Main {


    public static void main(String[] args) {
        RestClient.get().getJourneys(new Callback<JourneyResponse>() {
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
