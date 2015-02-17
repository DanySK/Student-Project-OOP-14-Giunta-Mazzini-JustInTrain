package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tragitto {

    private List<Soluzioni> soluzioni = new ArrayList<Soluzioni>();
    private String origine;
    private String destinazione;
    private Object errore;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Soluzioni> getSoluzioni() {
        return soluzioni;
    }

    public void setSoluzioni(java.util.List<Soluzioni> soluzioni) {
        this.soluzioni = soluzioni;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public Object getErrore() {
        return errore;
    }

    public void setErrore(Object errore) {
        this.errore = errore;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}