package com.example.lisamazzini.train_app.Model.Tragitto;

public class PlainSolution {

    private String categoria;
    private String numeroTreno;
    private String origine;
    private String orarioPartenza;
    private String destinazione;
    private String orarioArrivo;
    private String durata;

    public PlainSolution(String categoria, String numeroTreno, String origine, String orarioPartenza, String destinazione, String orarioArrivo, String durata) {
        this.categoria = categoria;
        this.numeroTreno = numeroTreno;
        this.origine = origine;
        this.orarioPartenza = orarioPartenza;
        this.destinazione = destinazione;
        this.orarioArrivo = orarioArrivo;
        this.durata = durata;
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
}
