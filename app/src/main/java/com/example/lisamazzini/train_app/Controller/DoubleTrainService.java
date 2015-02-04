package com.example.lisamazzini.train_app.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Parser.Fermate;
import com.example.lisamazzini.train_app.Parser.NewTrain;
import com.example.lisamazzini.train_app.Utilities;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.UncachedSpiceService;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class DoubleTrainService extends Service {

    private SpiceManager spiceManager = new SpiceManager(UncachedSpiceService.class);
    private String trainNumber;
    private String depStation;
    private String depStationCode;

    private String firstTrainData[];
    private String secondTrainData[];


    public int onStartCommand (Intent intent, int flags, int startId){
        Log.d("Service", "started");
        trainNumber = intent.getStringExtra("trainNumber");
        depStation = intent.getStringExtra("depStation");

        spiceManager.execute(new JourneyDataRequest(this.depStation), new DepartureDataRequestListenter());

        spiceManager.start(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy(){
        stopForeground(true);
        spiceManager.shouldStop();
        super.onDestroy();
    }

    private class DepartureDataRequestListenter implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
        }

        @Override
        public void onRequestSuccess(String s) {
            depStationCode = s.split("\\|")[1];
            spiceManager.execute(new TrainDataRequest(trainNumber), new CoseListener());

        }
    }

    private class CoseListener implements RequestListener<String>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(String s) {
            Log.d("Fin qui", "ci siamo? prima richiesta");
            String[] data = s.split(Constants.SEPARATOR);
            if(data.length == 1){
                String[] values = Utilities.splitString(data[0]);
                Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("trainNumber", values[0]);
                i.putExtra("stationCode", values[1]);
                startActivity(i);
                onDestroy();
            }
            else{
                firstTrainData = Utilities.splitString(data[0]);
                secondTrainData = Utilities.splitString(data[1]);

                spiceManager.execute(new TrainRequest(firstTrainData[0], firstTrainData[1]), new AltreCoseListener());
            }
        }
    }

    private class AltreCoseListener implements RequestListener<NewTrain>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(NewTrain newTrain) {
            Log.d("Fin qui", "ci siamo? seconda richiesta");

            for(Fermate f : newTrain.getFermate()){
                if(f.getId().equals(depStationCode)){
                    Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("trainNumber", newTrain.getNumeroTreno().toString());
                    i.putExtra("stationCode", newTrain.getIdOrigine());
                    startActivity(i);
                    onDestroy();
                    return;
                }
            }
            spiceManager.execute(new TrainRequest(secondTrainData[0], secondTrainData[1]), new AncoraAltreCoseListener());
        }
    }

    private class AncoraAltreCoseListener implements RequestListener<NewTrain>{

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(NewTrain newTrain) {
            Log.d("Fin qui", "ci siamo? terza richiesta");

            for(Fermate f : newTrain.getFermate()){
                if(f.getId().equals(depStationCode)){
                    Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("trainNumber", newTrain.getNumeroTreno().toString());
                    i.putExtra("stationCode", newTrain.getIdOrigine());
                    startActivity(i);
                    onDestroy();
                    return;
                }
            }
        }
    }


}
