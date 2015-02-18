package com.example.lisamazzini.train_app.Model.Tragitto;

public class PlainSolution {

    private boolean isLastVehicleOfJourney;
    private String categoria;
    private String numeroTreno;
    private String origine;
    private String orarioPartenza;
    private String destinazione;
    private String orarioArrivo;
    private String durata;
    private String delay = "";
    private String idOrigine = "";
    private String idPartenza = "";
    private String idArrivo = "";
    private boolean tomorrow = false;
    public  PlainSolution(final boolean isLastVehicleOfJourney, final String categoria, final String numeroTreno, final String origine, final String orarioPartenza,
                         final String destinazione, final String orarioArrivo, final String durata, final boolean tomorrow) {
        this.isLastVehicleOfJourney = isLastVehicleOfJourney;
        this.categoria = categoria;
        this.numeroTreno = numeroTreno;
        this.origine = origine;
        this.orarioPartenza = orarioPartenza;
        this.destinazione = destinazione;
        this.orarioArrivo = orarioArrivo;
        this.durata = durata;
        this.tomorrow = tomorrow;
    }


    public boolean isLastVehicleOfJourney() {
        return isLastVehicleOfJourney;
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
        return idOrigine;
    }

    public void setIdOrigine(String idOrigine) {
        this.idOrigine= idOrigine;
    }

    public String getIdPartenza() {
        return idPartenza;
    }

    public void setIdPartenza(String idPartenza) {
        this.idPartenza= idPartenza;
    }

    public String getIdArrivo() {
        return idArrivo;
    }

    public void setIdArrivo(String idarrivo) {
        this.idArrivo = idarrivo;
    }

    public boolean isTomorrow() {
        return this.tomorrow;
    }



}
