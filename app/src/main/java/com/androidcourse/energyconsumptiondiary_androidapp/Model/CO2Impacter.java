package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.media.Image;

public abstract class CO2Impacter {
    public int impacterID;
    public  String Name;
    public long CO2Amount;
    public Drawable img;

    public CO2Impacter(int impacterID, String name, long CO2Amount, Drawable img) {
        this.impacterID = impacterID;
        Name = name;
        this.CO2Amount = CO2Amount;
        this.img = img;
    }

    public int getImpacterID() {
        return impacterID;
    }

    public void setImpacterID(int impacterID) {
        this.impacterID = impacterID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getCO2Amount() {
        return CO2Amount;
    }

    public void setCO2Amount(long CO2Amount) {
        this.CO2Amount = CO2Amount;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }





}
