package com.example.lisamazzini.train_app.Network;


import com.example.lisamazzini.train_app.model.Constants;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public final class JourneyRestClient {

    private static JourneyAPI REST_CLIENT;

    static {
        setupRestClient();
    }

    private JourneyRestClient() {}

    public static JourneyAPI get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Constants.ROOT)
                .setClient(new OkClient(new OkHttpClient()));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter  = builder.build();
        REST_CLIENT = restAdapter.create(JourneyAPI.class);
    }
}