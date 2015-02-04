package com.example.lisamazzini.train_app.Parser;


    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

/**
 * Created by lisamazzini on 01/02/15.
 */



public class NewTrain{

    private Object fermateSoppresse;
    private List<Fermate> fermate = new ArrayList<Fermate>();
    private Object anormalita;
    private Object provvedimenti;
    private Object segnalazioni;
    private String stazioneUltimoRilevamento;
    private String idDestinazione;
    private String idOrigine;
    private List<Object> cambiNumero = new ArrayList<Object>();
    private Boolean hasProvvedimenti;
    private List<String> descOrientamento = new ArrayList<String>();
    private String compOraUltimoRilevamento;
    private Object motivoRitardoPrevalente;
    private Long numeroTreno;
    private String categoria;
    private String origine;
    private String destinazione;
    private Object codDestinazione;
    private String origineZero;
    private Long orarioArrivoZero;
    private Boolean circolante;
    private Boolean inStazione;
    private Boolean haCambiNumero;
    private Boolean nonPartito;
    private Long provvedimento;
    private Object riprogrammazione;
    private Long orarioPartenza;
    private Long orarioArrivo;
    private Object stazionePartenza;
    private Object stazioneArrivo;
    private Object statoTreno;
    private Object corrispondenze;
    private List<Object> servizi = new ArrayList<Object>();
    private Long ritardo;
    private String compOrarioPartenzaZeroEffettivo;
    private String compOrarioArrivoZeroEffettivo;
    private String compOrarioPartenzaZero;
    private String compOrarioArrivoZero;
    private String compOrarioArrivo;
    private String compOrarioPartenza;
    private List<String> compOrientamento = new ArrayList<String>();
    private String compTipologiaTreno;
    private List<String> compRitardo = new ArrayList<String>();
    private List<String> compRitardoAndamento = new ArrayList<String>();
    private List<String> compInStazionePartenza = new ArrayList<String>();
    private List<String> compInStazioneArrivo = new ArrayList<String>();
    private String compDurata;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



    /**
     *
     * @return
     * The fermateSoppresse
     */
    public Object getFermateSoppresse() {
        return fermateSoppresse;
    }

    /**
     *
     * @param fermateSoppresse
     * The fermateSoppresse
     */
    public void setFermateSoppresse(Object fermateSoppresse) {
        this.fermateSoppresse = fermateSoppresse;
    }


    /**
     *
     * @return
     * The fermate
     */
    public List<Fermate> getFermate() {
        return fermate;
    }

    /**
     *
     * @param fermate
     * The fermate
     */
    public void setFermate(List<Fermate> fermate) {
        this.fermate = fermate;
    }

    /**
     *
     * @return
     * The anormalita
     */
    public Object getAnormalita() {
        return anormalita;
    }

    /**
     *
     * @param anormalita
     * The anormalita
     */
    public void setAnormalita(Object anormalita) {
        this.anormalita = anormalita;
    }

    /**
     *
     * @return
     * The provvedimenti
     */
    public Object getProvvedimenti() {
        return provvedimenti;
    }

    /**
     *
     * @param provvedimenti
     * The provvedimenti
     */
    public void setProvvedimenti(Object provvedimenti) {
        this.provvedimenti = provvedimenti;
    }

    /**
     *
     * @return
     * The segnalazioni
     */
    public Object getSegnalazioni() {
        return segnalazioni;
    }

    /**
     *
     * @param segnalazioni
     * The segnalazioni
     */
    public void setSegnalazioni(Object segnalazioni) {
        this.segnalazioni = segnalazioni;
    }


    /**
     *
     * @return
     * The stazioneUltimoRilevamento
     */
    public String getStazioneUltimoRilevamento() {
        return stazioneUltimoRilevamento;
    }

    /**
     *
     * @param stazioneUltimoRilevamento
     * The stazioneUltimoRilevamento
     */
    public void setStazioneUltimoRilevamento(String stazioneUltimoRilevamento) {
        this.stazioneUltimoRilevamento = stazioneUltimoRilevamento;
    }

    /**
     *
     * @return
     * The idDestinazione
     */
    public String getIdDestinazione() {
        return idDestinazione;
    }

    /**
     *
     * @param idDestinazione
     * The idDestinazione
     */
    public void setIdDestinazione(String idDestinazione) {
        this.idDestinazione = idDestinazione;
    }

    /**
     *
     * @return
     * The idOrigine
     */
    public String getIdOrigine() {
        return idOrigine;
    }

