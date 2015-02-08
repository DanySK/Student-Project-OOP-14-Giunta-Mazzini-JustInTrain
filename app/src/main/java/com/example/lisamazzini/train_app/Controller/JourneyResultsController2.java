package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class JourneyResultsController2 {

    private List<PlainSolution> plainSolutions = new LinkedList<>();

    public void buildPlainSolutions(Tragitto tragitto) {
        plainSolutions.clear();
        for (Soluzioni sol : tragitto.getSoluzioni()) {
            for (Vehicle vehicle : sol.getVehicles()) {
                plainSolutions.add(new PlainSolution(vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
                        vehicle.getOrigine(), vehicle.getOraPartenza(), vehicle.getDestinazione(), vehicle.getOraArrivo(),
                        sol.getDurata()));
            }
        }
    }

    public List<PlainSolution> getPlainSolutions() {
        return this.plainSolutions.subList(0, 5);
    }
}