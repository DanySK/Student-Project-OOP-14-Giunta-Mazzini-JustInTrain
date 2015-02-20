package com.example.lisamazzini.train_app.controller;

import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.tragitto.PlainSolution;
import com.example.lisamazzini.train_app.model.tragitto.Soluzioni;
import com.example.lisamazzini.train_app.model.tragitto.Tragitto;
import com.example.lisamazzini.train_app.model.tragitto.Vehicle;
import com.example.lisamazzini.train_app.Utilities;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che modella un controller per la visualizzazione e l'elaborazione di tratte.
 *
 * @author albertogiunta
 */
public class JourneyListController {

    private static final int SOLUTION = 5;
    private final SimpleDateFormat sdf = new SimpleDateFormat(Constants.SDF);
    private final List<PlainSolution> totalPlainSolutions = new LinkedList<>();
    private int upperBound;
    private int lowerBound;
    private DateTime actualTime;
    private boolean foundFirstTakeable = false;
    private boolean isCustomTime;
    private String departureID, departureStation, arrivalID, arrivalStation, requestedTime;
    private final List<PlainSolution> partialPlainSolutions = new LinkedList<>();

    public final List<PlainSolution> getPartialPlainSolutions() {
        return partialPlainSolutions;
    }

    public final void addSolutions(final List<PlainSolution> list) {
        this.partialPlainSolutions.addAll(list);
    }

    public final void clearPartialPlainSolutionList() {
        this.partialPlainSolutions.clear();
    }

    public final boolean isCustomTime() {
        return isCustomTime;
    }

    public final void setCustomTime(final boolean pIsCustomTime) {
        this.isCustomTime = pIsCustomTime;
    }

    public final String getDepartureID() {
        return departureID;
    }

    public final void setDepartureID(final String pDepartureID) {
        this.departureID = pDepartureID;
    }

    public final String getDepartureStation() {
        return departureStation;
    }

    public final void setDepartureStation(final String pDepartureStation) {
        this.departureStation = pDepartureStation;
    }

    public final String getArrivalID() {
        return arrivalID;
    }

    public final void setArrivalID(final String pArrivalID) {
        this.arrivalID = pArrivalID;
    }

    public final String getArrivalStation() {
        return arrivalStation;
    }

    public final void setArrivalStation(final String pArrivalStation) {
        this.arrivalStation = pArrivalStation;
    }

    public final String getRequestedTime() {
        return requestedTime;
    }

    public final void setRequestedTime(final String pRequestedTime) {
        this.requestedTime = pRequestedTime;
        setTime(this.requestedTime);
    }

    /**
     * Metodo che deve chiamare chi impelementa un JourneyResultsController per settare l'orario con cui fare le operazioni.
     * @param time: orario come stringa, in formato yyyy-MM-dd'T'HH:mm:ss
     */
    public final void setTime(final String time) {
        try {
            actualTime = new DateTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per settare la categoria di un treno da nome esteso a abbreviato.
     * @param vehicle: oggetto su cui fare le operazioni
     * @param category: nome esteso della categoria da sostituire
     * @param abbr: nome abbreviato della categoria
     * @return
     */
    private String setCategory(final Vehicle vehicle, final String category, final String abbr) {
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
    public final void buildPlainSolutions(final Tragitto tragitto) {
        totalPlainSolutions.clear();
        upperBound = 0;
        lowerBound = 0;
        for (Soluzioni sol : tragitto.getSoluzioni()) {
            Iterator<Vehicle> i = sol.getVehicles().iterator();
            while (i.hasNext()) {
                Vehicle vehicle = i.next();
                boolean isLastVehicleOfJourney = !i.hasNext();
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciabianca", "FB"));
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciarossa", "FR"));
                vehicle.setCategoriaDescrizione("" + setCategory(vehicle, "frecciaargento", "FA"));
                try {
                    if (checkIsFirstTakeable(vehicle)) {
                        foundFirstTakeable = true;
                        lowerBound = totalPlainSolutions.size() > 0 ? totalPlainSolutions.size() - 1 : 0;
                    }
                    totalPlainSolutions.add(new PlainSolution(isLastVehicleOfJourney, vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
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
     *
     * @param vehicle: il vehicle da cui trarre informazioni
     * @return boolean
     * @throws ParseException
     */
    private boolean checkIsFirstTakeable(final Vehicle vehicle) throws ParseException {
        return (!foundFirstTakeable && vehicle.getOrarioPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(actualTime));
    }

    /**
     * Metodo che controlla se il treno in questione è deve ancora partire,
     * infatti insorgono conflitti qualora un treno del giorno successivo (viene restituito il treno con i dati relativi al giorno stesso).
     *
     * @param vehicle: il vehicle da cui trarre informazioni
     * @return boolean
     * @throws ParseException
     */
    private boolean checkIsTomorrow(final Vehicle vehicle) throws ParseException {
        return (foundFirstTakeable && vehicle.getOraPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(new DateTime().plusDays(1).toDateMidnight()));
    }

    /**
     * Metodo che restituisce una lista parziale di plainsolution,
     * che varia a seconda che si faccia una richiesta con orario "custom" o rispetto all'ora corrente.
     *
     * @param isCustom: booleano che rappresenta la modalità di richiesta
     * @return la lista parziale di plainsolutions
     */
    public final List<PlainSolution> getPlainSolutions(final boolean isCustom) {
        List<PlainSolution> temp = new LinkedList<>();

        if (isCustom) {
            lowerBound = 0;
        }
        upperBound = getIndexForNSolutions(SOLUTION);
        temp = this.totalPlainSolutions.subList(lowerBound, upperBound);
        lowerBound = upperBound + 1;
        if (lowerBound < totalPlainSolutions.size()) {
            return temp;
        } else {
            return new LinkedList<>();
        }
    }

    public final int getIndexForNSolutions(final int n) {
        int index = lowerBound;
        int vehicles = 0;
        for (int i = 0; i < n; i++) {
            while (this.totalPlainSolutions.size() > lowerBound + vehicles && !this.totalPlainSolutions.get(lowerBound + vehicles).isLastVehicleOfJourney()) {
                vehicles++;
            }
            vehicles++;
            index = totalPlainSolutions.size() > lowerBound + vehicles ? lowerBound + vehicles : index;
        }
        return index;
    }


    /**
     * Metodo che restituisce una matrice per righe fatta di stazioni e relativi id (nella stessa colonna)
     * da far scegliere nel caso si cerchi una stazione e vengano restituiti più di un risultato.
     *
     * @param list: una List<String> dei risultati restituiti dal server
     * @return una matrice per righe in cui ogni colonna è fatta di stazione e codice
     */
    public final String[][] getTableForMultipleResults(final List<String> list) {
        final String[][] dataMatrix = new String[2][list.size()];
        for (int i = 0; i < list.size(); i++) {
            String[] temp = splitData(list.get(i));
            dataMatrix[0][i] = temp[0];
            dataMatrix[1][i] = temp[1];
        }
        return dataMatrix;
    }

    /**
     * Metodo di utility che restituisce un codice stazione di tipo 01234 (e non S01234)
     * data in input una stringa di tipo STAZIONE|S01234.
     *
     * @param s: stringa di tipo STAZIONE|S01234
     * @return String[] contenente la stazione e il codice
     */
    public final String[] splitData(final String s) {
        return Utilities.splitStationForJourneySearch(s);
    }
}