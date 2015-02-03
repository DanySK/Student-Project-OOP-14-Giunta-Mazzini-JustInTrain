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
    private int timeSlot;

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getTimeSlot() {
        return this.timeSlot;
    }

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

    public class JourneyList extends ArrayList<Soluzioni> {

        private ArrayList<Soluzioni> journeyTrain;
        private int timeSlot;

        public JourneyList(ArrayList<Soluzioni> list, int timeSlot) {
            this.journeyTrain = list;
            this.timeSlot = timeSlot;
        }

        public ArrayList<Soluzioni> getList() {
            return this.journeyTrain;
        }

        public int getTimeSlot() {
            return this.timeSlot-1;
        }

        // TODO va aggiunto il currentTimeSlot al tragitto
    }
}
