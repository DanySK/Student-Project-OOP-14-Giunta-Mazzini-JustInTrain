//package com.example.lisamazzini.train_app.Controller;
//
//import android.util.Log;
//
//import com.example.lisamazzini.train_app.Model.Tragitto.Tragitto;
//import com.example.lisamazzini.train_app.Network.JourneyAPI;
//import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
//
//public class JourneyRequest extends RetrofitSpiceRequest<Tragitto, JourneyAPI> {
//
//    private String departureID;
//    private String arrivalID;
//    private String dateTime;
//    private int timeSlot;
//
//    public JourneyRequest(String departureID, String arrivalID, String dateTime, int timeSlot) {
//        super(Tragitto.class, JourneyAPI.class);
//        this.departureID = departureID;
//        this.arrivalID = arrivalID;
//        this.dateTime = dateTime;
//        this.timeSlot = timeSlot;
//    }
//
//    @Override
//    public Tragitto loadDataFromNetwork() throws Exception {
//        Log.d("cazzi", "loaddata");
//        Tragitto tragitto = getService().getJourneys(departureID, arrivalID, dateTime);
//        tragitto.setTimeSlot(this.timeSlot);
//        return tragitto;
//    }
//}
