package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.LinkedList;

public class PlainSolution {

    private String categoria;
    private String numeroTreno;
    private String origine;
    private String orarioPartenza;
    private String destinazione;
    private String orarioArrivo;
    private String durata;
    private String delay = "";
    private String IDorigine = "";
    private String IDpartenza = "";
    private String IDarrivo = "";
    private boolean isTomorrow = false;


    public PlainSolution(String categoria, String numeroTreno, String origine, String orarioPartenza, String destinazione, String orarioArrivo, String durata, boolean isTomorrow) {
        this.categoria = categoria;
        this.numeroTreno = numeroTreno;
        this.origine = origine;
        this.orarioPartenza = orarioPartenza;
        this.destinazione = destinazione;
        this.orarioArrivo = orarioArrivo;
        this.durata = durata;
        this.isTomorrow = isTomorrow;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNumeroTreno() {
        return numeroTreno;
    }

    public String getOrigine() {
        return origine;
    }

    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    public String getDurata() {
        return durata;
    }

    public void setDelay (Long delay) {
        this.delay = delay.toString();
    }

    public String getDelay() {
        return this.delay;
    }

    public String getIDorigine() {
        return IDorigine;
    }

    public void setIDorigine(String IDorigine) {
        this.IDorigine = IDorigine;
    }

    public String getIDpartenza() {
        return IDpartenza;
    }

    public void setIDpartenza(String IDpartenza) {
        this.IDpartenza = IDpartenza;
    }

    public String getIDarrivo() {
        return IDarrivo;
    }

    public void setIDarrivo(String IDarrivo) {
        this.IDarrivo = IDarrivo;
    }

    public boolean isTomorrow() {
        return this.isTomorrow;
    }



}
