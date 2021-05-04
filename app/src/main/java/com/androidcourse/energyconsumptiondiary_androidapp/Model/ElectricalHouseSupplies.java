package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class ElectricalHouseSupplies extends CO2Impacter  implements Parcelable {


    public ElectricalHouseSupplies(int impacterID, String name,int CO2Amount, Drawable  img) {
        super(impacterID, name, CO2Amount, img);
    }
    public ElectricalHouseSupplies() {
        super();
    }
    public ElectricalHouseSupplies(String name,int CO2Amount, Drawable  img) {
        super(name, CO2Amount, img);
    }
    public ElectricalHouseSupplies(String name,int CO2Amount) {
        super(name, CO2Amount);
    }

    protected ElectricalHouseSupplies(Parcel in) {

    }

    public ElectricalHouseSupplies(int impacterID, String name, String content, Drawable img) {
        super(impacterID, name, content, img);
    }
    public static final Creator<ElectricalHouseSupplies> CREATOR = new Creator<ElectricalHouseSupplies>() {
        @Override
        public ElectricalHouseSupplies createFromParcel(Parcel in) {
            return new ElectricalHouseSupplies(in);
        }

        @Override
        public ElectricalHouseSupplies[] newArray(int size) {
            return new ElectricalHouseSupplies[size];
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
