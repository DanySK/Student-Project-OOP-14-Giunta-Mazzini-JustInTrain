package com.example.lisamazzini.train_app.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.TotalRequests.TrainRequest;
import com.example.lisamazzini.train_app.GUI.Activity.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Treno.Treno;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;

import java.util.Calendar;

/**
 * Questa classe costituisce il Service che opera in background per gestire la comparsa e l'aggiornamento
 * della notifica di un treno "pinnato"; il service infatti si viene avviato alla pressione del Pin e
 * parte subito; se è presto (ovvero mancano più di 15 minuti al suo arrivo alla stazione desiderata)
 * il Service setta un AlarmManager che si occuperà di riinviare l'intent al momento giusto.
 * Se invece mancano meno di 15 minuti, la notifica viene costruita in base allo stato del treno e viene resa
 * visibile.
 * Created by Lisa Mazzini
 */
public class NotificationService extends Service {

    private final static Integer QUARTER = 15;
    private final static Long NOT_VISITED = 0L;
    private final SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private PendingIntent pIntentRefresh;
    private PendingIntent pIntentClose;
    private PendingIntent pIntentStart;
    private String numeroTreno;
    private String orarioPartenza;
    private String IDorigine;

    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){
        super.onStartCommand(intent, flags, startId);

        this.numeroTreno = intent.getStringExtra(Constants.TRAIN_N_EXTRA);
        this.IDorigine = intent.getStringExtra(Constants.ID_ORIGIN_EXTRA);
        this.orarioPartenza = intent.getStringExtra(Constants.DEPARTURE_TIME_EXTRA);

        setIntents();

        DateTime now = new DateTime(Calendar.getInstance().getTime());
        MutableDateTime departureTime = Utilities.getDate(this.orarioPartenza);
        Integer timeDifference = Minutes.minutesBetween(now, departureTime).getMinutes();

        if(timeDifference > QUARTER){
            // è presto
            departureTime.addMinutes(-QUARTER);
            Long millis = departureTime.getMillis();
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, millis, pIntentStart);
        }else {
            // è ora
            spiceManager.execute(new TrainRequest(this.numeroTreno, this.IDorigine), new ResultListener());
        }

        //lo SpiceManager viene avviato solo se non è già avviato
        if(!spiceManager.isStarted()) {
            spiceManager.start(this);
        }

        // Se il sistema operativo decide di interrompere il service per liberare memoria, restituendo questa costante
        // il service verrà riavviato con lo stesso intent
        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setIntents(){
        //Here I set the data for the refresh intent (so the data will be the same)
        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction(Constants.ACTION_REFRESH);

        //Here I set the close intent, just adding the action.
        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction(Constants.ACTION_DELETE);

        intentRefresh.putExtra(Constants.TRAIN_N_EXTRA, this.numeroTreno);
        intentRefresh.putExtra(Constants.ID_ORIGIN_EXTRA, this.IDorigine);
        intentRefresh.putExtra(Constants.DEPARTURE_TIME_EXTRA, this.orarioPartenza);

        Intent intentStart = new Intent(NotificationService.this, NotificationService.class);
        intentStart.putExtra(Constants.TRAIN_N_EXTRA, this.numeroTreno);
        intentStart.putExtra(Constants.ID_ORIGIN_EXTRA, this.IDorigine);
        intentStart.putExtra(Constants.DEPARTURE_TIME_EXTRA, this.orarioPartenza);
        pIntentStart = PendingIntent.getService(this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentClose = PendingIntent.getBroadcast(this, 1, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentRefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);
    }



    /**
     * Inner class adibita alla ricezione dei dati dopo la connessione a internet
     */

    private class ResultListener extends AbstractListener<Treno> {

        @Override
        public Context getDialogContext() {
            return NotificationService.this;
        }

        @Override
        public void onRequestSuccess(Treno train) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification not;
            Intent intentHome = new Intent(NotificationService.this, StationListActivity.class);
            intentHome.putExtra(Constants.TRAIN_N_EXTRA, numeroTreno);
            intentHome.putExtra(Constants.ID_ORIGIN_EXTRA, IDorigine);
            PendingIntent home = PendingIntent.getActivity(NotificationService.this, 1, intentHome, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setSmallIcon(R.drawable.ic_launcher)
                    .setOngoing(true)
                    .addAction(R.drawable.ic_refresh, Constants.ACTION_REFRESH, pIntentRefresh)
                    .addAction(R.drawable.ic_delete, Constants.ACTION_DELETE, pIntentClose)
                    .setTicker("Treno in arrivo!")
                    .setContentIntent(home);

            //If the train is not departed yet the notification will show the data
            if(notDeparted(train)) {
            not = builder.setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(train.getNumeroTreno() + " " + train.getCategoria())
                                .addLine("Il treno non è ancora partito"))
                        .build();
            //Else, the notification is empty
            }else if(isArrived(train)){
                not = builder.setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(train.getNumeroTreno() + " " + train.getCategoria())
                                .addLine("Treno arrivato a destinazione"))
                        .setTicker("Treno in arrivo!")
                        .build();
            }else{
                String ritardo;
                if(train.getRitardo() > 0){
                    ritardo = train.getRitardo() + "' di RITARDO";
                }else if(train.getRitardo() < 0){
                    ritardo = train.getRitardo()*-1 + "' di ANTICIPO";
                }else{
                    ritardo = "in ORARIO";
                }
                not = builder.setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(train.getNumeroTreno() + " " + train.getCategoria())
                                .addLine(ritardo)
                                .addLine("Andamento: " + Utilities.getProgress(train))
                                .addLine("Visto: " + train.getCompOraUltimoRilevamento() + " " + train.getStazioneUltimoRilevamento()))
                        .build();
            }
            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);
        }

        /**
         * Metodo che determina se un treno è partito o meno, controllando se la prima stazione è visitata
         *
         * @param train treno da controllare
         * @return true se è non partito, false se è partito
         */
        private boolean notDeparted(Treno train){
            return train.getFermate().get(0).getActualFermataType() == NOT_VISITED;
        }

        /**
         * Metodo che determina se un treno è arrivato, controllando se l'ultima stazione è visitata.
         * @param train treno da controllare
         * @return true sè è arrivato, false se non è arrivato
         */
        private boolean isArrived(Treno train){
            return train.getFermate().get(train.getFermate().size()-1).getActualFermataType() != NOT_VISITED ;
        }

    }
}
