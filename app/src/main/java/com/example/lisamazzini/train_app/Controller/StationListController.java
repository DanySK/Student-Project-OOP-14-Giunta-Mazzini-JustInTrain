package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Older.TrainAndStationsRequest;

/**
 * Created by lisamazzini on 22/01/15.
 */
public class StationListController {

    private final String trainNumber;

    public StationListController(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public TrainAndStationsRequest getRequest(){
        return new TrainAndStationsRequest(this.trainNumber);
    }



}
