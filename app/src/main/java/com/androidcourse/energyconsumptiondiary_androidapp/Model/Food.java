package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class Food extends CO2Impacter{


    public Food(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }

    public Food(int impacterID, String name, String content, Drawable img) {
        super(impacterID, name, content, img);
    }


}
