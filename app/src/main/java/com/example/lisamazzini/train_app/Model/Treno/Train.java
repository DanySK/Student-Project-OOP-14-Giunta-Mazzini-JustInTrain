package com.example.lisamazzini.train_app.Model.Treno;


    import com.example.lisamazzini.train_app.Model.Treno.Fermate;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

/**
 * Created by lisamazzini on 01/02/15.
 */
public class Train {

    private List<Fermate> fermate = new ArrayList<Fermate>();
    private String stazioneUltimoRilevamento;
    private String idOrigine;
    private String compOraUltimoRilevamento;
    private Long numeroTreno;
    private String categoria;
    private String subTitle;
    private String progress;
    private Long ritardo;

    public String getProgress() {
        return progress;
    }


    public void setProgress(String progress) {
        this.progress = progress;
    }


    public List<Fermate> getFermate() {
        return fermate;
    }


    public void setFermate(List<Fermate> fermate) {
        this.fermate = fermate;
    }


    public String getStazioneUltimoRilevamento() {
        return stazioneUltimoRilevamento;
    }


    public void setStazioneUltimoRilevamento(String stazioneUltimoRilevamento) {
        this.stazioneUltimoRilevamento = stazioneUltimoRilevamento;
    }


    public String getIdOrigine() {
        return idOrigine;
    }


    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }


    public String getCompOraUltimoRilevamento() {
        return compOraUltimoRilevamento;
    }


    public void setCompOraUltimoRilevamento(String compOraUltimoRilevamento) {
        this.compOraUltimoRilevamento = compOraUltimoRilevamento;
    }


    public Long getNumeroTreno() {
        return numeroTreno;
    }


    public void setNumeroTreno(Long numeroTreno) {
        this.numeroTreno = numeroTreno;
    }


    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }


    public Long getRitardo() {
        return ritardo;
    }


    public void setRitardo(Long ritardo) {
        this.ritardo = ritardo;
    }

}


