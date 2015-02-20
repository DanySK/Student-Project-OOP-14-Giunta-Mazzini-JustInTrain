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
    private Long ritardo;
    private String orientamento;
    private Long actualFermataType;
    private String binarioEffettivoPartenzaDescrizione;
    private String binarioProgrammatoPartenzaDescrizione;

    public final String getStazione() {
        return stazione;
    }


    public final void setStazione(final String pStazione) {
        this.stazione = pStazione;
    }


    public final String getId() {
        return id;
    }


    public final void setId(final String pId) {
        this.id = pId;
    }


    public final Long getActualFermataType() {
        return actualFermataType;
    }


    public final void setActualFermataType(final Long pActualFermataType) {
        this.actualFermataType = pActualFermataType;
    }


    public final String getOrientamento() {
        return orientamento;
    }


    public final void setOrientamento(final String pOrientamento) {
        this.orientamento = pOrientamento;
    }


    public final Long getProgrammata() {
        return programmata;
    }


    public final void setProgrammata(final Long pProgrammata) {
        this.programmata = pProgrammata;
    }


    public final Long getEffettiva() {
        return effettiva;
    }


    public final void setEffettiva(final Long oEffettiva) {
        this.effettiva = oEffettiva;
    }


    public final Long getRitardo() {
        return ritardo;
    }


    public final void setRitardo(final Long pRitardo) {
        this.ritardo = pRitardo;
    }


    public final String getBinarioEffettivoPartenzaDescrizione() {
        return binarioEffettivoPartenzaDescrizione;
    }


    public final void setBinarioEffettivoPartenzaDescrizione(final String pBinarioEffettivoPartenzaDescrizione) {
        this.binarioEffettivoPartenzaDescrizione = pBinarioEffettivoPartenzaDescrizione;
    }


    public final String getBinarioProgrammatoPartenzaDescrizione() {
        return binarioProgrammatoPartenzaDescrizione;
    }


    public final void setBinarioProgrammatoPartenzaDescrizione(final String pBinarioProgrammatoPartenzaDescrizione) {
        this.binarioProgrammatoPartenzaDescrizione = pBinarioProgrammatoPartenzaDescrizione;
    }

}