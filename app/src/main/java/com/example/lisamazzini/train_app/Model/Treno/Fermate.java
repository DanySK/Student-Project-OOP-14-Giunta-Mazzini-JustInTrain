package com.example.lisamazzini.train_app.Model.Treno;


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


    public final void setStazione(final String stazione) {
        this.stazione = stazione;
    }


    public final String getId() {
        return id;
    }


    public final void setId(final String id) {
        this.id = id;
    }


    public final Long getActualFermataType() {
        return actualFermataType;
    }


    public final void setActualFermataType(final Long actualFermataType) {
        this.actualFermataType = actualFermataType;
    }


    public final String getOrientamento() {
        return orientamento;
    }


    public final void setOrientamento(final String orientamento) {
        this.orientamento = orientamento;
    }


    public final Long getProgrammata() {
        return programmata;
    }


    public final void setProgrammata(final Long programmata) {
        this.programmata = programmata;
    }


    public final Long getEffettiva() {
        return effettiva;
    }


    public final void setEffettiva(final Long effettiva) {
        this.effettiva = effettiva;
    }


    public final Long getRitardo() {
        return ritardo;
    }


    public final void setRitardo(final Long ritardo) {
        this.ritardo = ritardo;
    }


    public final String getBinarioEffettivoPartenzaDescrizione() {
        return binarioEffettivoPartenzaDescrizione;
    }


    public final void setBinarioEffettivoPartenzaDescrizione(final String binarioEffettivoPartenzaDescrizione) {
        this.binarioEffettivoPartenzaDescrizione = binarioEffettivoPartenzaDescrizione;
    }


    public final String getBinarioProgrammatoPartenzaDescrizione() {
        return binarioProgrammatoPartenzaDescrizione;
    }


    public final void setBinarioProgrammatoPartenzaDescrizione(final String binarioProgrammatoPartenzaDescrizione) {
        this.binarioProgrammatoPartenzaDescrizione = binarioProgrammatoPartenzaDescrizione;
    }

}