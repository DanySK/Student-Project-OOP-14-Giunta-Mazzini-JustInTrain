package com.example.lisamazzini.train_app.Parser.NewJourneyParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JourneyResponse {

    private List<Soluzioni> soluzioni = new ArrayList<Soluzioni>();
    private String origine;
    private String destinazione;
    private Object errore;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The soluzioni
     */
    public List<Soluzioni> getSoluzioni() {
        return soluzioni;
    }

    /**
     *
     * @param soluzioni
     * The soluzioni
     */
    public void setSoluzioni(List<Soluzioni> soluzioni) {
        this.soluzioni = soluzioni;
    }

    /**
     *
     * @return
     * The origine
     */
    public String getOrigine() {
        return origine;
    }

    /**
     *
     * @param origine
     * The origine
     */
    public void setOrigine(String origine) {
        this.origine = origine;
    }

    /**
     *
     * @return
     * The destinazione
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     *
     * @param destinazione
     * The destinazione
     */
    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    /**
     *
     * @return
     * The errore
     */
    public Object getErrore() {
        return errore;
    }

    /**
     *
     * @param errore
     * The errore
     */
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