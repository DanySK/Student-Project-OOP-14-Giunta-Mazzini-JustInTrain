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
import android.widget.Toast;

import com.example.lisamazzini.train_app.Controller.TrainDataRequest;
import com.example.lisamazzini.train_app.R;
import com.example.lisamazzini.train_app.Model.Train;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Calendar;

public class NotificationService extends Service {

    private String number;
    private String time;
    private Train currentStateTrain;
//    private RemoteViews view;
    private PendingIntent pIntentRefresh;
    private PendingIntent pIntentClose;
    private PendingIntent pIntentAutorefresh;
    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private AlarmManager am;
    private Calendar calendar = Calendar.getInstance();


    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId){

        Intent intentRefresh = new Intent(this, ButtonListener.class);
        intentRefresh.setAction("Aggiorna");
        this.number = intent.getStringExtra("number");
        this.time = intent.getStringExtra("time");
        Integer hour = Integer.parseInt(this.time.split(":")[0]);
        Integer minute = Integer.parseInt(this.time.split(":")[1]) - 15;
        am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        intentRefresh.putExtra("number", this.number);
        intentRefresh.putExtra("time", this.time);

        Intent intentClose = new Intent(this, ButtonListener.class);
        intentClose.setAction("Elimina");

        pIntentRefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentClose = PendingIntent.getBroadcast(this, 1, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntentAutorefresh = PendingIntent.getBroadcast(this, 1, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntentAutorefresh);

        spiceManager = new SpiceManager(UncachedSpiceService.class);
        TrainDataRequest request = new TrainDataRequest(this.number);
        //spiceManager.execute(request, new TrainRequestListener());



        //  view = new RemoteViews(getPackageName(), R.layout.layout_notification);


        Log.d("OOOOOOOOOOOOOOOOOOOO", "On start command" + intent.getStringExtra("number"));
        spiceManager.start(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class TrainRequestListener implements RequestListener<Train> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(NotificationService.this,
                    "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onRequestSuccess(Train train) {
            currentStateTrain = train;

            //view.setTextViewText(R.id.ntNumber, currentStateTrain.getNumber());
            //view.setTextViewText(R.id.ntDelay, Integer.toString(currentStateTrain.getDelay()));
           //view.setTextViewText(R.id.ntStation, currentStateTrain.getLastSeenStation());
            //view.setTextViewText(R.id.ntTime, currentStateTrain.getLastSeenTime());
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
            Log.d("OOOOOOOOOOOOOOOOOOOO", "On post execute");
            Notification not;

            if(currentStateTrain.isMoving()) {
                not = builder//.setContent(view)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setOngoing(true)
                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setBigContentTitle("Treno" + currentStateTrain.getNumber())
                                .addLine("Ritardo " + currentStateTrain.getDelay())
                                .addLine("Ultimo avvistamento" + currentStateTrain.getLastSeenStation())
                                .addLine("Ore " + currentStateTrain.getLastSeenTime()))
                        .build();
            }else{
                not = builder.setSmallIcon(R.drawable.ic_launcher)
                                        .setOngoing(true)
                                        .addAction(R.drawable.ic_launcher, "Aggiorna", pIntentRefresh)
                                        .addAction(R.drawable.ic_launcher, "Elimina", pIntentClose)
                                        .setStyle(new NotificationCompat.InboxStyle()
                                                .setBigContentTitle("Treno " + currentStateTrain.getNumber())
                                                .addLine("Arrivato o non ancora partito"))
                                        .build();

            }

            not.priority = Notification.PRIORITY_MAX;
            startForeground(1, not);

        }
    }
}
