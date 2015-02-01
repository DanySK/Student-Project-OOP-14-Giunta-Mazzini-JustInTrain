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

public class TrainRequest extends SpiceRequest<Train> {

    private final String searchNumber;

    public TrainRequest(String searchQuery){
        super(Train.class);
        this.searchNumber = searchQuery;
    }

    @Override
    public Train loadDataFromNetwork() throws DeletedTrainException, DoubleTrainNumberException, InvalidTrainNumberException, ParseException, IOException {
        final TrainDetailsParser scraperTrain = new TrainDetailsParser(this.searchNumber);
        return scraperTrain.computeResult();
    }
}
