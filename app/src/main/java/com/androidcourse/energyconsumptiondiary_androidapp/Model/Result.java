package com.androidcourse.energyconsumptiondiary_androidapp.Model;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Result {

    private String id;
    private String userId;
    private Date date;
    private double transportationResult=0;
    private double foodResult=0;
    private double servicesResult=0;
    private double electricsResult=0;

    MyCo2FootprintManager dbManager=MyCo2FootprintManager.getInstance();

    public Result() {
    }

    public Result(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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
        return getId().equals(result.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void calculateAndSetResult(ArrayList<TypeEntry> data){
        transportationResult=0;
        foodResult=0;
        electricsResult=0;
        servicesResult=0;
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

    public int calculateResultPoints(){
        int maxCo2=3000;
        double resultTotal= getTotal();
        return (int)(maxCo2-resultTotal);
    }

}
