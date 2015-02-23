package com.example.lisamazzini.train_app;


import com.example.lisamazzini.train_app.model.Constants;
import com.example.lisamazzini.train_app.model.treno.ListWrapper;
import com.example.lisamazzini.train_app.model.treno.Treno;
import com.example.lisamazzini.train_app.model.treno.Fermate;

import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe per tutti i metodi necessari all'elaborazione di dati che viene richiesta in più punti del
 * dell'applicazione; per questo sono statici.
 *
 * @author lisamazzini
 * @author albertogiunta
 */
public final class Utilities {

    private static final int FIVE = 5;
    private static final long TWO_MIN_DIFFERENCE = 2L;
    private static final String SPLITTER = "\\|";
    private Utilities() { }

    /**
     * Metodo che converte i millisecondi in una stringa rappresentante l'ora.
     * @param millis millisecondi da convertire
     * @return la stringa che rappresenta l'ora in formato HH:mm
     */
    public static String fromMsToTime(final Long millis) {
        if (millis == null) {
            return "--";
        }
        DateTime date = new DateTime(millis);
        date.plusHours(1);
        return DateTimeFormat.forPattern("HH:mm").print(date);
    }

    /**
     * Metodo che elabora i dati ottenuti in risposta dal server dall'indirizzo
     * http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/cercaNumeroTrenoTrenoAutocomplete/*numeroTreno*
     * che sono in formato "608 - LECCE|608-S11145", restituendoli in un array.
     *
     * @param data stringa dei dati
     * @return String[] con i dati divisi
     */
    public static String[] splitString(final String data) {
        final String[] result = new String[3];
        result[0] = data.split(SPLITTER)[1].split("-")[0];    //numero
        result[1] = data.split(SPLITTER)[1].split("-")[1];    //codice
        result[2] = data.split(SPLITTER)[0].split("-")[1];    //nome
        return result;
    }

    /**
     * Metodo che elabora i dati ottenuti in risposta dal server dall'indirizzo
     * http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/*stazione*
     * che sono in formato "PESARO|S07104", restituendoli in un array fatto di [PESARO, 07104].
     * @param data stringa dei dati
     * @return String[] con i dati divisi
     */
    public static String[] splitStationForJourneySearch(final String data) {
        return data.split(SPLITTER + "S");
    }

    /**
     * Metodo che elabora i dati ottenuti in risposta dal server dall'indirizzo
     * http://www.viaggiatreno.it/viaggiatrenomobile/resteasy/viaggiatreno/autocompletaStazione/*stazione*
     * che sono in formato "PESARO|S07104", restituendoli in un array fatto di [PESARO, S07104].
     * @param data stringa dei dati
     * @return String[] con i dati divisi
     */
    public static String[] splitStationForTrainSearch(final String data) {
        return data.split(SPLITTER);
    }

    public static String trimAndCapitalizeString(final String s) {
        return WordUtils.capitalizeFully(s).replaceAll("\\s+$", "");
    }

    public static String getShorterString(final String s) {
        return s.length() > 12 ? s.substring(0, 10).concat("...") : s;
    }

    /**
     * Metodo che presa una stringa che indica un orario (es 12:30) la elabora e restituisce un MutableDateTime
     * impostato al giorno corrente e all'ora indicata dalla stringa.
     *
     * @param time stringa indicante l'ora
     * @return MutableDateTime impostato al giorno corrente e alla determinata ora
     */
    public static MutableDateTime getDate(final String time) {
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
    public static String getProgress(final Treno train) {
        Long delta = 0L;
        Long intermediateDelta;
        List<Fermate> visited = new LinkedList<>();

        for (Fermate f : train.getFermate()) {
            if (f.getActualFermataType() != Constants.EMPTY) {
                visited.add(f);
            }
        }

        if (visited.size() <= FIVE) {
            for (int i = visited.size() - 2; i >= 0; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                delta += intermediateDelta;
            }
        } else {
            for (int i = visited.size() - 2; i >= visited.size() - FIVE - 1; i--) {
                intermediateDelta = visited.get(i + 1).getRitardo() - visited.get(i).getRitardo();
                delta += intermediateDelta;
            }
        }

        if (delta > TWO_MIN_DIFFERENCE) {
            return "In rallentamento";
        }
        if (delta < -TWO_MIN_DIFFERENCE) {
            return "In recupero";
        }

        return "Costante";
    }

    /**
     * Metodo che genera l'URL per la connessione all'autocomplete per le stazioni.
     * @param stationName nome della stazione
     * @return URL completo
     * @throws MalformedURLException se l'URL è formato male
     */
    public static URL generateStationAutocompleteURL(final String stationName) throws MalformedURLException {
        return new URL(Constants.ROOT + Constants.STATION_AUTOCOMPLETE + stationName + "?q=" + stationName);
    }

    /**
     * Metodo che genera l'URL per la connessione all'autocomplete per il numero di treno.
     * @param trainNumber numero treno
     * @return URL completo
     * @throws MalformedURLException se l'URL è formato male
     */
    public static URL generateTrainAutocompleteURL(final String trainNumber) throws MalformedURLException {
        return new URL(Constants.ROOT + Constants.TRAIN_AUTOCOMPLETE + trainNumber);
    }

    /**
     * Metodo che apre un BufferedReader su una pagina e crea un lista di String ("wrappata" poi in un ListWrapper)
     * contenente tutte le righe presenti nella pagina.
     *
     * es:
     *  31 - TARVISIO BOSCOVERDE|31-S03015
     *  31 - MILANO NORD CADORNA|31-N00001
     *
     * @param url URL della pagina a cui connettersi
     * @return ListWrapper con le stringhe
     * @throws IOException
     */
    public static ListWrapper fetchData(final URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        List<String> result = new LinkedList<>();
        while ((inputLine = in.readLine()) != null) {
            result.add(inputLine);
        }
        in.close();
        return new ListWrapper(result);
    }

    /**
     * Metodo che stabilisce se una lista ha un solo elemento.
     *
     * @param list lista da controllare
     * @return true se ha un solo elemento, false altrimenti
     */
    public static boolean isOneResult(final List<String> list) {
        return list.size() == 1;
    }

}
