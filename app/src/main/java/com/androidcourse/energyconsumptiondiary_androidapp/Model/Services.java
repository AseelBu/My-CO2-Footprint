package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class Services extends CO2Impacter {


    public Services(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }

    public Services(int id, String name, String content, Drawable img) {
        super(id,name,content,img);
    }


}
