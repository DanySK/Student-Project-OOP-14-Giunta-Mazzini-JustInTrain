package com.example.lisamazzini.train_app.model.tragitto;

/**
 * Classe che modella un oggetto "soluzione", che raccoglie e fornisce le informazioni in maniera più efficace
 * rispetto al model necessario per il parsing del json.
 *
 * @author albertogiunta
 */
public final class PlainSolution {

    private final boolean lastVehicleOfJourney;
    private final String categoria;
    private final String numeroTreno;
    private final String origine;
    private final String orarioPartenza;
    private final String destinazione;
    private final String orarioArrivo;
    private final String durata;
    private String delay = "";
    private String idOrigine = "";
    private String idPartenza = "";
    private String idArrivo = "";
    private final boolean tomorrow;

    /**
     * Costruttore.
     *
     * @param pIsLastVehicleOfJourney booleano che descrive se è l'ultimo Vehicle di una Solution
     * @param pCategoria categoria del treno
     * @param pNumeroTreno numero del treno
     * @param pOrigine stazione di partenza
     * @param pOrarioPartenza orario di partenza
     * @param pDestinazione stazione di arrivo
     * @param pOrarioArrivo orario di arrivo
     * @param pDurata durata del tragitto
     * @param pTomorrow booleano che descrive se il tragitto è una soluzione per i giorni successivi
     */
    public  PlainSolution(final boolean pIsLastVehicleOfJourney, final String pCategoria, final String pNumeroTreno, final String pOrigine, final String pOrarioPartenza,
                         final String pDestinazione, final String pOrarioArrivo, final String pDurata, final boolean pTomorrow) {
        this.lastVehicleOfJourney = pIsLastVehicleOfJourney;
        this.categoria = pCategoria;
        this.numeroTreno = pNumeroTreno;
        this.origine = pOrigine;
        this.orarioPartenza = pOrarioPartenza;
        this.destinazione = pDestinazione;
        this.orarioArrivo = pOrarioArrivo;
        this.durata = pDurata;
        this.tomorrow = pTomorrow;
    }

    /**
     * Getter per il booleano lastVehicleOfJourney.
     * @return lastVehicleOfJourney
     */
    public boolean isLastVehicleOfJourney() {
        return lastVehicleOfJourney;
    }

    /**
     * Getter per la categoria.
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Getter per il numero del treno.
     * @return numero del treno
     */
    public String getNumeroTreno() {
        return numeroTreno;
    }

    /**
     * Getter per la stazione di partenza del treno.
     * @return stazione di partenza
     */
    public String getOrigine() {
        return origine;
    }

    /**
     * Getter per l'orario di partenza.
     * @return orario di partenza
     */
    public String getOrarioPartenza() {
        return orarioPartenza;
    }

    /**
     * Getter per la stazione di arrivo.
     * @return stazione di arrivo
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     * Getter per l'orario di arrivo.
     * @return orario di arrivo
     */
    public String getOrarioArrivo() {
        return orarioArrivo;
    }

    /**
     * Getter per la durata.
     * @return durata
     */
    public String getDurata() {
        return durata;
    }

    /**
     * Setter per il ritardo.
     * @param pDelay ritardo
     */
    public void setDelay(final Long pDelay) {
        this.delay = pDelay.toString();
    }

    /**
     * Getter per il ritardo.
     * @return ritardo
     */
    public String getDelay() {
        return this.delay;
    }
    /**
     * Getter per l'id della stazione di origine del treno.
     * @return id di origine
     */
    public String getIDorigine() {
        return idOrigine;
    }
    /**
     * Setter per l'id della stazione di origine del treno.
     * @param pIdOrigine id da settare
     */
    public void setIdOrigine(final String pIdOrigine) {
        this.idOrigine = pIdOrigine;
    }

    /**
     * Getter per l'id della stazione di partenza.
     * @return id di partenza
     */
    public String getIdPartenza() {
        return idPartenza;
    }

    /**
     * Setter per l'id di partenza.
     * @param pIdPartenza id da settare
     */
    public void setIdPartenza(final String pIdPartenza) {
        this.idPartenza = pIdPartenza;
    }

    /**
     * Getter per l'id della stazione di arrivo.
     * @return id di arrivo
     */
    public String getIdArrivo() {
        return idArrivo;
    }

    /**
     * Setter per l'id di arrivo.
     * @param pIdArrivo id da settare
     */
    public void setIdArrivo(final String pIdArrivo) {
        this.idArrivo = pIdArrivo;
    }

    /**
     * Getter per il booleano tomorrow che indica se la soluzione è per i giorni successivi.
     * @return true se è nei giorni successivi, false altrimenti
     */
    public boolean isTomorrow() {
        return this.tomorrow;
    }



}
