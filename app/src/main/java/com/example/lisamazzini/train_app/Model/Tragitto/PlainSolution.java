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
     * Construttore necessario alla costruzione dell'oggetto.
     *
     * @param pIsLastVehicleOfJourney
     * @param pCategoria
     * @param pNumeroTreno
     * @param pOrigine
     * @param pOrarioPartenza
     * @param pDestinazione
     * @param pOrarioArrivo
     * @param pDurata
     * @param pTomorrow
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


    public boolean isLastVehicleOfJourney() {
        return lastVehicleOfJourney;
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

    public void setDelay(final Long pDelay) {
        this.delay = pDelay.toString();
    }

    public String getDelay() {
        return this.delay;
    }

    public String getIDorigine() {
        return idOrigine;
    }

    public void setIdOrigine(final String pIdOrigine) {
        this.idOrigine = pIdOrigine;
    }

    public String getIdPartenza() {
        return idPartenza;
    }

    public void setIdPartenza(final String pIdPartenza) {
        this.idPartenza = pIdPartenza;
    }

    public String getIdArrivo() {
        return idArrivo;
    }

    public void setIdArrivo(final String pIdArrivo) {
        this.idArrivo = pIdArrivo;
    }

    public boolean isTomorrow() {
        return this.tomorrow;
    }



}
