package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Parser.TrainDetailsParser;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by lisamazzini on 01/02/15.
 */
public class DoubleTrainRequest extends SpiceRequest<Train> {

    private final String searchNumber;
    private final String searchStationCode;


    public DoubleTrainRequest(String searchNumber, String searchStationCode){
        super(Train.class);
        this.searchNumber = searchNumber;
        this.searchStationCode = searchStationCode;
    }

    @Override
    public Train loadDataFromNetwork() throws Exception {
        Log.d("--------------------------", "sono qui nel loaddata");
        final TrainDetailsParser scraperTrain = new TrainDetailsParser(this.searchNumber);
        Train train = scraperTrain.computeChoiceResult(searchStationCode);
        return train;
    }
}
