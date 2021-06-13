package com.androidcourse.energyconsumptiondiary_androidapp.Model;
import android.graphics.Bitmap;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

public class Transportation extends Co2Impacter {
    String fuelType=null;

    public Transportation() {
    super();
    }

    public Transportation(String impacterID, String name, String question, Units unit, int co2Amount, Bitmap img, String fuelType) {
        super(impacterID, name, question,unit, co2Amount, img);
        this.fuelType = fuelType;
    }

    public Transportation(String name, String question,Units unit, int co2Amount, Bitmap img, String fuelType) {
        super(name, question,unit, co2Amount, img);
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
