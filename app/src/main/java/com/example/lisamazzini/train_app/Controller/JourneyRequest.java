package com.example.lisamazzini.train_app.Controller;

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
        return journeyResults.computeResult();
    }
}
