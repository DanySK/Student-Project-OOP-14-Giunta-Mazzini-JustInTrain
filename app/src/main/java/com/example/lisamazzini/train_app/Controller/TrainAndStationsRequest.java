package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.DeletedTrainException;
import com.example.lisamazzini.train_app.Exceptions.DoubleTrainNumberException;
import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Train;
import com.example.lisamazzini.train_app.Parser.StationListParser;
import com.example.lisamazzini.train_app.Parser.TrainDetailsParser;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.text.ParseException;

public class TrainAndStationsRequest extends SpiceRequest<Train> {

    private final String searchQuery;

    public TrainAndStationsRequest(String searchQuery){
        super(Train.class);
        this.searchQuery = searchQuery;
    }

    @Override
    public Train loadDataFromNetwork() throws DeletedTrainException, DoubleTrainNumberException, InvalidTrainNumberException, ParseException, IOException {
        final TrainDetailsParser trainDetailsParser = new TrainDetailsParser(this.searchQuery);
        final StationListParser stationListParser = new StationListParser(this.searchQuery);

        Train train = trainDetailsParser.computeResult();
        // TODO bisogna uniformare stationlistparser a traindetailsparser (ovvero uno fa i getter da un oggetto, l'altro direttamente dal parser!!!)
        stationListParser.computeResult();
        return new Train.TrainBuilder(train.getCategory(), train.getNumber(), train.isMoving(), train.getDelay(), train.getBirthStation(),
                train.getDeathStation(), train.getLastSeenStation(), train.getLastSeenTime()).withStationList(stationListParser.getStationList()).withProgress(stationListParser.getProgress()).build();
    }
}