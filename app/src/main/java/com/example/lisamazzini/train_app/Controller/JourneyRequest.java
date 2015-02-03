package com.example.lisamazzini.train_app.Controller;


import android.util.Log;

import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<Tragitto> {

    public JourneyRequest() {
        super(Tragitto.class);

    }

    @Override
    public Tragitto loadDataFromNetwork() throws Exception {
//        JourneyRestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00", new Callback<Tragitto>() {
//            @Override
//            public void success(Tragitto tragitto, Response response) {
//                Log.d("cazzi", "1");
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("cazzi", "" + error.getMessage());
//            }
//        });
//        Log.d("cazzi", "2");
        Log.d("cazzi", "loaddata");
        return JourneyRestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00");

    }

}
