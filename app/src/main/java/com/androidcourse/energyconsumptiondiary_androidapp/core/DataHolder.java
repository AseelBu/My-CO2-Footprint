package com.androidcourse.energyconsumptiondiary_androidapp.core;

import android.graphics.drawable.Drawable;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;

import java.util.ArrayList;

public class DataHolder {

    private ArrayList<Tip> tipsList = new ArrayList<>();

    private static DataHolder instance = null;


    private DataHolder(){}


    public static DataHolder getInstance(){
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public ArrayList<Tip> getTips(){
        return this.tipsList;
    }

    public void addTip(String title,String content,Drawable img){
        this.tipsList.add(new Tip(title,content, img));
    }
}
