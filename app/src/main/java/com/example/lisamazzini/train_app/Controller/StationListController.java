package com.example.lisamazzini.train_app.controller;

import com.example.lisamazzini.train_app.model.treno.Fermate;
import com.example.lisamazzini.train_app.network.data.TrainDataRequest;
import com.example.lisamazzini.train_app.network.total.TrainRequest;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.Utilities;

import java.util.LinkedList;
import java.util.List;

public class StationListController {

//    private final String trainNumber;
//    private String trainCode;


    private String trainNumber;
    private String[] trainDetails;
    private String trainCode;
    private List<Fermate> fermateList = new LinkedList<>();

    public String[] getTrainDetails() {
        return trainDetails;
    }

    public void setTrainDetails(final String[] trainDetails) {
        this.trainDetails = trainDetails;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(final String trainCode) {
        this.trainCode = trainCode;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(final String trainNumber) {
        this.trainNumber = trainNumber;
    }
    public List<Fermate> getFermateList() {
        return fermateList;
    }

    public void setFermateList(final Treno trainResponse) {
        fermateList.addAll(trainResponse.getFermate());
    }

    public StationListController(){

    }

    /**
     * Metodo che restituisce l'istanza della Request da passare allo SpiceManager
     * @return TrainDataRequest relativa al trainNumber corrente
     */
    public TrainDataRequest getNumberRequest(){
        return new TrainDataRequest(this.trainNumber);
    }

    /**
     * Metodo che setta il codice della stazione di origine del treno corrente
     * @param code codice della stazione di origine
     */

    /**
     * Metodo che restituisce l'istanza della Request da passare allo SpiceManager
     * @return TrainRequest relativa al numero e al codice di origine
     *
     * @throws java.lang.IllegalStateException se il codice non è stato settato
     */
    public TrainRequest getNumberAndCodeRequest(){
        if(this.trainCode == null){
            throw new IllegalStateException("Settare il codice della stazione di origine");
        }
        return new TrainRequest(this.trainNumber, this.trainCode);
    }

    /**
     * Metodo che presa in ingresso una lista di stringhe di tipo "31 - MILANO NORD CADORNA|31-N00001" le
     * spezza in array e mette gli array in una matrice
     * @param data lista di stringhe con i dati
     * @return matrice con i dati suddivisi
     */
    public String[][] computeMatrix(final List<String> data){
        final String[][] dataMatrix = new String[data.size()][3];
        for (int i = 0 ; i < data.size(); i++){
            dataMatrix[i] = computeData(data.get(i));
        }
        return dataMatrix;
    }

    /**
     * Metodo che presa la matrice di dati restituisce un array con tutti i nomi delle stazioni, da mostrare
     * all'utente per poter scegliere la stazione desiderata
     * @param dataMatrix matrice di dati
     * @return l'array di stringhe
     */

    public String[] computeChoices(final String[][] dataMatrix){
        String[] choices = new String[dataMatrix.length];
        for (int i = 0 ; i < dataMatrix.length; i++){
            choices[i] = computeSingleChoice(dataMatrix[i]);
        }
        return choices;
    }

    /**
     * Metodo che crea la singola scelta
     * @param first array da cui prendere i dati
     * @return stringa che descrive la scelta
     */
    private String computeSingleChoice(final String[] first){
        final String result= "Treno " + first[0] + " in partenza da " + first[2];
        return result;
    }

    /**
     * Metodo che restituisce l'andamento del treno
     * @param train treno da analizzare
     * @return stringa che descrive l'andamento del treno
     */
    public String getProgress(final Treno train){
        return Utilities.getProgress(train);
    }

    public String[] computeData(final String s) {
        return Utilities.splitString(s);
    }
}