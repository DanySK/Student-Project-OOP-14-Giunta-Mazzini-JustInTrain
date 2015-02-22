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
    private List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Getter per la durata del percorso.
     * @return durata
     */
    public final String getDurata() {
        return durata;
    }

    /**
     * Setter per la durata del percorso.
     * @param pDurata durata da settare
     */
    public final void setDurata(final String pDurata) {
        this.durata = pDurata;
    }

    /**
     * Getter per la lista di Vehicles.
     * @return lista di Vehicles
     */
    public final List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Setter per la lista di Vehicles.
     * @param pVehicles lista da settare
     */
    public final void setVehicles(final List<Vehicle> pVehicles) {
        this.vehicles = pVehicles;
    }
}