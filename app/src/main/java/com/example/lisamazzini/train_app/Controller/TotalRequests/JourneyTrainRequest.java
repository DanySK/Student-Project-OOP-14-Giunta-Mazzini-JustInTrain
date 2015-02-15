package com.example.lisamazzini.train_app.Controller.TotalRequests;

import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Network.TrainRestClient;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JourneyTrainRequest extends SpiceRequest<PlainSolutionWrapper> {

    private List<PlainSolution> plainSolutions;
    private Iterator<String> iterator;
    private Train train;

    public JourneyTrainRequest(List<PlainSolution> plainSolutions) {
        super(PlainSolutionWrapper.class);
        this.plainSolutions = plainSolutions;
    }

    @Override
    public PlainSolutionWrapper loadDataFromNetwork() throws Exception {

        List<String> result;

        for (PlainSolution p : plainSolutions) {

            p.setIDpartenza(getID(p.getOrigine()));
            p.setIDarrivo(getID(p.getDestinazione()));

            result = Utilities.fetchData(Utilities.generateTrainAutocompleteURL(p.getNumeroTreno())).getList();
            if (result.size() == 0) {
                return new PlainSolutionWrapper(new LinkedList<PlainSolution>());
            }
            iterator = result.iterator();
            makeRequest(p);
            p.setIDorigine(train.getIdOrigine());
            p.setDelay(train.getRitardo());
            if (result.size() > 1) {
                boolean trovato = false;
                while (!trovato && iterator.hasNext()) {
                    makeRequest(p);
                    for(Fermate f : train.getFermate()){
                        if(f.getId().equals(p.getIDpartenza())){
                            p.setIDorigine(train.getIdOrigine());
                            p.setDelay(train.getRitardo());
                            trovato = true;
                            break;
                        }
                    }
                }
            }
        }
        return new PlainSolutionWrapper(plainSolutions);
    }

    private String getID(String stationName) throws IOException {
        return Utilities.splitStationForTrainSearch
                (Utilities.fetchData
                        (Utilities.generateStationAutocompleteURL(stationName))
                        .getList()
                        .get(0))
                [1];
    }

    private void makeRequest(PlainSolution p) {
        String[] trainData = iterator.next().split("\\|")[1].split("-");
        train = TrainRestClient.get().getTrain(trainData[0], trainData[1]);
        if (p.isTomorrow()) {
            train.setRitardo(0L);
        }
    }
}
