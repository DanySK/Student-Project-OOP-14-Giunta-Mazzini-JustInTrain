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


    public final boolean isLastVehicleOfJourney() {
        return isLastVehicleOfJourney;
    }

    public final String getCategoria() {
        return categoria;
    }

    public final String getNumeroTreno() {
        return numeroTreno;
    }

    public final String getOrigine() {
        return origine;
    }

    public final String getOrarioPartenza() {
        return orarioPartenza;
    }

    public final String getDestinazione() {
        return destinazione;
    }

    public final String getOrarioArrivo() {
        return orarioArrivo;
    }

    public final String getDurata() {
        return durata;
    }

    public final void setDelay(final Long delay) {
        this.delay = delay.toString();
    }

    public final String getDelay() {
        return this.delay;
    }

    public final String getIdOrigine() {
        return idOrigine;
    }

    public final void setIdOrigine(final String idOrigine) {
        this.idOrigine = idOrigine;
    }

    public final String getIdPartenza() {
        return idPartenza;
    }

    public final void setIdPartenza(final String idPartenza) {
        this.idPartenza = idPartenza;
    }

    public final String getIdArrivo() {
        return idArrivo;
    }

    public final void setIdArrivo(final String idArrivo) {
        this.idArrivo = idArrivo;
    }

    public final boolean isTomorrow() {
        return this.tomorrow;
    }



}
