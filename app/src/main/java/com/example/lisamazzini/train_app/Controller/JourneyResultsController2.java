package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class JourneyResultsController2 {

    private final String departure;
    private final String arrival;
    private final String date;
    private final String time;
    private List<PlainSolution> plainSolutions = new LinkedList<>();


    public JourneyResultsController2(String departure, String arrival, String date, String time) {
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
    }

    public void buildPlainSolutions(Tragitto tragitto) {
        plainSolutions.clear();
        for (Soluzioni sol : tragitto.getSoluzioni()) {
            for (Vehicle vehicle : sol.getVehicles()) {
                plainSolutions.add(new PlainSolution(vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
                        vehicle.getOrigine(), vehicle.getOraPartenza(), vehicle.getDestinazione(), vehicle.getOraArrivo(),
                        sol.getDurata()));
            }
        }
        Log.d("cazzi", "build " + plainSolutions.size());
    }

    public List<PlainSolution> getPlainSolutions() {
        Log.d("cazzi", " get " + plainSolutions.size());

        return this.plainSolutions.subList(10, 15);
    }
}