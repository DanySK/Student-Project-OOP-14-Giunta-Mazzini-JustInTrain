package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;


public class TrainDataRequest extends SpiceRequest<String> {

    private final static String mainUrl = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/";

    private final String searchNumber;

    public TrainDataRequest(String searchQuery){
        super(String.class);
        this.searchNumber = searchQuery;
    }

    @Override
    public String loadDataFromNetwork() throws IOException {
        URL url = new URL(mainUrl + searchNumber);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String result = in.readLine();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            result += Constants.SEPARATOR + inputLine;
            System.out.println(inputLine);
        }
        in.close();

        return result;
    }
}
