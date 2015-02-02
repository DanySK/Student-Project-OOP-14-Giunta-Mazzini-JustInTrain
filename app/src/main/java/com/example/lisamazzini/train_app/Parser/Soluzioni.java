package com.example.lisamazzini.train_app.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Soluzioni {

    private String durata;
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The durata
     */
    public String getDurata() {
        return durata;
    }

    /**
     *
     * @param durata
     * The durata
     */
    public void setDurata(String durata) {
        this.durata = durata;
    }

    /**
     *
     * @return
     * The vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     *
     * @param vehicles
     * The vehicles
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}