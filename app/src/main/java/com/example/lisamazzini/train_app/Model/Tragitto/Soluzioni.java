package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Soluzioni {

    private String durata;
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public final String getDurata() {
        return durata;
    }

    public final void setDurata(final String durata) {
        this.durata = durata;
    }

    public final List<Vehicle> getVehicles() {
        return vehicles;
    }

    public final void setVehicles(final List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public final Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public final void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }
}