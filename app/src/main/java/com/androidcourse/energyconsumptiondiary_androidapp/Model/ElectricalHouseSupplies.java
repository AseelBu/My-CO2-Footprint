package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class ElectricalHouseSupplies extends CO2Impacter {
    public int ElectID;

    public ElectricalHouseSupplies(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }


    public int getElectID() {
        return ElectID;
    }

    public void setElectID(int electID) {
        ElectID = electID;
    }
}