    /**
     *
     * @param idOrigine
     * The idOrigine
     */
    public void setIdOrigine(String idOrigine) {
        this.idOrigine = idOrigine;
    }

    /**
     *
     * @return
     * The cambiNumero
     */
    public List<Object> getCambiNumero() {
        return cambiNumero;
    }

    /**
     *
     * @param cambiNumero
     * The cambiNumero
     */
    public void setCambiNumero(List<Object> cambiNumero) {
        this.cambiNumero = cambiNumero;
    }

    /**
     *
     * @return
     * The hasProvvedimenti
     */
    public Boolean getHasProvvedimenti() {
        return hasProvvedimenti;
    }

    /**
     *
     * @param hasProvvedimenti
     * The hasProvvedimenti
     */
    public void setHasProvvedimenti(Boolean hasProvvedimenti) {
        this.hasProvvedimenti = hasProvvedimenti;
    }

    /**
     *
     * @return
     * The descOrientamento
     */
    public List<String> getDescOrientamento() {
        return descOrientamento;
    }

    /**
     *
     * @param descOrientamento
     * The descOrientamento
     */
    public void setDescOrientamento(List<String> descOrientamento) {
        this.descOrientamento = descOrientamento;
    }

    /**
     *
     * @return
     * The compOraUltimoRilevamento
     */
    public String getCompOraUltimoRilevamento() {
        return compOraUltimoRilevamento;
    }

    /**
     *
     * @param compOraUltimoRilevamento
     * The compOraUltimoRilevamento
     */
    public void setCompOraUltimoRilevamento(String compOraUltimoRilevamento) {
        this.compOraUltimoRilevamento = compOraUltimoRilevamento;
    }

    /**
     *
     * @return
     * The motivoRitardoPrevalente
     */
    public Object getMotivoRitardoPrevalente() {
        return motivoRitardoPrevalente;
    }

    /**
     *
     * @param motivoRitardoPrevalente
     * The motivoRitardoPrevalente
     */
    public void setMotivoRitardoPrevalente(Object motivoRitardoPrevalente) {
        this.motivoRitardoPrevalente = motivoRitardoPrevalente;
    }



    /**
     *
     * @return
     * The numeroTreno
     */
    public Long getNumeroTreno() {
        return numeroTreno;
    }

    /**
     *
     * @param numeroTreno
     * The numeroTreno
     */
    public void setNumeroTreno(Long numeroTreno) {
        this.numeroTreno = numeroTreno;
    }

    /**
     *
     * @return
     * The categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     * The categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }



    /**
     *
     * @return
     * The origine
     */
    public String getOrigine() {
        return origine;
    }

    /**
     *
     * @param origine
     * The origine
     */
    public void setOrigine(String origine) {
        this.origine = origine;
    }



    /**
     *
     * @return
     * The destinazione
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     *
     * @param destinazione
     * The destinazione
     */
    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    /**
     *
     * @return
     * The codDestinazione
     */
    public Object getCodDestinazione() {
        return codDestinazione;
    }

    /**
     *
     * @param codDestinazione
     * The codDestinazione
     */
    public void setCodDestinazione(Object codDestinazione) {
        this.codDestinazione = codDestinazione;
    }


    /**
     *
     * @return
     * The origineZero
     */
    public String getOrigineZero() {
        return origineZero;
    }

    /**
     *
     * @param origineZero
     * The origineZero
     */
    public void setOrigineZero(String origineZero) {
        this.origineZero = origineZero;
    }

    /**
     *
     * @return
     * The destinazioneZero
     */


    /**
     *
     * @return
     * The orarioArrivoZero
     */
    public Long getOrarioArrivoZero() {
        return orarioArrivoZero;
    }

    /**
     *
     * @param orarioArrivoZero
     * The orarioArrivoZero
     */
    public void setOrarioArrivoZero(Long orarioArrivoZero) {
        this.orarioArrivoZero = orarioArrivoZero;
    }

    /**
     *
     * @return
     * The circolante
     */
    public Boolean getCircolante() {
        return circolante;
    }

    /**
     *
     * @param circolante
     * The circolante
     */
    public void setCircolante(Boolean circolante) {
        this.circolante = circolante;
    }


    /**
     *
     * @return
     * The inStazione
     */
    public Boolean getInStazione() {
        return inStazione;
    }

    /**
     *
     * @param inStazione
     * The inStazione
     */
    public void setInStazione(Boolean inStazione) {
        this.inStazione = inStazione;
    }

