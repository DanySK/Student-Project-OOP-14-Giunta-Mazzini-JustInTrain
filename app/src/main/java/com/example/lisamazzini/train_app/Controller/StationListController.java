package com.example.lisamazzini.train_app.controller;

import com.example.lisamazzini.train_app.model.treno.Fermate;
import com.example.lisamazzini.train_app.network.data.TrainDataRequest;
import com.example.lisamazzini.train_app.network.total.TrainRequest;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.Utilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe controller della StationList view.
 *
 * @author lisamazzini
 */

public class StationListController {

    private String trainNumber;
    private String[] trainDetails;
    private String trainCode;
    private final List<Fermate> fermateList = new LinkedList<>();

    /**
     * Getter per i dettagli del treno.
     * @return array con i dettagli del treno
     */
    public final String[] getTrainDetails() {
        return Arrays.copyOf(trainDetails, trainDetails.length);
    }

    /**
     * Setter per i dettagli del treno.
     * @param pTrainDetails da settare
     */
    public final void setTrainDetails(final String[] pTrainDetails) {
        this.trainDetails = pTrainDetails.clone();
    }

    /**
     * Getter per il codice della stazione di origine del treno.
     * @return numero del treno
     */
    public final String getTrainCode() {
        return trainCode;
    }

    /**
     * Setter per il codice della stazione di origine del treno.
     * @param pTrainCode da settare
     */
    public final void setTrainCode(final String pTrainCode) {
        this.trainCode = pTrainCode;
    }

    /**
     * Getter per il numero del treno.
     * @return numero del treno
     */
    public final String getTrainNumber() {
        return trainNumber;
    }

    /**
     * Setter per il numero del treno.
     * @param pTrainNumber da settare
     */
    public final void setTrainNumber(final String pTrainNumber) {
        this.trainNumber = pTrainNumber;
    }

    /**
     * Getter per la lista di Fermate.
     * @return la lista di Fermate
     */
    public final List<Fermate> getFermateList() {
        return fermateList;
    }

    /**
     * Setter per la lista di Fermate.
     * @param trainResponse treno da cui prendere la lista di fermate
     */
    public final void setFermateList(final Treno trainResponse) {
        fermateList.addAll(trainResponse.getFermate());
    }

    /**
     * Metodo che restituisce l'istanza della Request da passare allo SpiceManager.
     * @return TrainDataRequest relativa al trainNumber corrente
     */
    public final TrainDataRequest getNumberRequest() {
        return new TrainDataRequest(this.trainNumber);
    }

    /**
     * Metodo che restituisce l'istanza della Request da passare allo SpiceManager.
     * @return TrainRequest relativa al numero e al codice di origine
     *
     */
    public final TrainRequest getNumberAndCodeRequest() {
        if (this.trainCode == null) {
            throw new IllegalStateException("Settare il codice della stazione di origine");
        }
        return new TrainRequest(this.trainNumber, this.trainCode);
    }

    /**
     * Metodo che presa in ingresso una lista di stringhe di tipo "31 - MILANO NORD CADORNA|31-N00001" le
     * spezza in array e mette gli array in una matrice.
     * @param data lista di stringhe con i dati
     * @return matrice con i dati suddivisi
     */
    public final String[][] computeMatrix(final List<String> data) {
        final String[][] dataMatrix = new String[data.size()][3];
        for (int i = 0; i < data.size(); i++) {
            dataMatrix[i] = computeData(data.get(i));
        }
        return dataMatrix;
    }

    /**
     * Metodo che presa la matrice di dati restituisce un array con tutti i nomi delle stazioni, da mostrare
     * all'utente per poter scegliere la stazione desiderata.
     * @param dataMatrix matrice di dati
     * @return l'array di stringhe
     */

    public final String[] computeChoices(final String[][] dataMatrix) {
        String[] choices = new String[dataMatrix.length];
        for (int i = 0; i < dataMatrix.length; i++) {
            choices[i] = computeSingleChoice(dataMatrix[i]);
        }
        return choices;
    }

    /**
     * Metodo che crea la singola scelta.
     * @param first array da cui prendere i dati
     * @return stringa che descrive la scelta
     */
    private String computeSingleChoice(final String[] first) {
        return "Treno " + first[0] + " in partenza da " + first[2];
    }

    /**
     * Metodo che restituisce l'andamento del treno.
     * @param train treno da analizzare
     * @return stringa che descrive l'andamento del treno
     */
    public final String getProgress(final Treno train) {
        return Utilities.getProgress(train);
    }

    /**
     * Metodo che elabora i dettagli di un treno spezzando una stringa in un array.
     * @param s stringa con i dati
     * @return array con i dati
     */
    public final String[] computeData(final String s) {
        return Utilities.splitString(s);
    }
}