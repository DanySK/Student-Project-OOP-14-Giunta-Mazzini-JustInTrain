//package com.example.lisamazzini.train_app.Controller;
//
//import com.example.lisamazzini.train_app.Model.Train;
//import com.example.lisamazzini.train_app.Parser.OldParsers.TrainDetailsParser;
//import com.octo.android.robospice.request.SpiceRequest;
//
//public class DoubleTrainRequest extends SpiceRequest<Train> {
//
//    private final String searchNumber;
//    private final String searchStationCode;
//
//
//    public DoubleTrainRequest(String searchNumber, String searchStationCode){
//        super(Train.class);
//        this.searchNumber = searchNumber;
//        this.searchStationCode = searchStationCode;
//    }
//
//    @Override
//    public Train loadDataFromNetwork() throws Exception {
//        final TrainDetailsParser scraperTrain = new TrainDetailsParser(this.searchNumber);
//        return scraperTrain.computeChoiceResult(searchStationCode);
//    }
//}
