package com.example.lisamazzini.train_app.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.lisamazzini.train_app.GUI.StationListActivity;
import com.example.lisamazzini.train_app.Model.Constants;
import com.example.lisamazzini.train_app.Model.Fermate;
import com.example.lisamazzini.train_app.Model.NewTrain;
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

        // ( 1 ) cerco il codice della stazione che mi viene passata
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

            // ( 2 ) Trovato il codice cerco il codice di origine del treno in questione
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

            // ( 3 ) Cerco di caricare il treno, controllando che non sia doppio
            Log.d("Fin qui", "ci siamo? prima richiesta");
            String[] data = s.split(Constants.SEPARATOR);

            //( 3.1 ) Se il treno non è doppio faccio partire la StationListActivity con i dati corretti
            if(data.length == 1){
                String[] values = Utilities.splitString(data[0]);
                Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("trainNumber", values[0]);
                i.putExtra("stationCode", values[1]);
                startActivity(i);
                onDestroy();
            }

            // ( 3.2 ) se il treno è doppio devo cercare la stazione che mi han passato all'interno della lista delle stazioni delle due possibilità
            // qui in particolare faccio partire la richiesta per la prima possibilità
            else{
                firstTrainData = Utilities.splitString(data[0]);
                secondTrainData = Utilities.splitString(data[1]);

                spiceManager.execute(new TrainRequest(firstTrainData[0], firstTrainData[1]), new AltreCoseListener());
            }
        }
    }

    // ( 4 ) Risultato della prima richiesta, cerco qui la stazione, se la trovo passo i dati alla StationList altrimenti sarà nella seconda e mando i relativi dati
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


            Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("trainNumber", newTrain.getNumeroTreno().toString());
            i.putExtra("stationCode", newTrain.getIdOrigine());
            startActivity(i);
            onDestroy();

//            spiceManager.execute(new TrainRequest(secondTrainData[0], secondTrainData[1]), new AncoraAltreCoseListener());
        }
    }


//    private class AncoraAltreCoseListener implements RequestListener<NewTrain>{
//
//        @Override
//        public void onRequestFailure(SpiceException spiceException) {
//
//        }
//
//        @Override
//        public void onRequestSuccess(NewTrain newTrain) {
//            Log.d("Fin qui", "ci siamo? terza richiesta");
//
//            for(Fermate f : newTrain.getFermate()){
//                if(f.getId().equals(depStationCode)){
//                    Intent i = new Intent(DoubleTrainService.this, StationListActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.putExtra("trainNumber", newTrain.getNumeroTreno().toString());
//                    i.putExtra("stationCode", newTrain.getIdOrigine());
//                    startActivity(i);
//                    onDestroy();
//                    return;
//                }
//            }
//        }
//    }


}
