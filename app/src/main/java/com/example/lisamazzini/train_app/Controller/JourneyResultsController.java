package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;
import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Utilities;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class JourneyResultsController {

    private List<PlainSolution> plainSolutions = new LinkedList<>();
    SimpleDateFormat sdf = new SimpleDateFormat(Constants.SDF);
    private int upperBound;
    private int lowerBound;
    private DateTime actualTime;
    private boolean foundFirstTakeable = false;

    public void setTime(String time) {
        try {
            actualTime = new DateTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String setCategory(Vehicle vehicle, String category, String abbr) {
        if (vehicle.getCategoriaDescrizione() != null && vehicle.getCategoriaDescrizione().equalsIgnoreCase(category)) {
            return abbr;
        }
        return vehicle.getCategoriaDescrizione();
    }

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


    private boolean checkIsFirstTakeable(Vehicle vehicle) throws ParseException {
        return (!foundFirstTakeable && vehicle.getOrarioPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(actualTime));
    }

    private boolean checkIsTomorrow(Vehicle vehicle) throws ParseException {
        return (foundFirstTakeable && vehicle.getOraPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfter(new DateTime().plusDays(1).toDateMidnight()));
    }

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

    public String[][] getTableForMultipleResults(List<String> list) {
        final String[][] dataMatrix = new String[2][list.size()];
        for (int i = 0 ; i < list.size(); i++) {
            String[] temp = splitData(list.get(i));
            dataMatrix[0][i] = temp[0];
            dataMatrix[1][i] = temp[1];
//            dataMatrix[0][i]= computeChoices(dataMatrix[0][i]);
        }
        return dataMatrix;
    }


    public String[] splitData(String data){
        return Utilities.splitJourney(data);
    }

    public String computeChoices(String s){
        return "Stazione: " + s;
    }
}