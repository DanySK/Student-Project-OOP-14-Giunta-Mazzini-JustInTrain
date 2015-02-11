package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.InvalidStationException;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JourneyDataRequest extends SpiceRequest<String>{

    private final static String mainUrl = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/";

    private final String station;

    public JourneyDataRequest(String station){
        super(String.class);
        this.station = station;
    }

    @Override
    public String loadDataFromNetwork() throws IOException, InvalidStationException {
        URL url = new URL(mainUrl + station + "?=" + station);
        Log.d("cazzi", url.toString());

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String result = in.readLine();
        in.close();
        if (result == null) {
            throw new InvalidStationException();
        }
        Log.d("cazzi", "result = " + result);
        return result;
    }
}