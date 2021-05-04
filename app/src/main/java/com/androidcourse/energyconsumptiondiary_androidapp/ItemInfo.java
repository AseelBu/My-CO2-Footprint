package com.androidcourse.energyconsumptiondiary_androidapp;

public class ItemInfo {
    private String name;
    private int imgId;
    //itemInfo constructer
    public ItemInfo(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;

    }
    public ItemInfo() {
    }
      //get name
    public String getName()
    {
        return name;
    }
      //get image id
    public int getImgId()
    {
        return imgId;
    }
    //set name
    public void setName(String name)
    {
        this.name = name;
    }
    //set image id
    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }
}
