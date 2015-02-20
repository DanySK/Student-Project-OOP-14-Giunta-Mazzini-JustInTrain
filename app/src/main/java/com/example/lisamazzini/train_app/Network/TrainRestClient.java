package com.example.lisamazzini.train_app.network;

import com.example.lisamazzini.train_app.model.Constants;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Il rest client per eseguire la richiesta riguardante i treni.
 *
 * @author albertogiunta
 * @author lisamazzini
 */
public final class TrainRestClient {

        private static TrainAPI restClient;

        static {
            setupRestClient();
        }

        private TrainRestClient() { }

        /**
         * Getter per il restClient.
         * @return la richiesta TrainApi
         */
        public static TrainAPI get() {
            return restClient;
        }

        private static void setupRestClient() {
            final RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(Constants.ROOT)
                    .setClient(new OkClient(new OkHttpClient()));
            builder.setLogLevel(RestAdapter.LogLevel.FULL);

            final RestAdapter restAdapter = builder.build();
            restClient = restAdapter.create(TrainAPI.class);
    }
}