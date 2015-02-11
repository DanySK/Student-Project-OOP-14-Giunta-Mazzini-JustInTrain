package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Exceptions.InvalidTrainNumberException;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;


public class TrainDataRequest extends AbstractRequest {

    private final static String mainUrl = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/";

    private final String searchNumber;

    public TrainDataRequest(String searchQuery){
        super(ListWrapper.class);
        this.searchNumber = searchQuery;
    }

    @Override
    protected URL generateURL() throws MalformedURLException {
        return new URL(mainUrl + searchNumber);
    }

    @Override
    protected void check(List result) throws InvalidTrainNumberException {
        if(result.size()==0) {
            throw new InvalidTrainNumberException();
        }
    }
}
