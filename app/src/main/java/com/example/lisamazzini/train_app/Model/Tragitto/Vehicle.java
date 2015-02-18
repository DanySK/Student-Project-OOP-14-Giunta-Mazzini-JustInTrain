package com.example.lisamazzini.train_app.Model.Tragitto;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {

    private static final int FIRST_INDEX = 11;
    private static final int SECOND_INDEX = 16;
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

    public final boolean isTomorrow() {
        return isTomorrow;
    }

    public final void setTomorrow(final boolean isTomorrow) {
        this.isTomorrow = isTomorrow;
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

    public final String getOrarioPartenza() {
        return orarioPartenza;
    }

    public final void setOrarioPartenza(final String orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public final String getOraPartenza() {
        setOraPartenza();
        return oraPartenza;
    }

    public final void setOraPartenza() {
        this.oraPartenza = getOrarioPartenza().substring(FIRST_INDEX, SECOND_INDEX);
    }

    public final String getOrarioArrivo() {
        return orarioArrivo;
    }

    public final void setOrarioArrivo(final String orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    public final String getOraArrivo() {
        setOraArrivo();
        return oraArrivo;
    }

    public final void setOraArrivo() {
        this.oraArrivo = getOrarioArrivo().substring(FIRST_INDEX, SECOND_INDEX);
    }

    public final String getCategoria() {
        return categoria;
    }

    public final void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    public final String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    public final void setCategoriaDescrizione(final String categoriaDescrizione) {
        this.categoriaDescrizione = categoriaDescrizione;
    }

    public final String getNumeroTreno() {
        return numeroTreno;
    }

    public final void setNumeroTreno(final String numeroTreno) {
        this.numeroTreno = numeroTreno;
    }

    public final Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public final void setAdditionalProperty(final String name, final Object value) {
        this.additionalProperties.put(name, value);
    }
}