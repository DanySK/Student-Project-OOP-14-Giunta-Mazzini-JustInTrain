package com.example.lisamazzini.train_app.Parser;

import android.app.Application;

import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;
import com.octo.android.robospice.retrofit.RetrofitSpiceService;

import retrofit.converter.Converter;
/*

/**
 * Created by lisamazzini on 02/02/15.
 */
public class NewTrainRetrofitSpiceService extends RetrofitGsonSpiceService {
    private final static String BASE_URL = "http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(InterfacciaAPI.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}

