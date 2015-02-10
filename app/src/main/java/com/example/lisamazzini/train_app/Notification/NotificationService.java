package com.example.lisamazzini.train_app.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.lisamazzini.train_app.Controller.AbstractListener;
import com.example.lisamazzini.train_app.Controller.StationListController;
import com.example.lisamazzini.train_app.Controller.TrainRequest;
import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;
import java.util.Calendar;

/**
 * Created by Lisa Mazzini
 */
public class NotificationService extends Service {

    //This is the intent to refresh the Notification, that will start the service again
    private PendingIntent pIntentRefresh;
    //This is the intent to stop the service
    private PendingIntent pIntentClose;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);

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
        spiceManager = new SpiceManager(UncachedSpiceService.class);

        this.numeroTreno = intent.getStringExtra("number");
        this.IDorigine = intent.getStringExtra("idOrigine");
        this.orarioPartenza = intent.getStringExtra("oraPartenza");

        //Here I set the data for the refresh intent (so the data will be the same)
        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");

        //Here I set the close intent, just adding the action.
        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction("Elimina");

        intentRefresh.putExtra("number", this.numeroTreno);
        intentRefresh.putExtra("idOrigine", this.IDorigine);
        intentRefresh.putExtra("oraPartenza", this.orarioPartenza);

        Intent intentStart = new Intent(NotificationService.this, NotificationService.class);
        intentStart.putExtra("number", this.numeroTreno);
        intentStart.putExtra("idOrigine", this.IDorigine);
        intentStart.putExtra("oraPartenza", this.orarioPartenza);
        PendingIntent pStart = PendingIntent.getService(this, 0, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentClose = PendingIntent.getBroadcast(this, 1, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentRefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);

        DateTime now = new DateTime(Calendar.getInstance().getTime());
        MutableDateTime departureTime = Utilities.getDate(this.orarioPartenza);

        Integer timeDifference = Minutes.minutesBetween(now, departureTime).getMinutes();

        if(timeDifference > 15){
           // è presto
            departureTime.addMinutes(-15);
            Long millis = departureTime.getMillis();
            Log.d("Quanti siete", "" + millis);
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, millis, pStart);
        }else {
            // è ora
            spiceManager.execute(new TrainRequest(this.numeroTreno, this.IDorigine), new ResultListener());
        }
        spiceManager.start(this);
        // If the OS stops the service after running out of memory, the service will be started again with da same intent
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private class ResultListener extends AbstractListener<NewTrain> {

        @Override
        public Context getDialogContext() {
            return NotificationService.this;
        }

        @Override
        public void onRequestSuccess(NewTrain train) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification not;
            Intent intentHome = new Intent(NotificationService.this, StationListActivity.class);
            intentHome.putExtra("trainNumber", numeroTreno);
            intentHome.putExtra("stationCode", IDorigine);
            PendingIntent home = PendingIntent.getActivity(NotificationService.this, 1, intentHome, PendingIntent.FLAG_UPDATE_CURRENT);

            //If the train is not departed yet the notification will show the data
            if(notDeparted(train)) {
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setTicker("Treno in arrivo!")
                        .setContentIntent(home)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(train.getNumeroTreno() + " " + train.getCategoria())
                                .addLine("Il treno non è ancora partito"))
                        .build();
            //Else, the notification is empty
            }else if(isArrived(train)){
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setContentIntent(home)
                        .setStyle(new NotificationCompat.InboxStyle()
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
                not = builder
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setTicker("Treno in arrivo!")
                        .setContentIntent(home)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle(train.getNumeroTreno() + " " + train.getCategoria())
                                .addLine(ritardo)
                                .addLine("Andamento: " + Utilities.getProgress(train))
                                .addLine("Visto a " + train.getStazioneUltimoRilevamento() + " alle " + train.getCompOraUltimoRilevamento()))
                        .build();
            }
            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);
        }

        private boolean notDeparted(NewTrain train){
            return train.getFermate().get(0).getActualFermataType() == 0;
        }

        private boolean isArrived(NewTrain train){
            return train.getFermate().get(train.getFermate().size()-1).getActualFermataType() == 1;
        }

    }
}
