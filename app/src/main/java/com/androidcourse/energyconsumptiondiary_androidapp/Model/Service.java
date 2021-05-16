package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;

import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

public class Service extends Co2Impacter {
    public Service() {
    }

    public Service(int impacterID, String name, String question, Units unit, int co2Amount, Bitmap img) {
        super(impacterID, name, question, unit, co2Amount, img);
    }

    public Service(String name, String question, Units unit, int co2Amount, Bitmap img) {
        super(name, question, unit, co2Amount, img);
    }
}
