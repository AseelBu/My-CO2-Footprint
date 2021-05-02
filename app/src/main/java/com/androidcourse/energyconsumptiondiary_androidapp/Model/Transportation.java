package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class Transportation extends CO2Impacter {

    public String FuelType;

    public Transportation(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }




    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

}
