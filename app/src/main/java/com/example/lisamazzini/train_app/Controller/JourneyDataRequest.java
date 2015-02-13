package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class JourneyDataRequest extends AbstractRequest{

    private final String station;

    public JourneyDataRequest(String station){
        super(ListWrapper.class);
        this.station = station;
    }

    protected URL generateURL() throws MalformedURLException {
        return new URL(Constants.ROOT + Constants.STATION_AUTOCOMPLETE + station + "?q=" + station);
    }

    protected void check(List result) throws InvalidStationException {
        if(result.size()==0) {
            throw new InvalidStationException();
        }
    }
}