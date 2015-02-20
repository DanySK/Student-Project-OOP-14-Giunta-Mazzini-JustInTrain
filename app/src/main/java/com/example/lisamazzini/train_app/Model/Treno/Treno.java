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
    private Long numeroTreno;
    private String categoria;
    private String subTitle;
    private String progress;
    private Long ritardo;


    public final List<Fermate> getFermate() {
        return fermate;
    }


    public final void setFermate(final List<Fermate> pFermate) {
        this.fermate = pFermate;
    }


    public final String getStazioneUltimoRilevamento() {
        return stazioneUltimoRilevamento;
    }


    public final void setStazioneUltimoRilevamento(final String pStazioneUltimoRilevamento) {
        this.stazioneUltimoRilevamento = pStazioneUltimoRilevamento;
    }


    public final String getIdOrigine() {
        return idOrigine;
    }


    public final void setIdOrigine(final String pIdOrigine) {
        this.idOrigine = pIdOrigine;
    }


    public final String getCompOraUltimoRilevamento() {
        return compOraUltimoRilevamento;
    }


    public final void setCompOraUltimoRilevamento(final String pCompOraUltimoRilevamento) {
        this.compOraUltimoRilevamento = pCompOraUltimoRilevamento;
    }


    public final Long getNumeroTreno() {
        return numeroTreno;
    }


    public final void setNumeroTreno(final Long pNumeroTreno) {
        this.numeroTreno = pNumeroTreno;
    }


    public final String getCategoria() {
        return categoria;
    }


    public final void setCategoria(final String pCategoria) {
        this.categoria = pCategoria;
    }


    public final String getSubTitle() {
        return subTitle;
    }


    public final void setSubTitle(final String pSubTitle) {
        this.subTitle = pSubTitle;
    }


    public final String getProgress() {
        return progress;
    }


    public final void setProgress(final String pProgress) {
        this.progress = pProgress;
    }


    public final Long getRitardo() {
        return ritardo;
    }


    public final void setRitardo(final Long pRitardo) {
        this.ritardo = pRitardo;
    }

}


