package com.example.lisamazzini.train_app.Controller;

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
import java.util.LinkedList;
import java.util.List;

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
            result = Utilities.fetchData(new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/" + p.getNumeroTreno())).getList();
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

    private void makeRequest(PlainSolution p) {
        datas = iterator.next().split("\\|")[1].split("-");
        train = TrainRestClient.get().getTrain(datas[0], datas[1]);
        if (p.isTomorrow()) {
            train.setRitardo(0L);
        }
    }
}
