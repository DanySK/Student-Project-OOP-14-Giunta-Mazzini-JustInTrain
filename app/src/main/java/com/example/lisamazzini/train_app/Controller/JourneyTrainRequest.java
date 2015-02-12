package com.example.lisamazzini.train_app.Controller;

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
import java.util.List;

public class JourneyTrainRequest extends SpiceRequest<PlainSolutionWrapper> {

    private List<PlainSolution> plainSolutions;

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
            url = new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/" + p.getOrigine()+ "?=" + p.getOrigine());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            p.setIDpartenza(in.readLine().split("\\|")[1]);
            in.close();

            // cerco l'id di arrivo e lo assegno
            url = new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/" + p.getDestinazione()+ "?=" + p.getDestinazione());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            p.setIDarrivo(in.readLine().split("\\|")[1]);
            in.close();

            // cerco i dati del treno in questione (stazione + codice stazione di origine totale)
            url = new URL("http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/" + p.getNumeroTreno());
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            Log.d("wft", p.getNumeroTreno());
            String result = in.readLine();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += Constants.SEPARATOR + inputLine;
                System.out.println(inputLine);
            }
            in.close();

            ////////////////////////////////////////////////////////////////////////

            // capisco se il treno è singolo o doppio}

            //I used Constants.SEPARATOR to divide the result in case there are more train with the same number
            String[] datas = result.split(Constants.SEPARATOR);
            //Only one train
            if (datas.length == 1) {
                Log.d("cazzi", datas[0]);
                // I take the second part of the string, and divide it in 2; example 608 - S11145 -> [608,S11145]
                datas = Utilities.splitString(datas[0]);

                // scarico i dati di quel preciso treno e setto cosa mi serve
                Train train = TrainRestClient.get().getTrain(datas[0], datas[1]);
                p.setIDorigine(train.getIdOrigine());
                p.setDelay(train.getRitardo());

            } else {

                Log.d("cazzi", datas[0] + " " + datas[1]);
                // se c'è più di un treno con quel codice, scorro la lista di stazioni di entrambi e cerco il mio

                //Here I take the data of the first train
                final String[] firstChoiceData = Utilities.splitString(datas[0]);

                //Here I take the data of the second train
                final String[] secondChoiceData = Utilities.splitString(datas[1]);

                Train train;

                // scarico i dati del primo treno trovato e setto cosa mi serve
                train = TrainRestClient.get().getTrain(firstChoiceData[0], firstChoiceData[1]);

                boolean trovato = false;

                for(Fermate f : train.getFermate()){
                    if(f.getId().equals(p.getIDpartenza())){
                        p.setIDorigine(train.getIdOrigine());
                        p.setDelay(train.getRitardo());
                        trovato = true;
                        break;
                    }
                }

                // scarico i dati del secondo treno trovato e setto cosa mi serve
                if (trovato == false) {
                    train = TrainRestClient.get().getTrain(secondChoiceData[0], secondChoiceData[1]);
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
}
