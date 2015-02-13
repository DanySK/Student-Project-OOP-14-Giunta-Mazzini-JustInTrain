package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Exceptions.InvalidInputException;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public abstract class AbstractRequest extends SpiceRequest<ListWrapper>{

    public AbstractRequest(Class<ListWrapper> clazz) {
        super(clazz);
    }

    public ListWrapper loadDataFromNetwork() throws IOException, InvalidInputException{
        ListWrapper result = Utilities.fetchData(generateURL());
        check(result.getList());
        return result;
    }

    protected abstract URL generateURL() throws MalformedURLException;

    protected abstract void check(List<String> result) throws InvalidInputException;

}