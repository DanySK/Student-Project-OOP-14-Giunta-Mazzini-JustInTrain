package com.example.lisamazzini.train_app.Model.Treno;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lisamazzini on 02/02/15.
 */

public class Fermate {

    private String stazione;
    private String id;
    private Object listaCorrispondenze;
    private Long programmata;
    private Object programmataZero;
    private Long effettiva;
    private Long ritardo;
    private Object partenzaTeoricaZero;
    private Object arrivoTeoricoZero;
    private Object partenzaTeorica;
    private Object arrivoTeorico;
    private Long partenzaReale;
    private Long arrivoReale;
    private Long ritardoPartenza;
    private Long ritardoArrivo;
    private Long progressivo;
    private String orientamento;
    private Long actualFermataType;
    private String binarioEffettivoArrivoDescrizione;
    private String binarioProgrammatoArrivoDescrizione;
    private String binarioEffettivoPartenzaDescrizione;
    private String binarioProgrammatoPartenzaDescrizione;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


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

    public Object getListaCorrispondenze() {
        return listaCorrispondenze;
    }

    public void setListaCorrispondenze(Object listaCorrispondenze) {
        this.listaCorrispondenze = listaCorrispondenze;
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

    public Object getProgrammataZero() {
        return programmataZero;
    }

    public void setProgrammataZero(Object programmataZero) {
        this.programmataZero = programmataZero;
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

    public Object getPartenzaTeoricaZero() {
        return partenzaTeoricaZero;
    }

    public void setPartenzaTeoricaZero(Object partenzaTeoricaZero) {
        this.partenzaTeoricaZero = partenzaTeoricaZero;
    }

    public Object getArrivoTeoricoZero() {
        return arrivoTeoricoZero;
    }

    public void setArrivoTeoricoZero(Object arrivoTeoricoZero) {
        this.arrivoTeoricoZero = arrivoTeoricoZero;
    }

    public Object getPartenzaTeorica() {
        return partenzaTeorica;
    }

    public void setPartenzaTeorica(Object partenzaTeorica) {
        this.partenzaTeorica = partenzaTeorica;
    }

    public Object getArrivoTeorico() {
        return arrivoTeorico;
    }

    public void setArrivoTeorico(Object arrivoTeorico) {
        this.arrivoTeorico = arrivoTeorico;
    }

    public Long getPartenzaReale() {
        return partenzaReale;
    }

    public void setPartenzaReale(Long partenzaReale) {
        this.partenzaReale = partenzaReale;
    }

    public Long getArrivoReale() {
        return arrivoReale;
    }

    public void setArrivoReale(Long arrivoReale) {
        this.arrivoReale = arrivoReale;
    }

    public Long getRitardoPartenza() {
        return ritardoPartenza;
    }

    public void setRitardoPartenza(Long ritardoPartenza) {
        this.ritardoPartenza = ritardoPartenza;
    }

    public Long getRitardoArrivo() {
        return ritardoArrivo;
    }

    public void setRitardoArrivo(Long ritardoArrivo) {
        this.ritardoArrivo = ritardoArrivo;
    }

    public Long getProgressivo() {
        return progressivo;
    }

    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    public String getBinarioEffettivoArrivoDescrizione() {
        return binarioEffettivoArrivoDescrizione;
    }

    public void setBinarioEffettivoArrivoDescrizione(String binarioEffettivoArrivoDescrizione) {
        this.binarioEffettivoArrivoDescrizione = binarioEffettivoArrivoDescrizione;
    }

    public String getBinarioProgrammatoArrivoDescrizione() {
        return binarioProgrammatoArrivoDescrizione;
    }

    public void setBinarioProgrammatoArrivoDescrizione(String binarioProgrammatoArrivoDescrizione) {
        this.binarioProgrammatoArrivoDescrizione = binarioProgrammatoArrivoDescrizione;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}