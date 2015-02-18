package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tragitto {

    private List<Soluzioni> soluzioni = new ArrayList<Soluzioni>();
    private String origine;
    private String destinazione;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public final List<Soluzioni> getSoluzioni() {
        return soluzioni;
    }

    public final void setSoluzioni(final List<Soluzioni> soluzioni) {
        this.soluzioni = soluzioni;
    }

    public final String getOrigine() {
        return origine;
    }

    public final void setOrigine(final String origine) {
        this.origine = origine;
    }

    public final String getDestinazione() {
        return destinazione;
    }

    public final void setDestinazione(final String destinazione) {
        this.destinazione = destinazione;
    }

    public final Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public final void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }
}