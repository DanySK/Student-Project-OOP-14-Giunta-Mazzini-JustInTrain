package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Soluzioni {

    private String durata;
    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}