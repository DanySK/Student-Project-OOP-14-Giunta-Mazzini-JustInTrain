package com.example.lisamazzini.train_app.Notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.NotificationPack;
import com.example.lisamazzini.train_app.Controller.TrainRequest;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.R;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Calendar;

public class NotificationService extends Service {

    private NotificationPack information;
    //This is the intent to refresh the Notification, that will start the service again
    private PendingIntent pIntentRefresh;
    //This is the intent to stop the service
    private PendingIntent pIntentClose;
    //This will be the intent to refresh automatically the service, one day
    private PendingIntent pIntentAutorefresh;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private AlarmManager am;


    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        // Here I have all the information needed for the Notification (see more @NotificationPack)
        information = intent.getExtras().getParcelable("information");

        //Here I set the data for the refresh intent (so the data will be the same)
        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");
        intentRefresh.putExtra("information" , information);

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
        spiceManager.execute(new TrainRequest(information.getNumber(), information.getIDorigine()), new ResultListener());
        // TrainRequest request = new TrainRequest(this.number);
        //spiceManager.execute(request, new TrainRequestListener());

        spiceManager.start(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class ResultListener implements RequestListener<NewTrain> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(NotificationService.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(NewTrain train) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Notification not;

            //If the train is not departed yet the notification will show the data
            if(!train.getNonPartito()) {
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
            //Else, the notification is empty
            }else{
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                                        .setOngoing(true)
                                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                                        .setStyle(new NotificationCompat.InboxStyle()
                                                .setBigContentTitle("Treno " + train.getNumeroTreno())
                                                .addLine("Arrivato o non ancora partito"))
                                        .build();

            }

            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);

        }
    }

}
