package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.media.Image;

public abstract class CO2Impacter {
    public int impacterID;
    public  String Name;
    public int CO2Amount;
    public int img;


    public CO2Impacter(int impacterID, String name, int CO2Amount, int img) {
        this.impacterID = impacterID;
        Name = name;
        this.CO2Amount = CO2Amount;
        this.img = img;
    }
    public CO2Impacter(String name, int CO2Amount, int img) {
        Name = name;
        this.CO2Amount = CO2Amount;
        this.img = img;
    }


    public CO2Impacter(String name, int CO2Amount) {
        Name = name;
        this.CO2Amount = CO2Amount;
    }

    public CO2Impacter() {

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

    public int getCO2Amount() {
        return CO2Amount;
    }

    public void setCO2Amount(int CO2Amount) {
        this.CO2Amount = CO2Amount;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }





}
