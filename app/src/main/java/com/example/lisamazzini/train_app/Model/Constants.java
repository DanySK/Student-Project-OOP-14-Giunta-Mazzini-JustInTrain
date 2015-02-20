package com.example.lisamazzini.train_app.model;

/**
 * Contains constants for the whole project.
 *
 * @author Alberto Giunta
 */

public final class Constants {

    public static final String ROOT = "http://193.138.161.135/viaggiatrenomobile/resteasy/viaggiatreno";

    public static final String STATION_AUTOCOMPLETE = "/autocompletaStazione/";
    public static final String TRAIN_AUTOCOMPLETE = "/cercaNumeroTrenoTrenoAutocomplete/";
    public static final String SEPARATOR = "%";

    public static final String WITH_IDS = "withIDs";

    public static final String WITH_STATIONS = "withStations";
    public static final String JOURNEY_PREF_FILE = "JourneyPref";

    public static final String TRAIN_PREF_FILE = "TrainPref";
    public static final String SDF = "yyyy-MM-dd'T'HH:mm:ss";

    public static final String SDF_NO_SECS = "yyyy-MM-dd'T'HH:mm:00";
    public static final String DELAY_ACH = "Hai accumulato 60 minuti di ritardo!";

    public static final String PIN_ACH = "Hai pinnato 10 treni!!";
    public static final String TRAIN_N_EXTRA = "trainNumber";

    public static final String ID_ORIGIN_EXTRA = "idOrigine";
    public static final String DEPARTURE_TIME_EXTRA = "oraPartenza";
    public static final String DEPARTURE_STAT_EXTRA = "departureStation";

    public static final String ARRIVAL_STAT_EXTRA = "arrivalStation";
    public static final String REQUESTED_TIME_EXTRA = "requestedTime";
    public static final String IS_CUSTOM_TIME_EXTRA = "isCustomTime";
    public static final String ACTION_REFRESH = "Aggiorna";
    public static final String ACTION_DELETE = "Elimina";
    public static final String ACH_STORE_FILE = "ACHIEVEMENT_STORE";

    public static final String ACH_DATA_FILE = "ACHIEVEMENT_DATA";
    public static final String JOURNEY_FAVOURITE = "journey favourite";

    public static final String STATION_FAVOURITE = "station favourite";
    public static final String WRONG_TRAIN_TITLE = "Numero treno non valido!";

    public static final String WRONG_TRAIN = "Il numero inserito non corrisponde a nessun treno";
    public static final String WRONG_STATION_TITLE = "Stazione inesistente!";
    public static final String WRONG_STATION = "Il nome inserito non corrisponde a nessun risultato";
    public static final String CONNECTION_ERROR_TITLE = "Problemi di connessione!";
    public static final String CONNECTION_ERROR = "Controllare la propria connessione internet";
    public static final String NO_AVAILABLE_SOLUTION_TITLE = "Nessuna soluzione trovata!";
    public static final String NO_AVAILABLE_SOLUTION = "Nessuna soluzione disponibile per la tratta selezionata";
    public static final String SERVICE_NOT_AVAILABLE_TITLE = "Servizio non disponibile!";
    public static final String SERVICE_NOT_AVAILABLE = "Il servizio Viaggiatreno.com è offline o in manuetenzione";
    public static final String OK_MSG = "Ok";

    public static final int EMPTY = 0;

    private Constants() {
    }
}
