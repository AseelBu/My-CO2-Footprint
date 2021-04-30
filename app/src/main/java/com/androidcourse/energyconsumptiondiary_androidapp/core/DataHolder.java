package com.androidcourse.energyconsumptiondiary_androidapp.core;

import android.graphics.drawable.Drawable;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataHolder {

    private ArrayList<Tip> tipsList = new ArrayList<>();
    private ArrayList<User> usersList= new ArrayList();

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

    public void addUser(String firstName,String lastName,int points, Drawable img){
        this.usersList.add(new User(firstName,lastName,points,img));
    }

    public List<User> getLeaderboardUsers() {
        int size=10;
        List<User>leadUsers =new ArrayList<User>();
        List<User> users= new ArrayList<User>(this.usersList);
        Collections.sort(users);
        for(int i=0;i<size;i++){
            leadUsers.add(users.get(i));
        }
        return leadUsers;
    }
}
