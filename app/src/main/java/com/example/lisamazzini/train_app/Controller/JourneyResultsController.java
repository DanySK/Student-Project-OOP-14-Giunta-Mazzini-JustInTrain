package com.example.lisamazzini.train_app.Controller;

import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;
import com.example.lisamazzini.train_app.Utilities;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che modella un controller per la visualizzazione e l'elaborazione di tratte.
 *
 * @author albertogiunta
 */
public class JourneyResultsController {

    private final SimpleDateFormat sdf = new SimpleDateFormat(Constants.SDF);
    private final List<PlainSolution> plainSolutions = new LinkedList<>();
    private int upperBound;
    private int lowerBound;
    private DateTime actualTime;
    private boolean foundFirstTakeable = false;


    /**
     * Metodo che deve chiamare chi impelementa un JourneyResultsController per settare l'orario con cui fare le operazioni
     * @param time: orario come stringa, in formato yyyy-MM-dd'T'HH:mm:ss
     */
    public void setTime(String time) {
        try {
            actualTime = new DateTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Meotod per settare la categoria di un treno da nome esteso a abbreviativo
     * @param vehicle: oggetto su cui fare le operazioni
     * @param category: nome esteso della categoria da sostituire
     * @param abbr: nome abbreviato della categoria
     * @return
     */
    private String setCategory(Vehicle vehicle, String category, String abbr) {
        if (vehicle.getCategoriaDescrizione() != null && vehicle.getCategoriaDescrizione().equalsIgnoreCase(category)) {
            return abbr;
        }
        return vehicle.getCategoriaDescrizione();
    }

    /**
     * Metodo che trasforma la lista di soluzioni che viene fornita dai server di trenitalia a un oggetto (plainSolution) più consono e flessibile
     * Esso infatti aggiunge supporto al ritardo ed altre informazioni utili altrimenti non accessibili.
     * @param tragitto: intera risposta del server, contiene al suo interno una lista di soluzioni, e a loro volta di vehicles
     */
    public void buildPlainSolutions(Tragitto tragitto) {
        plainSolutions.clear();
        upperBound = 0;
        lowerBound = 0;
        for (Soluzioni sol : tragitto.getSoluzioni()) {
            for (Vehicle vehicle : sol.getVehicles()) {
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciabianca", "FB"));
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciarossa", "FR"));
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciaargento", "FA"));
                try {
                    if (checkIsFirstTakeable(vehicle)) {
                        foundFirstTakeable = true;
                        lowerBound = plainSolutions.size() > 0 ? plainSolutions.size() - 1 : 0;
                    }
                    plainSolutions.add(new PlainSolution(vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
                            vehicle.getOrigine(), vehicle.getOraPartenza(), vehicle.getDestinazione(), vehicle.getOraArrivo(),
                            sol.getDurata(), checkIsTomorrow(vehicle)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Metodo che controlla se il treno in questione è il primo "prendibile",
     * ovvero se è il primo treno con orario di partenza successivo all'orario stabilito precedentemente.
     * @param vehicle: il vehicle da cui trarre informazioni
     * @return boolean
     * @throws ParseException
     */
    private boolean checkIsFirstTakeable(Vehicle vehicle) throws ParseException {
        return (!foundFirstTakeable && vehicle.getOrarioPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(actualTime));
    }

    /**
     * Metodo che controlla se il treno in questione è deve ancora partire,
     * infatti insorgono conflitti qualora un treno del giorno successivo (viene restituito il treno con i dati relativi al giorno stesso)
     *
     * @param vehicle: il vehicle da cui trarre informazioni
     * @return boolean
     * @throws ParseException
     */
    private boolean checkIsTomorrow(Vehicle vehicle) throws ParseException {
        return (foundFirstTakeable && vehicle.getOraPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(new DateTime().plusDays(1).toDateMidnight()));
    }

    /**
     * Metodo che restituisce una lista parziale di plainsolution,
     * che varia a seconda che si faccia una richiesta con orario "custom" o rispetto all'ora corrente
     * @param isCustom: booleano che rappresenta la modalità di richiesta
     * @return la lista parziale di plainsolutions
     */
    public List<PlainSolution> getPlainSolutions(boolean isCustom) {
        List<PlainSolution> subList;
        if (isCustom) {
            upperBound = plainSolutions.size() < 5 ? plainSolutions.size() : 5;
            subList = this.plainSolutions.subList(0, upperBound);
        } else {
            upperBound = lowerBound + 5 >= plainSolutions.size() ? plainSolutions.size()-1 : lowerBound + 5;
            subList = this.plainSolutions.subList(lowerBound, upperBound);
        }
        lowerBound += 6;
        if (lowerBound < plainSolutions.size()-1) {
            return subList;
        } else {
            return new LinkedList<PlainSolution>();
        }
    }

    /**
     * Metodo che restituisce una matrice per righe fatta di stazioni e relativi id (nella stessa colonna)
     * da far scegliere nel caso si cerchi una stazione e vengano restituiti più di un risultato.
     * @param list: una List<String> dei risultati restituiti dal server
     * @return una matrice per righe in cui ogni colonna è fatta di stazione e codice
     */
    public String[][] getTableForMultipleResults(List<String> list) {
        final String[][] dataMatrix = new String[2][list.size()];
        for (int i = 0 ; i < list.size(); i++) {
            String[] temp = splitData(list.get(i));
            dataMatrix[0][i] = temp[0];
            dataMatrix[1][i] = temp[1];
        }
        return dataMatrix;
    }

    /**
     * Metodo di utility che restituisce un codice stazione di tipo 01234 (e non S01234)
     * data in input una stringa di tipo STAZIONE|S01234
     * @param s: stringa di tipo STAZIONE|S01234
     * @return String[] contenente la stazione e il codice
     */
    public String[] splitData(String s) {
        return Utilities.splitStationForJourneySearch(s);
    }
}