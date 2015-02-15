package com.example.lisamazzini.train_app.Controller.TotalRequests;

import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Network.TrainRestClient;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Request che dato numero del treno e codice della stazione di origine, si connette
 * alla pagina da cui prendere i dati
 *
 * @author lisamazzini
 */
public class TrainRequest extends SpiceRequest<Train> {

    private final String code;
    private final String number;

    public TrainRequest(String number, String code){
        super(Train.class);
        this.code = code;
        this.number = number;
    }

    @Override
    public Train loadDataFromNetwork() throws Exception {
        return TrainRestClient.get().getTrain(this.number,this.code);
    }
}
