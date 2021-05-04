package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public  class CO2Impacter  implements Parcelable {
    public int impacterID;
    public  String Name;
    public int CO2Amount;
    public Drawable img;


    public CO2Impacter(int impacterID, String name, int CO2Amount, Drawable img) {
        this.impacterID = impacterID;
        Name = name;
        this.CO2Amount = CO2Amount;
        this.img = img;
    }
    public CO2Impacter(String name, int CO2Amount, Drawable img) {
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

    public CO2Impacter(Parcel in) {
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
        dest.writeString(Name);
        dest.writeInt(CO2Amount);
        Bitmap bitmap = (Bitmap)((BitmapDrawable) img).getBitmap();
        dest.writeParcelable(bitmap,Parcelable.PARCELABLE_WRITE_RETURN_VALUE);

    }






}
