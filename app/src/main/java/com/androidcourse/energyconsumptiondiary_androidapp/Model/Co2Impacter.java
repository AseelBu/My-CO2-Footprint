package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;

import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

import java.util.Objects;

public  class  Co2Impacter implements Comparable<Co2Impacter>{
    private int impacterID=-1;
    private   String name;
    private String question;
    private Units unit=Units.UNIT;// type of unit that matches the question
    private int co2Amount=0;

    private Bitmap img=null;

    public void setImpacterID(int impacterID) {
        this.impacterID = impacterID;
    }

    public Co2Impacter() {
    }

    public Co2Impacter(int impacterID, String name, String question,Units unit, int co2Amount, Bitmap img) {
        this.impacterID = impacterID;
        this.name = name;
        this.question = question;
        this.unit=unit;
        this.co2Amount = co2Amount;
        this.img = img;
    }



    public Co2Impacter(String name, String question,Units unit, int co2Amount, Bitmap img) {
        this.name = name;
        this.question = question;
        this.unit=unit;
        this.co2Amount = co2Amount;
        this.img = img;
    }

    public int getImpacterID() {
        return impacterID;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }





    public int getCo2Amount() {
        return co2Amount;
    }

    public void setCo2Amount(int co2Amount) {
        this.co2Amount = co2Amount;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Co2Impacter)) return false;
        Co2Impacter that = (Co2Impacter) o;
        return getImpacterID() == that.getImpacterID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImpacterID());
    }

    @Override
    public int compareTo(Co2Impacter o) {
        return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
    }
}
