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

    /**
     * Getter per il booleano tomorrow.
     * @return true se è parte di una soluzione per i giorni successivi, false altrimenti
     */
    public final boolean isTomorrow() {
        return tomorrow;
    }

    /**
     * Setter per il booleano tomorrow.
     * @param pIsTomorrow valore da settare.
     */
    public final void setTomorrow(final boolean pIsTomorrow) {
        this.tomorrow = pIsTomorrow;
    }

    /**
     * Getter per la stazione di partenza della Soluzione.
     * @return
     */
    public final String getOrigine() {
        return origine;
    }

    /**
     * Setter per la stazione di partenza della Soluzione.
     * @param pOrigine stazione da settare
     */
    public final void setOrigine(final String pOrigine) {
        this.origine = pOrigine;
    }

    /**
     * Getter per la stazione di destinazione della Soluzione.
     * @return stazione di destinazione
     */
    public final String getDestinazione() {
        return destinazione;
    }

    /**
     * Setter per la stazione di destinazione della Soluzione.
     * @param pDestinazione stazione da settare
     */
    public final void setDestinazione(final String pDestinazione) {
        this.destinazione = pDestinazione;
    }

    /**
     * Getter per l'orario di partenza.
     * @return orario di partenza
     */
    public final String getOrarioPartenza() {
        return orarioPartenza;
    }

    /**
     * Setter per l'orario di partenza.
     * @param pOrarioPartenza orario da settare
     */
    public final void setOrarioPartenza(final String pOrarioPartenza) {
        this.orarioPartenza = pOrarioPartenza;
    }

    /**
     * Getter per l'orario di partenza in formato HH:mm.
     * @return orario di partenza HH:mm
     */
    public final String getOraPartenza() {
        setOraPartenza();
        return oraPartenza;
    }

    /**
     * Setter per l'orario di partenza in formato HH:mm.
     */
    public final void setOraPartenza() {
        this.oraPartenza = getOrarioPartenza().substring(FIRST_INDEX, SECOND_INDEX);
    }

    /**
     * Getter per l'orario di arrivo.
     * @return orario di arrivo
     */
    public final String getOrarioArrivo() {
        return orarioArrivo;
    }

    /**
     * Setter per l'orario di arrivo.
     * @param pOrarioArrivo orario da settare
     */
    public final void setOrarioArrivo(final String pOrarioArrivo) {
        this.orarioArrivo = pOrarioArrivo;
    }

    /**
     * Getter per l'orario di arrivo in formato HH:mm.
     * @return orario di arrivo
     */
    public final String getOraArrivo() {
        setOraArrivo();
        return oraArrivo;
    }

    /**
     * Setter per l'orario di arrivo in formato HH:mm
     */
    public final void setOraArrivo() {
        this.oraArrivo = getOrarioArrivo().substring(FIRST_INDEX, SECOND_INDEX);
    }

    /**
     * Getter per la categoria del treno.
     * @return categoria
     */
    public final String getCategoria() {
        return categoria;
    }

    /**
     * Setter per la categoria del treno.
     * @param pCategoria categoria da settare
     */
    public final void setCategoria(final String pCategoria) {
        this.categoria = pCategoria;
    }

    /**
     * Getter per la descrizione della categoria del treno.
     * @return categoria
     */
    public final String getCategoriaDescrizione() {
        return categoriaDescrizione;
    }

    /**
     * Setter per la descrizione della categoria del treno.
     * @param pCategoriaDescrizione categoria da settare
     */
    public final void setCategoriaDescrizione(final String pCategoriaDescrizione) {
        this.categoriaDescrizione = pCategoriaDescrizione;
    }

    /**
     * Getter per il numero del treno.
     * @return numero del treno
     */
    public final String getNumeroTreno() {
        return numeroTreno;
    }

    /**
     * Setter per il numero del treno.
     * @param pNumeroTreno numero da settare
     */
    public final void setNumeroTreno(final String pNumeroTreno) {
        this.numeroTreno = pNumeroTreno;
    }
}