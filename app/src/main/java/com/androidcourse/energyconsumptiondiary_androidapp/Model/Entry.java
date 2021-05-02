package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Entry {
    int userId= -1;
    Transportation transport =null;
    Food food = null;
    ElectricalHouseSupplies electric =null;
    Services service=null;
    Date date = Calendar.getInstance().getTime();

    public Entry() {

    }


    public Entry(int userId) {
        this.userId = userId;
    }

    public Transportation getTransport() {
        return transport;
    }

    public void setTransport(Transportation transport) {
        this.transport = transport;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public ElectricalHouseSupplies getElectric() {
        return electric;
    }

    public void setElectric(ElectricalHouseSupplies electric) {
        this.electric = electric;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

}
