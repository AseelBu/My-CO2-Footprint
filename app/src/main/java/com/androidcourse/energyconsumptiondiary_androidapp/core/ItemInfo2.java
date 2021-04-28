package com.androidcourse.energyconsumptiondiary_androidapp.core;

public class ItemInfo2 {
    private String name;
    private int imgId;

    public ItemInfo2(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }
    public ItemInfo2() {
    }

    public String getName() {
        return name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
