package com.example.lisamazzini.train_app.Controller;

public class StationListController {

    private final String trainNumber;

    public StationListController(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public TrainAndStationsRequest getRequest(){
        return new TrainAndStationsRequest(this.trainNumber);
    }
}