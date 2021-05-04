package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Services extends CO2Impacter implements Parcelable {


    public Services(int impacterID, String name, int CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }
    public Services() {
        super();
    }
    public Services(String name, int CO2Amount, Drawable  img) {
        super(name, CO2Amount, img);
    }
    public Services(String name, int  CO2Amount) {
        super(name, CO2Amount);
    }
    public Services(int id, String name, String content, Drawable img) {
        super(id, name, content, img);
    }
    protected Services(Parcel in) {

    }

    public static final Creator<Services> CREATOR = new Creator<Services>() {
        @Override
        public Services createFromParcel(Parcel in) {
            return new Services(in);
        }

        @Override
        public Services[] newArray(int size) {
            return new Services[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }



}
