package com.example.lisamazzini.train_app.Model.Treno;

import java.util.HashMap;
import java.util.Map;



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


    public String getStazione() {
        return stazione;
    }


    public void setStazione(String stazione) {
        this.stazione = stazione;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Long getActualFermataType() {
        return actualFermataType;
    }


    public void setActualFermataType(Long actualFermataType) {
        this.actualFermataType = actualFermataType;
    }


    public String getOrientamento() {
        return orientamento;
    }


    public void setOrientamento(String orientamento) {
        this.orientamento = orientamento;
    }


    public Long getProgrammata() {
        return programmata;
    }


    public void setProgrammata(Long programmata) {
        this.programmata = programmata;
    }


    public Long getEffettiva() {
        return effettiva;
    }


    public void setEffettiva(Long effettiva) {
        this.effettiva = effettiva;
    }


    public Long getRitardo() {
        return ritardo;
    }


    public void setRitardo(Long ritardo) {
        this.ritardo = ritardo;
    }


    public String getBinarioEffettivoPartenzaDescrizione() {
        return binarioEffettivoPartenzaDescrizione;
    }


    public void setBinarioEffettivoPartenzaDescrizione(String binarioEffettivoPartenzaDescrizione) {
        this.binarioEffettivoPartenzaDescrizione = binarioEffettivoPartenzaDescrizione;
    }


    public String getBinarioProgrammatoPartenzaDescrizione() {
        return binarioProgrammatoPartenzaDescrizione;
    }


    public void setBinarioProgrammatoPartenzaDescrizione(String binarioProgrammatoPartenzaDescrizione) {
        this.binarioProgrammatoPartenzaDescrizione = binarioProgrammatoPartenzaDescrizione;
    }

}