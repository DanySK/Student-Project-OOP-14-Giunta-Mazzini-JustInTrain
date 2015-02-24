package com.example.lisamazzini.train_app.model;

/**
 * Contains constants for the whole project.
 *
 * @author albertogiunta
 * @author lisamazzini
 */

public final class Constants {

    /**
     * Root url.
     */
    public static final String ROOT = "http://193.138.161.135/viaggiatrenomobile/resteasy/viaggiatreno";

    /**
     * autocomplete action per le stazioni.
     */
    public static final String STATION_AUTOCOMPLETE = "/autocompletaStazione/";
    /**
     *  autocomplete action per i treni.
     */
    public static final String TRAIN_AUTOCOMPLETE = "/cercaNumeroTrenoTrenoAutocomplete/";

    /**
     * Separatore usato nella concatenzaione di id e nomi di stazioni nelle mappe dei preferiti.
     */
    public static final String SEPARATOR = "%";

    /**
     * Modalità di ricerca (con id).
     */
    public static final String WITH_IDS = "withIDs";

    /**
     * Modalità di ricerca (con nomi delle stazioni).
     */
    public static final String WITH_STATIONS = "withStations";

    /**
     * Nome del file dei preferiti di journeys.
     */
    public static final String JOURNEY_PREF_FILE = "JourneyPref";

    /**
     * Nome del file dei preferiti di treni.
     */
    public static final String TRAIN_PREF_FILE = "TrainPref";

    /**
     * SimpleDateFormat di tipo standard unicode.
     */
    public static final String SDF = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * SimpleDateFormat di tipo standard unicode (con i secondi già impostati a :00).
     */
    public static final String SDF_NO_SECS = "yyyy-MM-dd'T'HH:mm:00";

    /**
     * Chiave dell'extra per passare il numero del treno.
     */
    public static final String TRAIN_N_EXTRA = "trainNumber";

    /**
     * Chiave dell'extra per passare l'id della stazione di origine.
     */
    public static final String ID_ORIGIN_EXTRA = "idOrigine";

    /**
     * Chiave dell'extra per passare l'ora di partenza.
     */
    public static final String DEPARTURE_TIME_EXTRA = "oraPartenza";

    /**
     * Chiave dell'extra per passare il nome della stazione di partenza.
     */
    public static final String DEPARTURE_STAT_EXTRA = "departureStation";

    /**
     * Chiave dell'extra per passare il nome della stazione di arrivo.
     */
    public static final String ARRIVAL_STAT_EXTRA = "arrivalStation";

    /**
     * Chiave dell'extra per passare l'orario di ricerca.
     */
    public static final String REQUESTED_TIME_EXTRA = "requestedTime";

    /**
     * Chiave dell'extra per passare il fatto che l'utente abbia selezionato un orario o meno.
     */
    public static final String IS_CUSTOM_TIME_EXTRA = "isCustomTime";

    /**
     * Chiave dell'extra per passare l'azione di aggiornare al service della notifica.
     */
    public static final String ACTION_REFRESH = "Aggiorna";

    /**
     * Chiave dell'extra per passare l'azione di annullare tutto al service della notifica.
     */
    public static final String ACTION_DELETE = "Elimina";

    /**
     * Nome del file per salvare i dati degli achievements.
     */
    public static final String ACH_STORE_FILE = "ACHIEVEMENT_STORE";

    /**
     * Nome del file per salvare i dati degli achievements.
     */
    public static final String ACH_DATA_FILE = "ACHIEVEMENT_DATA";

    /**
     * Costante "vuoto".
     */
    public static final int EMPTY = 0;

    /**
     * Costante "in tempo".
     */
    public static final int ON_TIME = 0;

    /**
     * Valore che assume il campo di una fermata cancellata.
     */
    public static final long STATION_CANCELLED = 3L;

    /**
     * Valore che assume il campo di una fermata straordinaria.
     */
    public static final long STATION_EXTRA = 2L;

    /**
     * Valore che assume il campo di una fermata visitata.
     */
    public static final long STATION_VISITED = 1L;

    /**
     * Testo per l'achievement.
     */
    public static final String PIN_ACH = "Hai pinnato 10 treni!!";

    /**
     * Testo per l'achievement.
     */
    public static final String DELAY_ACH = "Hai accumulato 60 minuti di ritardo!";

    /**
     * Titolo per il dialog "treno sbagliato".
     */
    public static final String WRONG_TRAIN_TITLE = "Numero treno non valido!";

    /**
     * Testo per il dialog "treno sbagliato".
     */
    public static final String WRONG_TRAIN = "Il numero inserito non corrisponde a nessun treno";

    /**
     * Titolo per il dialog "stazione sbagliata".
     */
    public static final String WRONG_STATION_TITLE = "Stazione inesistente!";

    /**
     * Testo per il dialog "stazione sbagliato".
     */
    public static final String WRONG_STATION = "Il nome inserito non corrisponde a nessun risultato";

    /**
     * Titolo per il dialog "problemi di connessione".
     */
    public static final String CONNECTION_ERROR_TITLE = "Problemi di connessione!";

    /**
     * Testo per il dialog "problemi di connessione".
     */
    public static final String CONNECTION_ERROR = "Controllare la propria connessione internet";

    /**
     * Titolo per il dialog "nessuna soluzione".
     */
    public static final String NO_AVAILABLE_SOLUTION_TITLE = "Nessuna soluzione trovata!";

    /**
     * Testo per il dialog "nessuna soluzione".
     */
    public static final String NO_AVAILABLE_SOLUTION = "Nessuna soluzione disponibile per la tratta selezionata";

    /**
     * Titolo per il dialog "servizio down".
     */
    public static final String SERVICE_NOT_AVAILABLE_TITLE = "Servizio non disponibile!";

    /**
     * Testo per il dialog "servizio down".
     */
    public static final String SERVICE_NOT_AVAILABLE = "Il servizio Viaggiatreno.com è offline o in manuetenzione";

    /**
     * Messaggio d'accettazione.
     */
    public static final String OK_MSG = "Ok";

    /**
     * Testo achievement ritardatario livello 1.
     */
    public static final String ACH_DELAY_LV_1 = "Ritardatario! (60 minuti)";

    /**
     * Testo achievement pinnatore livello 1.
     */
    public static final String ACH_PINNER_LV_1 = "Pinnatore seriale!";

    /**
     * Testo per la ricerca in corso nel toast.
     */
    public static final String TOAST_PENDING_RESEARCH = "Ricerca in corso...";

    /**
     * Testo per la ricerca in corso nella toolbar.
     */
    public static final String TOOLBAR_PENDING_RESEARCH = "Cerco...";

    /**
     * Testo per la toolbar dei treni preferiti.
     */
    public static final String TOOLBAR_FAVOURITE_TRAINS = "Treni preferiti";

    /**
     * Testo per la toolbar dei treni favoriti, se nessuna è presente.
     */
    public static final String TOOLBAR_NO_FAV_TRAIN = "Nessuna treno preferito!";

    /**
     * Testo per la toolbar degli achievement sbloccati.
     */
    public static final String TOOLBAR_ACHIEVEMENTS = "Achievement sbloccati";

    /**
     * Testo per la toolbar delle tratte favorite, se nessuna è presente.
     */
    public static final String TOOLBAR_NO_FAV_JOURNEY = "Nessuna tratta preferita!";

    private Constants() {
    }
}
