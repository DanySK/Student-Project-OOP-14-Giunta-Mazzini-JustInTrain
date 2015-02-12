package com.example.lisamazzini.train_app.Controller;

import android.content.DialogInterface;
import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Network.TrainRestClient;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JourneyTrainRequest extends SpiceRequest<PlainSolutionWrapper> {

    List<PlainSolution> plainSolutions;
    List<String> result;
    Iterator<String> iterator;
    String[] datas;
    Train train;

    public JourneyTrainRequest(List<PlainSolution> plainSolutions) {
        super(PlainSolutionWrapper.class);
        this.plainSolutions = plainSolutions;
    }

    @Override
    public PlainSolutionWrapper loadDataFromNetwork() throws Exception {

        BufferedReader in;
        URL url;

        for (PlainSolution p : plainSolutions) {

            // cerco l'id di partenza e lo assegno
            url = new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/" + p.getOrigine() + "?=" + p.getOrigine());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            p.setIDpartenza(in.readLine().split("\\|")[1]);
            in.close();

            // cerco l'id di arrivo e lo assegno
            url = new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/" + p.getDestinazione() + "?=" + p.getDestinazione());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            p.setIDarrivo(in.readLine().split("\\|")[1]);
            in.close();

            // cerco i dati del treno in questione (stazione + codice stazione di origine totale)
            result = Utilities.dallInternet(new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/" + p.getNumeroTreno())).getList();
            iterator = result.iterator();
            makeRequest();
            p.setIDorigine(train.getIdOrigine());
            p.setDelay(train.getRitardo());
            if (result.size() > 1) {
                boolean trovato = false;
                while (!trovato && iterator.hasNext()) {
                    makeRequest();
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

    private void makeRequest() {
        datas = iterator.next().split("\\|")[1].split("-");
        train = TrainRestClient.get().getTrain(datas[0], datas[1]);
    }
}
