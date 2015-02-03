package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Parser.InterfacciaAPI;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.Parser.RestClientTrain;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
/*
/**
 * Created by lisamazzini on 02/02/15.
 */


public class TrainRequest extends SpiceRequest<NewTrain> {

    String[] datas;

    public TrainRequest(String[] datas){
        super(NewTrain.class);
        this.datas = datas;
    }

    @Override
    public NewTrain loadDataFromNetwork() throws Exception {
        return RestClientTrain.get().getTrain(datas[0],datas[1]);
    }

}
