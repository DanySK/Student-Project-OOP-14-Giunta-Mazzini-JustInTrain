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


    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        // Here I have all the information needed for the Notification (see more @NotificationPack)
        this.numeroTreno = intent.getStringExtra("number");
        this.IDorigine = intent.getStringExtra("idOrigine");
        this.IDpartenza = intent.getStringExtra("idPartenza");
        this.IDarrivo = intent.getStringExtra("idArrivo");
        this.orarioArrivo = intent.getStringExtra("oraArrivo");
        this.orarioPartenza = intent.getStringExtra("oraPartenza");




        //Here I set the data for the refresh intent (so the data will be the same)
        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");
        intentRefresh = intent;

        //Here I set the close intent, just adding the action.
        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction("Elimina");

        pIntentRefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentClose = PendingIntent.getBroadcast(this, 1, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentAutorefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);

//        Integer hour = Integer.parseInt(this.time.split(":")[0]);
//        Integer minute = Integer.parseInt(this.time.split(":")[1]) - 15;
//        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);




//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, 0);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntentAutorefresh);

        spiceManager = new SpiceManager(UncachedSpiceService.class);

        //
        spiceManager.execute(new TrainRequest(this.numeroTreno, this.IDorigine), new ResultListener());
        // TrainRequest request = new TrainRequest(this.number);
        //spiceManager.execute(request, new TrainRequestListener());

        spiceManager.start(this);

        // If the OS stops the service after running out of memory, the service will be started again with da same intent
        return START_REDELIVER_INTENT;
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
                        .build();
            }
            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);
        }
    }
}
