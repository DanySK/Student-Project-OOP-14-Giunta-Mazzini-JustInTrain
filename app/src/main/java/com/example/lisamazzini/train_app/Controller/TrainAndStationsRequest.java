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

/**
 * Created by lisamazzini on 21/01/15.
 */
public class TrainAndStationsRequest extends SpiceRequest<Train> {

        private final String searchQuery;
        private Train.TrainBuilder builder;

        public TrainAndStationsRequest(String searchQuery){
            super(Train.class);
            this.searchQuery = searchQuery;
        }

        @Override
        public Train loadDataFromNetwork() throws DeletedTrainException, DoubleTrainNumberException, InvalidTrainNumberException, ParseException, IOException {
            Log.d("--------------------------", "sono qui nel loaddata");
            final TrainDetailsParser scraperTrain = new TrainDetailsParser(this.searchQuery);
            StationListParser scraperJourney = new StationListParser(this.searchQuery);

            Train train = null;

            train = scraperTrain.computeResult();
            scraperJourney.computeResult();
            Train.TrainBuilder builder = new Train.TrainBuilder(train.getCategory(), train.getNumber(), train.isMoving(), train.getDelay(), train.getBirthStation(),
                 train.getDeathStation(), train.getLastSeenStation(), train.getLastSeenTime());
            train = builder.withStationList(scraperJourney.getStationList()).withProgress(scraperJourney.getProgress()).build();



            return train;
        }
}