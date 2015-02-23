package com.example.lisamazzini.train_app.model.treno;

/**
 * Classe che modella un oggetto "fermata", necessario per il parsing del json.
 *
 * @author lisamazzini
 */
public class Fermate {

    private String stazione;
    private String id;
    private Long programmata;
    private Long effettiva;
    private long ritardo;
    private long actualFermataType;
    private String binarioEffettivoPartenzaDescrizione;
    private String binarioProgrammatoPartenzaDescrizione;

    /**
     * Getter per il nome della stazione.
     * @return stazione
     */
    public final String getStazione() {
        return stazione;
    }

    /**
     * Setter per il nome della stazione.
     * @param pStazione nome da settare
     */
    public final void setStazione(final String pStazione) {
        this.stazione = pStazione;
    }

    /**
     * Getter per l'id della stazione.
     * @return id
     */
    public final String getId() {
        return id;
    }

    /**
     * Setter per l'id della stazione.
     * @param pId id della stazione
     */
    public final void setId(final String pId) {
        this.id = pId;
    }

    /**
     * Getter per l'actualFermataType, che indica se la fermata
     * è stata visitata (1) o meno(0), se è straordinaria(2) o se è cancellata (3).
     * @return actualFermataType
     */
    public final long getActualFermataType() {
        return actualFermataType;
    }

    /**
     * Setter per l'actualFermatatype.
     * @param pActualFermataType valore da settare
     */
    public final void setActualFermataType(final long pActualFermataType) {
        this.actualFermataType = pActualFermataType;
    }

    /**
     * Getter per l'orario di partenza programmato.
     * @return orario in millisecondi
     */
    public final Long getProgrammata() {
        return programmata;
    }

    /**
     * Setter per la partenza programmata.
     * @param pProgrammata orario da settare
     */
    public final void setProgrammata(final Long pProgrammata) {
        this.programmata = pProgrammata;
    }

    /**
     * Getter per l'orario di partenza effettivo.
     * @return orario in millisecondi
     */
    public final Long getEffettiva() {
        return effettiva;
    }

    /**
     * Setter per la partenza effettiva.
     * @param oEffettiva orario da settare
     */
    public final void setEffettiva(final Long oEffettiva) {
        this.effettiva = oEffettiva;
    }

    /**
     * Getter per il ritardo.
     * @return ritardo
     */
    public final long getRitardo() {
        return ritardo;
    }

    /**
     * Setter per il ritardo.
     * @param pRitardo ritardo da settare
     */
    public final void setRitardo(final long pRitardo) {
        this.ritardo = pRitardo;
    }

    /**
     * Getter per il numero del binario di partenza effettivo.
     * @return numero di binario effettivo
     */
    public final String getBinarioEffettivoPartenzaDescrizione() {
        return binarioEffettivoPartenzaDescrizione;
    }

    /**
     * Setter per il binario effettivo di partenza.
     * @param pBinarioEffettivoPartenzaDescrizione binario effettivo
     */
    public final void setBinarioEffettivoPartenzaDescrizione(final String pBinarioEffettivoPartenzaDescrizione) {
        this.binarioEffettivoPartenzaDescrizione = pBinarioEffettivoPartenzaDescrizione;
    }

    /**
     * Getter per il numero del binario di partenza programmato.
     * @return numero di binaio programmato
     */
    public final String getBinarioProgrammatoPartenzaDescrizione() {
        return binarioProgrammatoPartenzaDescrizione;
    }

    /**
     * Setter per il binario di partenza programmato.
     * @param pBinarioProgrammatoPartenzaDescrizione binario programmato
     */
    public final void setBinarioProgrammatoPartenzaDescrizione(final String pBinarioProgrammatoPartenzaDescrizione) {
        this.binarioProgrammatoPartenzaDescrizione = pBinarioProgrammatoPartenzaDescrizione;
    }

}