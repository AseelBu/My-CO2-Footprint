package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class Transportation extends CO2Impacter {

    public String FuelType;

    public Transportation(int impacterID, String name, long CO2Amount, Drawable img, String fuelType) {
        super(impacterID, name, CO2Amount, img);
        FuelType = fuelType;
    }

    public Transportation(String name, String content, long CO2Amount, String fuelType) {
        super(name, content, CO2Amount);
        FuelType = fuelType;
    }

    public Transportation(int impacterID, String name, String content, Drawable img) {
        super(impacterID, name, content, img);
    }

    public Transportation(String name, String content, long CO2Amount) {
        super(name, content, CO2Amount);
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

}
