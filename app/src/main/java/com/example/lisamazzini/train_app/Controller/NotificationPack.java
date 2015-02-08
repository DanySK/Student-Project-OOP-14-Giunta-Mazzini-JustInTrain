//package com.example.lisamazzini.train_app.Controller;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.example.lisamazzini.train_app.Model.Tragitto.PlainSolution;
//
///**
// *
// * This is a "pack" of information that the NotificationService needs to update the Notification;
// * Instead of a large number of intents, we created this Parcelable that can be passed as a single
// * object through a intent.
// *
// * Created by lisamazzini on 04/02/15.
// */
//
//
//public class NotificationPack implements Parcelable {
//
//    private final PlainSolution plainSolution;
//
//
//    public NotificationPack(PlainSolution plainSolution) {
//        this.plainSolution = plainSolution;
//    }
//
//    public static Creator CREATOR = new Creator() {
//        @Override
//        public Object createFromParcel(Parcel source) {
//            return null;
//        }
//
//        @Override
//        public Object[] newArray(int size) {
//            return new Object[0];
//        }
//    };
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        return;
//    }
//
//    public String getArrTime() {
//        return this.plainSolution.getOrarioArrivo();
//    }
//
//    public String getNumber() {
//        return this.plainSolution.getNumeroTreno();
//    }
//
//    public String getIDpartenza() {
//        return this.plainSolution.getIDpartenza();
//    }
//
//    public String getDepTime() {
//        return this.plainSolution.getOrarioPartenza();
//    }
//
//    public String getIDarrivo() {
//        return this.plainSolution.getIDarrivo();
//    }
//
//
//    public String getIDorigine() {
//        return this.plainSolution.getIDorigine();
//    }
//
//    public String getUltimoRilevamento(){
//        return this.getUltimoRilevamento();
//    }
//
//    public String getOraUltimoRilevamento(){
//        return this.getOraUltimoRilevamento();
//    }
//
//
//
//}
