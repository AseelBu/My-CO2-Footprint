package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.drawable.Drawable;

public class Food extends CO2Impacter{
    public int foodID;

    public Food(int impacterID, String name, long CO2Amount, Drawable img) {
        super(impacterID, name, CO2Amount, img);
    }


    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }
}
