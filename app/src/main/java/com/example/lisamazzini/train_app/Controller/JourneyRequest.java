package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Older.JsoupPlannedJourney;
import com.example.lisamazzini.train_app.Parser.JourneyResultsParser;
import com.octo.android.robospice.request.SpiceRequest;

public class JourneyRequest extends SpiceRequest<JourneyListWrapper>{

    private final JourneyResultsParser journeyResults;


    public JourneyRequest(JourneyResultsParser journeyResults) {
        super(JourneyListWrapper.class);
        this.journeyResults = journeyResults;
    }

    @Override
    public JourneyListWrapper loadDataFromNetwork() throws Exception {
        Log.d("cazzi", "loaddatafromnetwork");
        return journeyResults.computeResult();

        //  TODO fai richiesta a JsoupJourney;
    }
}
