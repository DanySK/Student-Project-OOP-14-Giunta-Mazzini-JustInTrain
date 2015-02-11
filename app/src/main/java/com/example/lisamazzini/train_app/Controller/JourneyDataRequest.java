package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JourneyDataRequest extends AbstractRequest{

    private final static String mainUrl = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/";

    private final String station;

    public JourneyDataRequest(String station){
        super(String.class);
        this.station = station;
    }

    protected URL generateURL() throws MalformedURLException {
        return new URL(mainUrl + station + "?=" + station);
    }

    protected void check(List result) throws InvalidStationException {
        if(result.size()==0) {
            throw new InvalidStationException();
        }
    }
}