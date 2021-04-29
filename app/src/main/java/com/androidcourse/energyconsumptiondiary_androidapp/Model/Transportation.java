package com.androidcourse.energyconsumptiondiary_androidapp.Model;

public class Transportation {
    public int transID;
    public String FuelType;

    public Transportation(int transID, String fuelType) {
        this.transID = transID;
        FuelType = fuelType;
    }
    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

}
