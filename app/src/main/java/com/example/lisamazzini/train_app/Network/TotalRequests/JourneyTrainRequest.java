package com.example.lisamazzini.train_app.Network.TotalRequests;

import android.util.Log;

import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolutionWrapper;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.Network.TrainRestClient;
import com.example.lisamazzini.train_app.model.treno.Fermate;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.request.SpiceRequest;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che modella una richiesta necessaria a ottenere i dati di un treno (ad esempio ritardo, binari etc)
 * a partire da una tratta.
 * Di norma viene svolta dopo aver scaricato i dati della journeyRequest e costruito una lista di PlainSolutions,
 * a quel punto per ogni plainSolution, si fa una richiesta di tipo trainRequest per ogni treno nella plainSolution,
 * se presenti più di un treno con quel numero si scaricano a mano a mano le info su quei treni e si controlla se
 * il dato treno passa per la stazione di partenza della tratta fornita.
 * Se si si prende quel treno e si passa alla prossima plainsolution, altirmenti si ripete il procedimento per gli
 * altri treni che vengono elencati
 *
 * @author albertogiunta
 */
public class JourneyTrainRequest extends SpiceRequest<PlainSolutionWrapper> {

    private List<PlainSolution> plainSolutions;
    private Iterator<String> iterator;
    private Treno train;

    public JourneyTrainRequest(final List<PlainSolution> plainSolutions) {
        super(PlainSolutionWrapper.class);
        this.plainSolutions = plainSolutions;
    }

    @Override
    public PlainSolutionWrapper loadDataFromNetwork() throws Exception {

        List<String> result;

        for (PlainSolution p : plainSolutions) {
            p.setIdPartenza(getID(p.getOrigine()));
            p.setIdArrivo(getID(p.getDestinazione()));

            result = Utilities.fetchData(Utilities.generateTrainAutocompleteURL(p.getNumeroTreno())).getList();
            if (result.size() == 0) {
                return new PlainSolutionWrapper(new LinkedList<PlainSolution>());
            }
            iterator = result.iterator();
            this.makeRequest(p);
            p.setIdOrigine(train.getIdOrigine());
            p.setDelay(train.getRitardo());
            if (result.size() > 1) {
                boolean containsDepartureStation = false;
                while (!containsDepartureStation && iterator.hasNext()) {
                    this.makeRequest(p);
                    for(Fermate f : train.getFermate()){
                        if(f.getId().equals(p.getIdPartenza())){
                            p.setIdOrigine(train.getIdOrigine());
                            p.setDelay(train.getRitardo());
                            containsDepartureStation = true;
                            break;
                        }
                    }
                }
            }
        }
        return new PlainSolutionWrapper(plainSolutions);
    }

    /**
     * Meotodo utile per ottenere l'id di una stazione dato il suo nome
     * Non è implementato usando la journeyDataRequest per l'impossibilità di usare una spiceRequest dentro un'altra spiceRequest
     * @param stationName: il nome della stazione
     * @return ID: l'id della stazione (il nome sarà fornito da trenitalia e quindi non c'è bisogno di controllare eventuali risultati multipli,
     * sarà sicuramente univoco.
     * @throws IOException
     */
    private String getID(final String stationName) throws IOException {
        Log.d("cazzi", "fermata: " + stationName);
        return Utilities.splitStationForTrainSearch
                (Utilities.fetchData
                        (Utilities.generateStationAutocompleteURL(stationName))
                        .getList()
                        .get(0))
                [1];
    }

    /**
     * Metodo usato per ottenere l'oggetto di tipo Treno a partire dall'id di origine e numero del treno stesso
     *
     * @param p: la plainSolution di cui ottenere un oggetto di tipo Treno
     */
    private void makeRequest(final PlainSolution p) {
        String[] trainData = iterator.next().split("\\|")[1].split("-");
        train = TrainRestClient.get().getTrain(trainData[0], trainData[1]);
        if (p.isTomorrow()) {
            train.setRitardo(0L);
        }
    }
}
