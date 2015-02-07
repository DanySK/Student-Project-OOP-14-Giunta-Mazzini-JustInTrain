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
import com.example.lisamazzini.train_app.Controller.TrainRequest;
import com.example.lisamazzini.train_app.Model.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationService extends Service {

    //This is the intent to refresh the Notification, that will start the service again
    private PendingIntent pIntentRefresh;
    //This is the intent to stop the service
    private PendingIntent pIntentClose;
    //This will be the intent to refresh automatically the service, one day
    private PendingIntent pIntentAutorefresh;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private AlarmManager am;

    private String numeroTreno;
    private String orarioPartenza;
    private String orarioArrivo;
    private String IDorigine;
    private String IDpartenza;
    private String IDarrivo;
    private DateTime time;


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
//        this.IDpartenza = intent.getStringExtra("idPartenza");
//        this.IDarrivo = intent.getStringExtra("idArrivo");
//        this.orarioArrivo = intent.getStringExtra("oraArrivo");
        this.orarioPartenza = intent.getStringExtra("oraPartenza");

        //Here I set the data for the refresh intent (so the data will be the same)
        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");

        //Here I set the close intent, just adding the action.
        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction("Elimina");

        // Here I have all the information needed for the Notification (see more @NotificationPack)
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
        try {
            this.time = new DateTime(new SimpleDateFormat("HH:mm").parse(this.orarioPartenza));
            this.time = this.time.minus(15);
            DateTime now = new DateTime(Calendar.getInstance().getTime());
            String[] a = orarioPartenza.split(":");
            MutableDateTime n = now.toMutableDateTime();
            n.setDate(Calendar.getInstance().getTimeInMillis());
            n.setTime(Integer.parseInt(a[0]), Integer.parseInt(a[1]), 0, 0);

            Integer timeDifference = Minutes.minutesBetween(now, n).getMinutes();
            Log.d("dio madonna", "" + timeDifference);
            if(timeDifference > 15){
               // è presto
                n.addMinutes(-15);
                Long millis = n.getMillis();
                Log.d("Quanti siete", "" + millis);
                AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, millis, pStart);
            }else {
                // è ora
                spiceManager.execute(new TrainRequest(this.numeroTreno, this.IDorigine), new ResultListener());
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        pIntentAutorefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);


        // TrainRequest request = new TrainRequest(this.number);
        //spiceManager.execute(request, new TrainRequestListener());

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
        public Context getContext() {
            return NotificationService.this;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException){
            Log.d("ERRORE", spiceException.getCause().getMessage());
        }

        @Override
        public void onRequestSuccess(NewTrain train) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification not;

            //If the train is not departed yet the notification will show the data
            if(train.getNonPartito()) {
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setTicker("Treno in arrivo")
                        .setWhen(System.currentTimeMillis())
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Treno " + train.getNumeroTreno())
                                .addLine("Il treno non è ancora partito"))
                        .build();

            //Else, the notification is empty
            }else if(train.getCircolante()){
                not = builder//.setContent(view)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setTicker("AlarmNotification")
                        .setWhen(System.currentTimeMillis())
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Treno" + train.getNumeroTreno())
                                .addLine("Ritardo " + train.getRitardo())
                                .addLine("Ultimo avvistamento" + train.getStazioneUltimoRilevamento())
                                .addLine("Ore " + train.getCompOraUltimoRilevamento()))
                        .build();
            }else{
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Treno " + train.getNumeroTreno())
                                .addLine("Treno arrivato a destinazione"))
                        .setTicker("Treno in arrivo!")
                        .setWhen(System.currentTimeMillis())
                        .build();
            }
            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);
        }
    }
}
