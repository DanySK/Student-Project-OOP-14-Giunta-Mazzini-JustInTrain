package com.example.lisamazzini.train_app.model.tragitto;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modella un oggetto "soluzione" necessaria per il parsing del json.
 * Ha una lista di vehicles e la durata.
 *
 * @author albertogiunta
 */
public class Soluzioni {

    private String durata;
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public final String getDurata() {
        return durata;
    }

    public final void setDurata(final String pDurata) {
        this.durata = pDurata;
    }

    public final List<Vehicle> getVehicles() {
        return vehicles;
    }

    public final void setVehicles(final List<Vehicle> pVehicles) {
        this.vehicles = pVehicles;
    }
}