package com.example.lisamazzini.train_app;

import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by lisamazzini on 21/01/15.
 */
public class TrainAndStationsRequest extends SpiceRequest<Train> {

        private final String searchQuery;

        public TrainAndStationsRequest(String searchQuery){
            super(Train.class);
            this.searchQuery = searchQuery;
        }

        @Override
        public Train loadDataFromNetwork() throws Exception {
            Log.d("--------------------------", "sono qui nel loaddata");
            final JsoupTrainDetails scraperTrain = new JsoupTrainDetails(this.searchQuery);
            JsoupJourneyDetails scraperJourney = new JsoupJourneyDetails(this.searchQuery);

            Train train = scraperTrain.computeResult();
            try {
                scraperJourney.computeResult();
                train = new Train(train, scraperJourney.getStationList());
                progress = scraperJourney.getProgress();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
    }
    return train;

        }
    }
}
