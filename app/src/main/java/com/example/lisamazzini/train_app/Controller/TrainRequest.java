package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.DeletedTrainException;
import com.example.lisamazzini.train_app.Exceptions.DoubleTrainNumberException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Parser.TrainDetailsParser;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.text.ParseException;

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
    public Train loadDataFromNetwork() throws DeletedTrainException, DoubleTrainNumberException, InvalidTrainNumberException, ParseException, IOException {
        Log.d("--------------------------", "sono qui nel loaddata");
        final TrainDetailsParser scraperTrain = new TrainDetailsParser(this.searchQuery);
        Train train = scraperTrain.computeResult();
        return train;
    }
}
