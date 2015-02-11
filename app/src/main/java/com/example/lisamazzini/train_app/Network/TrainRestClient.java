package com.example.lisamazzini.train_app.Network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class TrainRestClient {


        private static TrainAPI REST_CLIENT_LISA;
        private static String ROOT = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno";

        static {
            setupRestClientLisa();
        }

        private TrainRestClient() {}

        public static TrainAPI get() {
            return REST_CLIENT_LISA;
        }

        private static void setupRestClientLisa() {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ROOT)
                    .setClient(new OkClient(new OkHttpClient()));
            builder.setLogLevel(RestAdapter.LogLevel.FULL);

            RestAdapter restAdapter = builder.build();
            REST_CLIENT_LISA = restAdapter.create(TrainAPI.class);

    }
}
