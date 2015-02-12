package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {

    private String origine;
    private String destinazione;
    private String orarioPartenza;
    private String oraPartenza;
    private String orarioArrivo;
    private String oraArrivo;
    private String categoria;
    private String categoriaDescrizione;
    private String numeroTreno;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private boolean isTomorrow;

    public boolean isTomorrow() {
        return isTomorrow;
    }

    public void setTomorrow(boolean isTomorrow) {
        this.isTomorrow = isTomorrow;
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

    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(String orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public String getOraPartenza() {
        setOraPartenza();
        return oraPartenza;
    }

    public void setOraPartenza() {
        this.oraPartenza = getOrarioPartenza().substring(11, 16);
    }

    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    public void setOrarioArrivo(String orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public String getOraArrivo() {
        setOraArrivo();
        return oraArrivo;
    }

    public void setOraArrivo() {
        this.oraArrivo = getOrarioArrivo().substring(11, 16);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    public void setCategoriaDescrizione(String categoriaDescrizione) {
        this.categoriaDescrizione = categoriaDescrizione;
    }

    public String getNumeroTreno() {
        return numeroTreno;
    }

    public void setNumeroTreno(String numeroTreno) {
        this.numeroTreno = numeroTreno;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}