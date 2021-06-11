package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Result {

    private int id;
    private int userId;
    private Date date;
    private double transportationResult=0;
    private double foodResult=0;
    private double servicesResult=0;
    private double electricsResult=0;

    MyCo2FootprintManager dbManager=MyCo2FootprintManager.getInstance();

    public Result() {
    }

    public Result(int userId,Date date) {
        this.userId=userId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTransportationResult() {
        return transportationResult;
    }

    public void setTransportationResult(double transportationResult) {
        this.transportationResult = transportationResult;
    }

    public double getFoodResult() {
        return foodResult;
    }

    public void setFoodResult(double foodResult) {
        this.foodResult = foodResult;
    }

    public double getServicesResult() {
        return servicesResult;
    }

    public void setServicesResult(double servicesResult) {
        this.servicesResult = servicesResult;
    }

    public double getElectricsResult() {
        return electricsResult;
    }

    public void setElectricsResult(double electricsResult) {
        this.electricsResult = electricsResult;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result result = (Result) o;
        return getId() == result.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void calculateAndSetResult(ArrayList<TypeEntry> data){
        for(TypeEntry entry:data){
           int entryValue=entry.getValue();
            String impacterId = entry.getId();
           Co2Impacter impacter=dbManager.getImpacterById(impacterId);
           int impacterCo2=impacter.getCo2Amount();

           int resultForEntry=impacterCo2*entryValue;
           if(impacter instanceof Transportation){
               transportationResult+=resultForEntry;
           }
           else if (impacter instanceof Food){
               foodResult+=resultForEntry;
           }
           else if (impacter instanceof ElectricalHouseSupplies){
                electricsResult+=resultForEntry;
           }
           else if (impacter instanceof Service){
                servicesResult+=resultForEntry;
           }

        }

    }

    //return the sum of all results
    public double getTotal(){
        return transportationResult+foodResult+electricsResult+servicesResult;
    }


}
