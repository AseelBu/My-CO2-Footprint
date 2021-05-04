package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Food extends CO2Impacter implements Parcelable{
    public int foodID;

    public Food(int impacterID, String name, int  CO2Amount,int img) {
        super(impacterID, name, CO2Amount, img);
    }
    public Food(String name, int  CO2Amount,int img) {
        super(name, CO2Amount, img);
    }

    public Food(String name, int  CO2Amount) {
        super(name, CO2Amount);
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }






    protected Food(Parcel in) {
        super();
    }
    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
