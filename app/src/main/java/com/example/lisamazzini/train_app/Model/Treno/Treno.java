package com.example.lisamazzini.train_app.model.treno;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modella un oggetto Treno, necessaria per il parsing json.
 * Ha una lista di fermate e altre informazioni.
 *
 * @author lisamazzini
 */
public class Treno {

    private List<Fermate> fermate = new ArrayList<Fermate>();
    private String stazioneUltimoRilevamento;
    private String idOrigine;
    private String compOraUltimoRilevamento;
    private long numeroTreno;
    private String categoria;
    private String subTitle;
    private String progress;
    private long ritardo;

    /**
     * Getter per la lista di Fermate.
     * @return la lista di Fermate
     */
    public final List<Fermate> getFermate() {
        return fermate;
    }

    /**
     * Setter per la lista di Fermate.
     * @param pFermate lista da settare
     */
    public final void setFermate(final List<Fermate> pFermate) {
        this.fermate = pFermate;
    }

    /**
     * Getter per la stazione dove il treno è stato rilevato l'ultima volta.
     * @return nome della stazione
     */
    public final String getStazioneUltimoRilevamento() {
        return stazioneUltimoRilevamento;
    }

    /**
     * Setter per la stazione di ultimo rilevamento.
     * @param pStazioneUltimoRilevamento stazione da settare
     */
    public final void setStazioneUltimoRilevamento(final String pStazioneUltimoRilevamento) {
        this.stazioneUltimoRilevamento = pStazioneUltimoRilevamento;
    }

    /**
     * Getter per l'id della stazione di origine del treno.
     * @return id di origine
     */
    public final String getIdOrigine() {
        return idOrigine;
    }

    /**
     * Setter per l'id della stazione di origine.
     * @param pIdOrigine id da settare
     */
    public final void setIdOrigine(final String pIdOrigine) {
        this.idOrigine = pIdOrigine;
    }

    /**
     * Getter per l'orario di ultimo rilevamento.
     * @return orario di ultimo rilevamento
     */
    public final String getCompOraUltimoRilevamento() {
        return compOraUltimoRilevamento;
    }

    /**
     * Setter per l'orario di ultimo rilevamento.
     * @param pCompOraUltimoRilevamento orario da settare
     */
    public final void setCompOraUltimoRilevamento(final String pCompOraUltimoRilevamento) {
        this.compOraUltimoRilevamento = pCompOraUltimoRilevamento;
    }

    /**
     * Getter per il numero di treno.
     * @return numero treno
     */
    public final long getNumeroTreno() {
        return numeroTreno;
    }

    /**
     * Setter per il numero del treno.
     * @param pNumeroTreno numero da settare
     */
    public final void setNumeroTreno(final long pNumeroTreno) {
        this.numeroTreno = pNumeroTreno;
    }

    /**
     * Getter per la categoria del treno.
     * @return categoria.
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
     * Getter per il subTitle dove sono scritte le informazioni sul treno.
     * @return subTitle
     */
    public final String getSubTitle() {
        return subTitle;
    }

    /**
     * Setter per il subTitle.
     * @param pSubTitle da settare
     */
    public final void setSubTitle(final String pSubTitle) {
        this.subTitle = pSubTitle;
    }

    /**
     * Getter per il Progress che descrive l'andamento del treno.
     * @return progress
     */
    public final String getProgress() {
        return progress;
    }

    /**
     * Setter per il Progress del treno.
     * @param pProgress da settare
     */
    public final void setProgress(final String pProgress) {
        this.progress = pProgress;
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

}


