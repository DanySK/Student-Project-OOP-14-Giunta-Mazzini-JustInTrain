package com.example.lisamazzini.train_app.Network;

import com.example.lisamazzini.train_app.Model.Constants;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class TrainRestClient {


        private static TrainAPI REST_CLIENT;

        static {
            setupRestClient();
        }

        private TrainRestClient() {}

        public static TrainAPI get() {
            return REST_CLIENT;
        }

        private static void setupRestClient() {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(Constants.ROOT)
                    .setClient(new OkClient(new OkHttpClient()));
            builder.setLogLevel(RestAdapter.LogLevel.FULL);

            RestAdapter restAdapter = builder.build();
            REST_CLIENT = restAdapter.create(TrainAPI.class);
    }
}
