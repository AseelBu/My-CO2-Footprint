package com.androidcourse.energyconsumptiondiary_androidapp.Model;

public class UserCO2Impact {
     User user;
     Co2Impacter impacter;
    public long amount;

    public UserCO2Impact(User user) {
        this.user = user;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }




}
