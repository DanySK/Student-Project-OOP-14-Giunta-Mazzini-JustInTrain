package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Utilities;

public class StationListController {

    private final String trainNumber;
    private String trainCode;

    public StationListController(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public TrainDataRequest getNumberRequest(){
        return new TrainDataRequest(this.trainNumber);
    }

    public void setCode(String code){
        this.trainCode = code;
    }

    public TrainRequest getNumberAndCodeRequest(){
        return new TrainRequest(this.trainNumber, this.trainCode);
    }

    public String[] computeData(String data){
        return Utilities.splitString(data);
    }

    public String computeChoices(String[] first){
        final String result= "Treno " + first[0] + " in partenza da " + first[2];
        return result;
    }

    public String getProgress(Train train){
        return Utilities.getProgress(train);
    }
}