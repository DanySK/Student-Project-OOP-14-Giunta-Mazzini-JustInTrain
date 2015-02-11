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

    /**
     * @return The stazione
     */
    public String getStazione() {
        return stazione;
    }

    /**
     * @param stazione The stazione
     */
    public void setStazione(String stazione) {
        this.stazione = stazione;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Long getActualFermataType() {
        return actualFermataType;
    }

    public void setActualFermataType(Long actualFermataType) {
        this.actualFermataType = actualFermataType;
    }
    /**
     * @return The listaCorrispondenze
     */
    public Object getListaCorrispondenze() {
        return listaCorrispondenze;
    }

    /**
     * @param listaCorrispondenze The listaCorrispondenze
     */
    public void setListaCorrispondenze(Object listaCorrispondenze) {
        this.listaCorrispondenze = listaCorrispondenze;
    }

    public String getOrientamento() {
        return orientamento;
    }

    public void setOrientamento(String orientamento) {
        this.orientamento = orientamento;
    }
    /**
     * @return The programmata
     */
    public Long getProgrammata() {
        return programmata;
    }

    /**
     * @param programmata The programmata
     */
    public void setProgrammata(Long programmata) {
        this.programmata = programmata;
    }

    /**
     * @return The programmataZero
     */
    public Object getProgrammataZero() {
        return programmataZero;
    }

    /**
     * @param programmataZero The programmataZero
     */
    public void setProgrammataZero(Object programmataZero) {
        this.programmataZero = programmataZero;
    }

    /**
     * @return The effettiva
     */
    public Long getEffettiva() {
        return effettiva;
    }

    /**
     * @param effettiva The effettiva
     */
    public void setEffettiva(Long effettiva) {
        this.effettiva = effettiva;
    }

    /**
     * @return The ritardo
     */
    public Long getRitardo() {
        return ritardo;
    }

    /**
     * @param ritardo The ritardo
     */
    public void setRitardo(Long ritardo) {
        this.ritardo = ritardo;
    }

    /**
     * @return The partenzaTeoricaZero
     */
    public Object getPartenzaTeoricaZero() {
        return partenzaTeoricaZero;
    }

    /**
     * @param partenzaTeoricaZero The partenzaTeoricaZero
     */
    public void setPartenzaTeoricaZero(Object partenzaTeoricaZero) {
        this.partenzaTeoricaZero = partenzaTeoricaZero;
    }

    /**
     * @return The arrivoTeoricoZero
     */
    public Object getArrivoTeoricoZero() {
        return arrivoTeoricoZero;
    }

    /**
     * @param arrivoTeoricoZero The arrivoTeoricoZero
     */
    public void setArrivoTeoricoZero(Object arrivoTeoricoZero) {
        this.arrivoTeoricoZero = arrivoTeoricoZero;
    }

    /**
     * @return The partenzaTeorica
     */
    public Object getPartenzaTeorica() {
        return partenzaTeorica;
    }

    /**
     * @param partenzaTeorica The partenzaTeorica
     */
    public void setPartenzaTeorica(Object partenzaTeorica) {
        this.partenzaTeorica = partenzaTeorica;
    }

    /**
     * @return The arrivoTeorico
     */
    public Object getArrivoTeorico() {
        return arrivoTeorico;
    }

    /**
     * @param arrivoTeorico The arrivoTeorico
     */
    public void setArrivoTeorico(Object arrivoTeorico) {
        this.arrivoTeorico = arrivoTeorico;
    }

    /**
     * @return The partenzaReale
     */
    public Long getPartenzaReale() {
        return partenzaReale;
    }

    /**
     * @param partenzaReale The partenzaReale
     */
    public void setPartenzaReale(Long partenzaReale) {
        this.partenzaReale = partenzaReale;
    }

    /**
     * @return The arrivoReale
     */
    public Long getArrivoReale() {
        return arrivoReale;
    }

    /**
     * @param arrivoReale The arrivoReale
     */
    public void setArrivoReale(Long arrivoReale) {
        this.arrivoReale = arrivoReale;
    }

    /**
     * @return The ritardoPartenza
     */
    public Long getRitardoPartenza() {
        return ritardoPartenza;
    }

    /**
     * @param ritardoPartenza The ritardoPartenza
     */
    public void setRitardoPartenza(Long ritardoPartenza) {
        this.ritardoPartenza = ritardoPartenza;
    }

    /**
     * @return The ritardoArrivo
     */
    public Long getRitardoArrivo() {
        return ritardoArrivo;
    }

    /**
     * @param ritardoArrivo The ritardoArrivo
     */
    public void setRitardoArrivo(Long ritardoArrivo) {
        this.ritardoArrivo = ritardoArrivo;
    }

    /**
     * @return The progressivo
     */
    public Long getProgressivo() {
        return progressivo;
    }

    /**
     * @param progressivo The progressivo
     */
    public void setProgressivo(Long progressivo) {
        this.progressivo = progressivo;
    }

    /**
     * @return The binarioEffettivoArrivoDescrizione
     */
    public String getBinarioEffettivoArrivoDescrizione() {
        return binarioEffettivoArrivoDescrizione;
    }

    /**
     * @param binarioEffettivoArrivoDescrizione The binarioEffettivoArrivoDescrizione
     */
    public void setBinarioEffettivoArrivoDescrizione(String binarioEffettivoArrivoDescrizione) {
        this.binarioEffettivoArrivoDescrizione = binarioEffettivoArrivoDescrizione;
    }

    /**
     * @return The binarioProgrammatoArrivoDescrizione
     */
    public String getBinarioProgrammatoArrivoDescrizione() {
        return binarioProgrammatoArrivoDescrizione;
    }

    /**
     * @param binarioProgrammatoArrivoDescrizione The binarioProgrammatoArrivoDescrizione
     */
    public void setBinarioProgrammatoArrivoDescrizione(String binarioProgrammatoArrivoDescrizione) {
        this.binarioProgrammatoArrivoDescrizione = binarioProgrammatoArrivoDescrizione;
    }

    /**
     * @return The binarioEffettivoPartenzaDescrizione
     */
    public String getBinarioEffettivoPartenzaDescrizione() {
        return binarioEffettivoPartenzaDescrizione;
    }

    /**
     * @param binarioEffettivoPartenzaDescrizione The binarioEffettivoPartenzaDescrizione
     */
    public void setBinarioEffettivoPartenzaDescrizione(String binarioEffettivoPartenzaDescrizione) {
        this.binarioEffettivoPartenzaDescrizione = binarioEffettivoPartenzaDescrizione;
    }

    /**
     * @return The binarioProgrammatoPartenzaDescrizione
     */
    public String getBinarioProgrammatoPartenzaDescrizione() {
        return binarioProgrammatoPartenzaDescrizione;
    }

    /**
     * @param binarioProgrammatoPartenzaDescrizione The binarioProgrammatoPartenzaDescrizione
     */
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