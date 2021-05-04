package com.androidcourse.energyconsumptiondiary_androidapp.core;

public class ItemInfo2 {
    private String name;
    private int imgId;
     //ItemInfo2 Constructor
    public ItemInfo2(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }
    //ItemInfo2 Constructor
    public ItemInfo2() {
    }
    //get name method
    public String getName()
    {
        return name;
    }
    //get Image id method
    public int getImgId()
    {
        return imgId;
    }
      //set name method
    public void setName(String name)
    {
        this.name = name;
    }
    //set image id method
    public void setImgId(int imgId)
    {
        this.imgId = imgId;
    }
}