    /**
     *
     * @return
     * The haCambiNumero
     */
    public Boolean getHaCambiNumero() {
        return haCambiNumero;
    }

    /**
     *
     * @param haCambiNumero
     * The haCambiNumero
     */
    public void setHaCambiNumero(Boolean haCambiNumero) {
        this.haCambiNumero = haCambiNumero;
    }

    /**
     *
     * @return
     * The nonPartito
     */
    public Boolean getNonPartito() {
        return nonPartito;
    }

    /**
     *
     * @param nonPartito
     * The nonPartito
     */
    public void setNonPartito(Boolean nonPartito) {
        this.nonPartito = nonPartito;
    }

    /**
     *
     * @return
     * The provvedimento
     */
    public Long getProvvedimento() {
        return provvedimento;
    }

    /**
     *
     * @param provvedimento
     * The provvedimento
     */
    public void setProvvedimento(Long provvedimento) {
        this.provvedimento = provvedimento;
    }

    /**
     *
     * @return
     * The riprogrammazione
     */
    public Object getRiprogrammazione() {
        return riprogrammazione;
    }

    /**
     *
     * @param riprogrammazione
     * The riprogrammazione
     */
    public void setRiprogrammazione(Object riprogrammazione) {
        this.riprogrammazione = riprogrammazione;
    }

    /**
     *
     * @return
     * The orarioPartenza
     */
    public Long getOrarioPartenza() {
        return orarioPartenza;
    }

