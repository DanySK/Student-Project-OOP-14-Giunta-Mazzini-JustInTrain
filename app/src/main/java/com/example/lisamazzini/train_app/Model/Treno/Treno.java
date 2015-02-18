package com.example.lisamazzini.train_app.Model.Treno;



    import java.util.ArrayList;
    import java.util.List;

/**
 * Created by lisamazzini on 01/02/15.
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


    public final void setFermate(final List<Fermate> fermate) {
        this.fermate = fermate;
    }


    public final String getStazioneUltimoRilevamento() {
        return stazioneUltimoRilevamento;
    }


    public final void setStazioneUltimoRilevamento(final String stazioneUltimoRilevamento) {
        this.stazioneUltimoRilevamento = stazioneUltimoRilevamento;
    }


    public final String getIdOrigine() {
        return idOrigine;
    }


    public final void setIdOrigine(final String idOrigine) {
        this.idOrigine = idOrigine;
    }


    public final String getCompOraUltimoRilevamento() {
        return compOraUltimoRilevamento;
    }


    public final void setCompOraUltimoRilevamento(final String compOraUltimoRilevamento) {
        this.compOraUltimoRilevamento = compOraUltimoRilevamento;
    }


    public final Long getNumeroTreno() {
        return numeroTreno;
    }


    public final void setNumeroTreno(final Long numeroTreno) {
        this.numeroTreno = numeroTreno;
    }


    public final String getCategoria() {
        return categoria;
    }


    public final void setCategoria(final String categoria) {
        this.categoria = categoria;
    }


    public final String getSubTitle() {
        return subTitle;
    }


    public final void setSubTitle(final String subTitle) {
        this.subTitle = subTitle;
    }


    public final String getProgress() {
        return progress;
    }


    public final void setProgress(final String progress) {
        this.progress = progress;
    }


    public final Long getRitardo() {
        return ritardo;
    }


    public final void setRitardo(final Long ritardo) {
        this.ritardo = ritardo;
    }

}


