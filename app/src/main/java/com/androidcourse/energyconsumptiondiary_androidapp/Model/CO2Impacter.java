package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Parcelable;
import android.os.Parcel;


import java.util.Objects;

public  class CO2Impacter  implements Parcelable {
    private int impacterID;
    private   String name;
    private String content;
    private int co2Amount=0;
    private Drawable img;

    public CO2Impacter(int impacterID, String name, int CO2Amount, Drawable img) {
        this.impacterID = impacterID;
        this.name = name;
        this.co2Amount = CO2Amount;
        this.img = img;
    }

    public CO2Impacter(String name,String content, int CO2Amount) {
        this.name = name;
        this.co2Amount = CO2Amount;
    }

    public CO2Impacter(String name, int CO2Amount, Drawable img) {
        this.name = name;
        this.co2Amount = CO2Amount;
        this.img = img;
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

    public CO2Impacter(String name, int CO2Amount) {
        this.name = name;
        this.co2Amount = CO2Amount;
    }

    public CO2Impacter() {

    }

    public CO2Impacter(Parcel in) {
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

    public int getCO2Amount() {
        return co2Amount;
    }

    public void setCO2Amount(int CO2Amount) {
        this.co2Amount = CO2Amount;
    }

    public Drawable getImg() {
        return img;
    }
    public static final Creator<CO2Impacter> CREATOR = new Creator<CO2Impacter>() {
        @Override
        public CO2Impacter createFromParcel(Parcel in) {
            return new CO2Impacter(in);
        }

        @Override
        public CO2Impacter[] newArray(int size) {
            return new CO2Impacter[size];
        }
    };
    public void setImg(Drawable img) {
        this.img = img;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(impacterID);
        dest.writeString(this.name);
        dest.writeInt(co2Amount);
        Bitmap bitmap = (Bitmap)((BitmapDrawable) img).getBitmap();
        dest.writeParcelable(bitmap,flags);

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
