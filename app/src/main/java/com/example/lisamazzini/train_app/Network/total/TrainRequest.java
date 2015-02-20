package com.example.lisamazzini.train_app.network.total;

import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.network.TrainRestClient;
import com.octo.android.robospice.request.SpiceRequest;

/**
 * Request che dato numero del treno e codice della stazione di origine, si connette
 * alla pagina da cui prendere i dati.
 *
 * @author lisamazzini
 */
public class TrainRequest extends SpiceRequest<Treno> {

    private final String code;
    private final String number;

    /**
     * Costruttore.
     * @param pNumber numero del treno
     * @param pCode codice della stazione di origine del treno
     */
    public TrainRequest(final String pNumber, final String pCode) {
        super(Treno.class);
        this.code = pCode;
        this.number = pNumber;
    }

    @Override
    public final Treno loadDataFromNetwork() {
        return TrainRestClient.get().getTrain(this.number, this.code);
    }
}
