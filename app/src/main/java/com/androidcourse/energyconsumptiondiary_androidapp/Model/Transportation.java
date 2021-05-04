package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Transportation extends CO2Impacter implements Parcelable {

    public String FuelType;


    public Transportation(int impacterID, String name, int  CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);

    }
    public Transportation() {
        super();
    }
    public Transportation(String name, int CO2Amount, Drawable img) {
        super(name, CO2Amount, img);
    }

    public Transportation(String name,String type, int CO2Amount, Drawable img) {
        super(name, CO2Amount, img);
        this.FuelType=type;
    }
    public Transportation(String name,String type, int  CO2Amount) {
        super(name, CO2Amount);

    }

    public Transportation(String name, String content, int CO2Amount, String fuelType) {
        super(name, content, CO2Amount);
        FuelType = fuelType;
    }

    public Transportation(int impacterID, String name, String content, Drawable img) {
        super(impacterID, name, content, img);
    }

   

    public Transportation(String name, String content, Drawable img) {
        super(name, content, img);
    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }


    protected Transportation(Parcel in) {
        super(in);
        FuelType = in.readString();
    }

    public static final Creator<Transportation> CREATOR = new Creator<Transportation>() {
        @Override
        public Transportation createFromParcel(Parcel in) {
            return new Transportation(in);
        }

        @Override
        public Transportation[] newArray(int size) {
            return new Transportation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(FuelType);
    }

}
