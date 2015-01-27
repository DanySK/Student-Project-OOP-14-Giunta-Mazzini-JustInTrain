package com.example.lisamazzini.train_app.Controller;


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
