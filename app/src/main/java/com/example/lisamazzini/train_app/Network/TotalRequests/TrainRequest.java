package com.example.lisamazzini.train_app.Network.TotalRequests;

import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.Network.TrainRestClient;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Request che dato numero del treno e codice della stazione di origine, si connette
 * alla pagina da cui prendere i dati
 *
 * @author lisamazzini
 */
public class TrainRequest extends SpiceRequest<Treno> {

    private final String code;
    private final String number;

    public TrainRequest(final String number, final String code){
        super(Treno.class);
        this.code = code;
        this.number = number;
    }

    @Override
    public Treno loadDataFromNetwork() throws Exception {
        return TrainRestClient.get().getTrain(this.number,this.code);
    }
}
