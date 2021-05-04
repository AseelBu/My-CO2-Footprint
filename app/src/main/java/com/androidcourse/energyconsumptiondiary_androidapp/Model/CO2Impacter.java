package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.Objects;

public abstract class CO2Impacter {
    private int impacterID;
    private   String name;
    private String content;
    private long CO2Amount=0;
    private Drawable img;

    public CO2Impacter(int impacterID, String name, long CO2Amount, Drawable img) {
        this.impacterID = impacterID;
        this.name = name;
        this.CO2Amount = CO2Amount;
        this.img = img;
    }

    public CO2Impacter(String name,String content, long CO2Amount) {
        this.name = name;
        this.CO2Amount = CO2Amount;
    }

    public CO2Impacter(String name, String content, Drawable img) {
        this.name = name;
        this.content = content;
        this.img = img;
    }

    public CO2Impacter(int impacterID, String name, String content, Drawable img) {
        this.impacterID = impacterID;
        this.name = name;
        this.content = content;
        this.img = img;
    }

    public int getImpacterID() {
        return impacterID;
    }

    public void setImpacterID(int impacterID) {
        this.impacterID = impacterID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CO2Impacter)) return false;
        CO2Impacter that = (CO2Impacter) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
