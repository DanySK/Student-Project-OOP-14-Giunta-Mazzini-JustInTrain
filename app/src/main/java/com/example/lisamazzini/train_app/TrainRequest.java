package com.example.lisamazzini.train_app;

import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by lisamazzini on 21/01/15.
 */
public class TrainRequest extends SpiceRequest<Train> {

    private final String searchQuery;

    public TrainRequest(String searchQuery){
        super(Train.class);
        this.searchQuery = searchQuery;
    }

    @Override
    public Train loadDataFromNetwork() throws Exception {
        Log.d("--------------------------", "sono qui nel loaddata");
        final JsoupTrainDetails scraperTrain = new JsoupTrainDetails(this.searchQuery);
        Train train = scraperTrain.computeResult();
        return train;

    }
}
