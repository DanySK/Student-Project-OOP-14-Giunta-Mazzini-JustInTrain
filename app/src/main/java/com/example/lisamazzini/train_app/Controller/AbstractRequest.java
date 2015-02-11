package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Exceptions.InvalidInputException;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lisamazzini on 11/02/15.
 */
public abstract class AbstractRequest<ListWrapper> extends SpiceRequest<ListWrapper>{

    public AbstractRequest(Class<ListWrapper> clazz) {
        super(clazz);
    }

    public ListWrapper loadDataFromNetwork() throws IOException, InvalidInputException{

        BufferedReader in = new BufferedReader(new InputStreamReader(generateURL().openStream()));

        String inputLine;
        List<String> result = new LinkedList<>();
        while ((inputLine = in.readLine()) != null) {
            result.add(inputLine);
            System.out.println(inputLine);
        }
        in.close();
        check(result);
        return new  ListWrapper(result);

    }

    protected abstract URL generateURL() throws MalformedURLException;

    protected abstract void check(List<String> result) throws InvalidInputException;

}
