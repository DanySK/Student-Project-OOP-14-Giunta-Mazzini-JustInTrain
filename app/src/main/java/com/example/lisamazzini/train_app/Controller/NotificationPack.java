package com.example.lisamazzini.train_app.Controller;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lisamazzini on 04/02/15.
 */
public class NotificationPack implements Parcelable {

    private final String number;
    private final String IDorigine;
    private final String IDpartenza;
    private final String depTime;
    private final String IDarrivo;
    private final String arrTime;

    private NotificationPack(Parcel in) {
        this.number = in.readString();
        this.IDorigine = in.readString();
        this.IDpartenza = in.readString();
        this.depTime = in.readString();
        this.IDarrivo = in.readString();
        this.arrTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<NotificationPack> CREATOR = new Parcelable.Creator<NotificationPack>() {
        public NotificationPack createFromParcel(Parcel in) {
            return new NotificationPack(in);
        }

        public NotificationPack[] newArray(int size) {
            return new NotificationPack[size];
        }
    };

    public String getArrTime() {
        return arrTime;
    }

    public String getNumber() {
        return number;
    }

    public String getIDpartenza() {
        return IDpartenza;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getIDarrivo() {
        return IDarrivo;
    }


    public String getIDorigine() {
        return IDorigine;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(IDpartenza);
        dest.writeString(depTime);
        dest.writeString(IDarrivo);
        dest.writeString(arrTime);
    }
}
