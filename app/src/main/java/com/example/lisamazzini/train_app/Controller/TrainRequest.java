package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.Network.RestClientTrain;
import com.octo.android.robospice.request.SpiceRequest;
/*
/**
 * Created by lisamazzini on 02/02/15.
 */


public class TrainRequest extends SpiceRequest<NewTrain> {

    private final String code;
    private final String number;

    public TrainRequest(String number, String code){
        super(NewTrain.class);
        this.code = code;
        this.number = number;
    }

    @Override
    public NewTrain loadDataFromNetwork() throws Exception {
        return RestClientTrain.get().getTrain(this.number,this.code);
    }

}
