package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MyPieData {
    private float value= 0;
    private String label= null;
    private Drawable icon =null;

    public MyPieData(float value, String label) {
        this.value = value;
        this.label = label;
//        this.icon = icon;
    }

    public float getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }


}
