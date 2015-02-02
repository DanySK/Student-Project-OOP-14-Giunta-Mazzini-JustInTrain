package com.example.lisamazzini.train_app.Parser.NewJourneyParser;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {

    private String origine;
    private String destinazione;
    private String orarioPartenza;
    private String orarioArrivo;
    private String categoria;
    private String categoriaDescrizione;
    private String numeroTreno;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * The orarioPartenza
     */
    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    /**
     *
     * @param orarioPartenza
     * The orarioPartenza
     */
    public void setOrarioPartenza(String orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    /**
     *
     * @return
     * The orarioArrivo
     */
    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    /**
     *
     * @param orarioArrivo
     * The orarioArrivo
     */
    public void setOrarioArrivo(String orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    /**
     *
     * @return
     * The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     * The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     * The categoriaDescrizione
     */
    public String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    /**
     *
     * @param categoriaDescrizione
     * The categoriaDescrizione
     */
    public void setCategoriaDescrizione(String categoriaDescrizione) {
        this.categoriaDescrizione = categoriaDescrizione;
    }

    /**
     *
     * @return
     * The numeroTreno
     */
    public String getNumeroTreno() {
        return numeroTreno;
    }

    /**
     *
     * @param numeroTreno
     * The numeroTreno
     */
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