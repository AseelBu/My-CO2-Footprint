package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Tip {
    protected int tipId;
    protected String title;
    protected String content;
    protected Drawable img;

    public Tip(String title, String content,Drawable img) {
        this.title = title;
        this.content = content;
        this.img=img;
    }

    public Tip(int tipId, String title, String content, Drawable img) {
        this.tipId = tipId;
        this.title = title;
        this.content = content;
        this.img = img;
    }

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }
}