    /**
     *
     * @param orarioPartenza
     * The orarioPartenza
     */
    public void setOrarioPartenza(Long orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    /**
     *
     * @return
     * The orarioArrivo
     */
    public Long getOrarioArrivo() {
        return orarioArrivo;
    }

    /**
     *
     * @param orarioArrivo
     * The orarioArrivo
     */
    public void setOrarioArrivo(Long orarioArrivo) {
        this.orarioArrivo = orarioArrivo;
    }

    /**
     *
     * @return
     * The stazionePartenza
     */
    public Object getStazionePartenza() {
        return stazionePartenza;
    }

    /**
     *
     * @param stazionePartenza
     * The stazionePartenza
     */
    public void setStazionePartenza(Object stazionePartenza) {
        this.stazionePartenza = stazionePartenza;
    }

    /**
     *
     * @return
     * The stazioneArrivo
     */
    public Object getStazioneArrivo() {
        return stazioneArrivo;
    }

    /**
     *
     * @param stazioneArrivo
     * The stazioneArrivo
     */
    public void setStazioneArrivo(Object stazioneArrivo) {
        this.stazioneArrivo = stazioneArrivo;
    }

    /**
     *
     * @return
     * The statoTreno
     */
    public Object getStatoTreno() {
        return statoTreno;
    }

    /**
     *
     * @param statoTreno
     * The statoTreno
     */
    public void setStatoTreno(Object statoTreno) {
        this.statoTreno = statoTreno;
    }

    /**
     *
     * @return
     * The corrispondenze
     */
    public Object getCorrispondenze() {
        return corrispondenze;
    }

    /**
     *
     * @param corrispondenze
     * The corrispondenze
     */
    public void setCorrispondenze(Object corrispondenze) {
        this.corrispondenze = corrispondenze;
    }

    /**
     *
     * @return
     * The servizi
     */
    public List<Object> getServizi() {
        return servizi;
    }

    /**
     *
     * @param servizi
     * The servizi
     */
    public void setServizi(List<Object> servizi) {
        this.servizi = servizi;
    }

    /**
     *
     * @return
     * The ritardo
     */
    public Long getRitardo() {
        return ritardo;
    }

    /**
     *
     * @param ritardo
     * The ritardo
     */
    public void setRitardo(Long ritardo) {
        this.ritardo = ritardo;
    }


    /**
     *
     * @return
     * The compOrarioPartenzaZeroEffettivo
     */
    public String getCompOrarioPartenzaZeroEffettivo() {
        return compOrarioPartenzaZeroEffettivo;
    }

    /**
     *
     * @param compOrarioPartenzaZeroEffettivo
     * The compOrarioPartenzaZeroEffettivo
     */
    public void setCompOrarioPartenzaZeroEffettivo(String compOrarioPartenzaZeroEffettivo) {
        this.compOrarioPartenzaZeroEffettivo = compOrarioPartenzaZeroEffettivo;
    }

    /**
     *
     * @return
     * The compOrarioArrivoZeroEffettivo
     */
    public String getCompOrarioArrivoZeroEffettivo() {
        return compOrarioArrivoZeroEffettivo;
    }

    /**
     *
     * @param compOrarioArrivoZeroEffettivo
     * The compOrarioArrivoZeroEffettivo
     */
    public void setCompOrarioArrivoZeroEffettivo(String compOrarioArrivoZeroEffettivo) {
        this.compOrarioArrivoZeroEffettivo = compOrarioArrivoZeroEffettivo;
    }

    /**
     *
     * @return
     * The compOrarioPartenzaZero
     */
    public String getCompOrarioPartenzaZero() {
        return compOrarioPartenzaZero;
    }

    /**
     *
     * @param compOrarioPartenzaZero
     * The compOrarioPartenzaZero
     */
    public void setCompOrarioPartenzaZero(String compOrarioPartenzaZero) {
        this.compOrarioPartenzaZero = compOrarioPartenzaZero;
    }

    /**
     *
     * @return
     * The compOrarioArrivoZero
     */
    public String getCompOrarioArrivoZero() {
        return compOrarioArrivoZero;
    }

    /**
     *
     * @param compOrarioArrivoZero
     * The compOrarioArrivoZero
     */
    public void setCompOrarioArrivoZero(String compOrarioArrivoZero) {
        this.compOrarioArrivoZero = compOrarioArrivoZero;
    }

    /**
     *
     * @return
     * The compOrarioArrivo
     */
    public String getCompOrarioArrivo() {
        return compOrarioArrivo;
    }

    /**
     *
     * @param compOrarioArrivo
     * The compOrarioArrivo
     */
    public void setCompOrarioArrivo(String compOrarioArrivo) {
        this.compOrarioArrivo = compOrarioArrivo;
    }

    /**
     *
     * @return
     * The compOrarioPartenza
     */
    public String getCompOrarioPartenza() {
        return compOrarioPartenza;
    }

    /**
     *
     * @param compOrarioPartenza
     * The compOrarioPartenza
     */
    public void setCompOrarioPartenza(String compOrarioPartenza) {
        this.compOrarioPartenza = compOrarioPartenza;
    }

    /**
     *
     * @return
     * The compOrientamento
     */
    public List<String> getCompOrientamento() {
        return compOrientamento;
    }

    /**
     *
     * @param compOrientamento
     * The compOrientamento
     */
    public void setCompOrientamento(List<String> compOrientamento) {
        this.compOrientamento = compOrientamento;
    }

    /**
     *
     * @return
     * The compTipologiaTreno
     */
    public String getCompTipologiaTreno() {
        return compTipologiaTreno;
    }

    /**
     *
     * @param compTipologiaTreno
     * The compTipologiaTreno
     */
    public void setCompTipologiaTreno(String compTipologiaTreno) {
        this.compTipologiaTreno = compTipologiaTreno;
    }

    /**
     *
     * @return
     * The compRitardo
     */
    public List<String> getCompRitardo() {
        return compRitardo;
    }

    /**
     *
     * @param compRitardo
     * The compRitardo
     */
    public void setCompRitardo(List<String> compRitardo) {
        this.compRitardo = compRitardo;
    }

    /**
     *
     * @return
     * The compRitardoAndamento
     */
    public List<String> getCompRitardoAndamento() {
        return compRitardoAndamento;
    }

    /**
     *
     * @param compRitardoAndamento
     * The compRitardoAndamento
     */
    public void setCompRitardoAndamento(List<String> compRitardoAndamento) {
        this.compRitardoAndamento = compRitardoAndamento;
    }

    /**
     *
     * @return
     * The compInStazionePartenza
     */
    public List<String> getCompInStazionePartenza() {
        return compInStazionePartenza;
    }

    /**
     *
     * @param compInStazionePartenza
     * The compInStazionePartenza
     */
    public void setCompInStazionePartenza(List<String> compInStazionePartenza) {
        this.compInStazionePartenza = compInStazionePartenza;
    }

    /**
     *
     * @return
     * The compInStazioneArrivo
     */
    public List<String> getCompInStazioneArrivo() {
        return compInStazioneArrivo;
    }

    /**
     *
     * @param compInStazioneArrivo
     * The compInStazioneArrivo
     */
    public void setCompInStazioneArrivo(List<String> compInStazioneArrivo) {
        this.compInStazioneArrivo = compInStazioneArrivo;
    }

    /**
     *
     * @return
     * The compDurata
     */
    public String getCompDurata() {
        return compDurata;
    }

    /**
     *
     * @param compDurata
     * The compDurata
     */
    public void setCompDurata(String compDurata) {
        this.compDurata = compDurata;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


