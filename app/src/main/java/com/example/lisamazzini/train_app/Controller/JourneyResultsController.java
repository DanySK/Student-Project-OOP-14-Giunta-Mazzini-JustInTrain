package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;
import com.example.lisamazzini.train_app.Utilities;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class JourneyResultsController {

    private List<PlainSolution> plainSolutions = new LinkedList<>();
    private int upperBound;
    private int lowerBound;

    public void buildPlainSolutions(Tragitto tragitto) {
        plainSolutions.clear();
        upperBound = 0;
        lowerBound = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateTime dt = new DateTime();
        for (Soluzioni sol : tragitto.getSoluzioni())
            for (Vehicle vehicle : sol.getVehicles()) {
                if (vehicle.getCategoriaDescrizione() != null && vehicle.getCategoriaDescrizione().equalsIgnoreCase("frecciabianca")) {
                    vehicle.setCategoriaDescrizione("FB");
                } else if (vehicle.getCategoriaDescrizione() != null && vehicle.getCategoriaDescrizione().equalsIgnoreCase("frecciarossa")) {
                    vehicle.setCategoriaDescrizione("FR");
                } else if (vehicle.getCategoriaDescrizione() != null && vehicle.getCategoriaDescrizione().equalsIgnoreCase("frecciaargento")) {
                    vehicle.setCategoriaDescrizione("FA");
                }
                try {
                    if (lowerBound == -1 && vehicle.getOrarioPartenza() != null && new DateTime(sdf.parse(vehicle.getOrarioPartenza())).isAfterNow()) {
                        Log.d("cazzi", "lowerboiudn valeva " + lowerBound);
                        lowerBound = plainSolutions.size();
                        Log.d("cazzi", "setto lowerbound a " + lowerBound);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                plainSolutions.add(new PlainSolution(vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
                        vehicle.getOrigine(), vehicle.getOraPartenza(), vehicle.getDestinazione(), vehicle.getOraArrivo(),
                        sol.getDurata()));
            }
    }

    public List<PlainSolution> getPlainSolutions() {
        upperBound = lowerBound + 5 >= plainSolutions.size() ? plainSolutions.size()-1 : lowerBound + 5;
        Log.d("cazzi", "lowerbound " + lowerBound + " upperbound " + upperBound);
        List<PlainSolution> subList = this.plainSolutions.subList(lowerBound, upperBound);
        lowerBound += 6;
        if (lowerBound < plainSolutions.size()-1) {
            return subList;
        } else {
            return new LinkedList<PlainSolution>();
        }
    }

    public String[] computeData(String data){
        return Utilities.splitJourney(data);
    }

    public String computeChoices(String[] first){
        final String result= "Stazione: " + first[0];
        return result;
    }
}
