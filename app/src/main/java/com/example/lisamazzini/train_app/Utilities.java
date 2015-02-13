package com.example.lisamazzini.train_app;


import android.util.Log;

import com.example.lisamazzini.train_app.Model.Treno.ListWrapper;
import com.example.lisamazzini.train_app.Model.Treno.Train;
import com.example.lisamazzini.train_app.Model.Treno.Fermate;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe per tutti i metodi necessari all'elaborazione di dati che viene richiesta in più punti del
 * dell'applicazione; per questo sono statici
 * Created by Lisa Mazzini
 */
public class Utilities {

    /**
     * Metodo che converte i millisecondi in una stringa rappresentante l'ora
     * @param millis millisecondi da convertire
     * @return la stringa che rappresenta l'ora in formato HH:mm
     */
    public static String fromMsToTime(Long millis){
        if(millis == null){
            return "--";
        }
        DateTime date = new DateTime(millis);
        date.plusHours(1);
        return DateTimeFormat.forPattern("HH:mm").print(date);
    }

    /**
     * Metodo che elabora i dati ottenuti in risposta dal server dall'indirizzo
     * http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/*numeroTreno*
     * che sono in formato "608 - LECCE|608-S11145", restituendoli in un array
     *
     * @param data stringa dei dati
     * @return String[] con i dati divisi
     */
    public static String[] splitString(String data){
        final String[] result = new String[3];
        result[0] = data.split("\\|")[1].split("-")[0];    //numero
        result[1] = data.split("\\|")[1].split("-")[1];    //codice
        result[2] = data.split("\\|")[0].split("-")[1];    //nome
        return result;
    }

    /**
     * Metodo che elabora i dati ottenuti in risposta dal server dall'indirizzo
     * http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/*stazione*
     * che sono in formato "PESARO|S07104", restituendoli in un array
     * @param data stringa dei dati
     * @return String[] con i dati divisi
     */
    public static String[] splitJourney(String data) {
        return data.split("\\|S");
    }

    /**
     * Metodo che presa una stringa che indica un orario (es 12:30) la elabora e restituisce un MutableDateTime
     * impostato al giorno corrente e all'ora indicata dalla stringa.
     *
     * @param time stringa indicante l'ora
     * @return MutableDateTime impostato al giorno corrente e alla determinata ora
     */
    public static MutableDateTime getDate(String time){
        DateTime now = new DateTime(Calendar.getInstance().getTime());
        String[] arrTime = time.split(":");
        MutableDateTime date = now.toMutableDateTime();
        date.setDate(Calendar.getInstance().getTimeInMillis());
        date.setTime(Integer.parseInt(arrTime[0]), Integer.parseInt(arrTime[1]), 0, 0);
        return date;
    }

    /**
     * Metodo che calcola l'andamento del treno, sommando le differenze fra i ritardi nelle ultime 5
     * (o meno) stazioni visitate, calcolando la differenza fra i ritardi di due stazioni e sommando
     * tutte le diffenze; in base al risultato restituisce una stringa che descrive l'andamento.
     *
     * @param train treno da analizzare
     * @return stringa che descrive l'andamento
     */
    public static String getProgress(Train train){
        Long delta = 0L;
        Long intermediateDelta = 0L;
        List<Fermate> visited = new LinkedList<>();

        for(Fermate f : train.getFermate()){
            if(f.getActualFermataType() != 0){
                visited.add(f);
            }
        }

        if(visited.size() <= 5) {
            for (int i = visited.size() - 2; i >= 0; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" A intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }else{
            for (int i = visited.size()-2; i >= visited.size()-6; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                Log.d(" B intermediate delta", "" + intermediateDelta);
                delta += intermediateDelta;
            }
        }

        Log.d("delta", "" + delta);

        if(delta > 2L){
            return "In rallentamento";
        }
        if(delta < -2L){
            return "In recupero";
        }

        return "Costante";
    }

    /**
     * Metodo che si apre un BufferedReader su una pagina e crea un lista di String ("wrappata" poi in un ListWrapper)
     * contenente tutte le righe presenti nella pagina
     *
     * es:
     *  31 - TARVISIO BOSCOVERDE|31-S03015
     *  31 - MILANO NORD CADORNA|31-N00001
     *
     * @param url URL della pagina a cui connettersi
     * @return ListWrapper con le stringhe
     * @throws IOException
     */
    public static ListWrapper fetchData(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        List<String> result = new LinkedList<>();
        while ((inputLine = in.readLine()) != null) {
            result.add(inputLine);
        }
        in.close();
        return new ListWrapper(result);
    }
}
