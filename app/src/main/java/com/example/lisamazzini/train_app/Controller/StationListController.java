package com.example.lisamazzini.train_app.Controller;

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
        final String[] result = new String[3];
        result[0] = data.split("\\|")[1].split("-")[0];    //numero
        result[1] = data.split("\\|")[1].split("-")[1];    //codice
        result[2] = data.split("\\|")[0].split("-")[1];    //nome
        return result;
    }

    public String[] computeChoices(String[] first, String[] second){
        final String[] result = new String[2];
        result[0] = "Treno " + first[0] + " in partenza da " + first[2];
        result[1] = "Treno " + second[0] + " in partenza da " + second[2];
        return result;
    }
}