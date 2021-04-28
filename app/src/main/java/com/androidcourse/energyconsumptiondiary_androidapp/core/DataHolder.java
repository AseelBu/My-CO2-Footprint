package com.androidcourse.energyconsumptiondiary_androidapp.core;

public class DataHolder {
    private static DataHolder instance = null;

    //make the constructor private so that this class cannot be
    //instantiated
    private DataHolder(){}

    //Get the only object available
    public static DataHolder getInstance(){
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }
}
