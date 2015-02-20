package com.example.lisamazzini.train_app.model.tragitto;

/**
 * Classe che modella un oggetto Vehicle, necessaria per il parsing json.
 *
 * @author albertogiunta
 */
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
    private boolean tomorrow;

    public final boolean isTomorrow() {
        return tomorrow;
    }

    public final void setTomorrow(final boolean pIsTomorrow) {
        this.tomorrow = pIsTomorrow;
    }

    public final String getOrigine() {
        return origine;
    }

    public final void setOrigine(final String pOrigine) {
        this.origine = pOrigine;
    }

    public final String getDestinazione() {
        return destinazione;
    }

    public final void setDestinazione(final String pDestinazione) {
        this.destinazione = pDestinazione;
    }

    public final String getOrarioPartenza() {
        return orarioPartenza;
    }

    public final void setOrarioPartenza(final String pOrarioPartenza) {
        this.orarioPartenza = pOrarioPartenza;
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

    public final void setOrarioArrivo(final String pOrarioArrivo) {
        this.orarioArrivo = pOrarioArrivo;
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

    public final void setCategoria(final String pCategoria) {
        this.categoria = pCategoria;
    }

    public final String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    public final void setCategoriaDescrizione(final String pCategoriaDescrizione) {
        this.categoriaDescrizione = pCategoriaDescrizione;
    }

    public final String getNumeroTreno() {
        return numeroTreno;
    }

    public final void setNumeroTreno(final String pNumeroTreno) {
        this.numeroTreno = pNumeroTreno;
    }
}