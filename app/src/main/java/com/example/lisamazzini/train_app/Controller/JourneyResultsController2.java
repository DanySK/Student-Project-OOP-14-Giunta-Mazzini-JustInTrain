package com.example.lisamazzini.train_app.Controller;

import android.util.Log;

import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
import com.example.lisamazzini.train_app.Model.Tragitto.Soluzioni;
import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
import com.example.lisamazzini.train_app.Model.Tragitto.Vehicle;
import com.example.lisamazzini.train_app.Network.JourneyRestClient;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class JourneyResultsController2 {

    private final String departure;
    private final String arrival;
    private final String date;
    private final String time;
    private List<PlainSolution> plainSolutions = new LinkedList<>();
    Tragitto tragitto;


    public JourneyResultsController2(String departure, String arrival, String date, String time) {
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
    }

    public void makeRequests() {
//        final CountDownLatch latch = new CountDownLatch(1);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                tragitto = JourneyRestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00");
//                latch.countDown();
//            }
//        });
//        thread.start();
//        try {
//            latch.await();
//            buildPlainSolutions(tragitto);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



//        JourneyRestClient.get().getJourneys("7104", "5066", "2015-02-01T00:00:00", new Callback<Tragitto>() {
//            @Override
//            public void success(Tragitto journeys, Response response) {
//                buildPlainSolutions(journeys);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("cazzi", "" + error.getMessage());
//            }
//        });
    }

    public void buildPlainSolutions(Tragitto tragitto) {
        for (Soluzioni sol : tragitto.getSoluzioni()) {
            for (Vehicle vehicle : sol.getVehicles()) {
                plainSolutions.add(new PlainSolution(vehicle.getCategoriaDescrizione(), vehicle.getNumeroTreno(),
                        vehicle.getOrigine(), vehicle.getOraPartenza(), vehicle.getDestinazione(), vehicle.getOraArrivo(),
                        sol.getDurata()));
            }
        }
        Log.d("cazzi", "build " + plainSolutions.size());
    }

    public List<PlainSolution> getPlainSolutions() {
        Log.d("cazzi", " get " + plainSolutions.size());
        return this.plainSolutions;
    }

}