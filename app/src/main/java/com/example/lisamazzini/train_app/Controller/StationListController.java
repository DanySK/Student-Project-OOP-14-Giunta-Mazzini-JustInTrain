package com.example.lisamazzini.train_app.Controller;

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

    public String[] computeChoices(String[] first, String[] second){
        final String[] result = new String[2];
        result[0] = "Treno " + first[0] + " in partenza da " + first[2];
        result[1] = "Treno " + second[0] + " in partenza da " + second[2];
        return result;
    }
}