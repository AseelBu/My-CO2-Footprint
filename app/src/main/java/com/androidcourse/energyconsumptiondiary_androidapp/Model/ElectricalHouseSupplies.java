package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class ElectricalHouseSupplies extends CO2Impacter {


    public ElectricalHouseSupplies(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }

    public ElectricalHouseSupplies(int impacterID, String name, String content, Drawable img) {
        super(impacterID, name, content, img);
    }
}
