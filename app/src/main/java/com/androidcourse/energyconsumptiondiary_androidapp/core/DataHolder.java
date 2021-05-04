package com.androidcourse.energyconsumptiondiary_androidapp.core;

import android.graphics.drawable.Drawable;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.CO2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DataHolder {


    private ArrayList<Tip> tipsList = new ArrayList<>();
     private ArrayList<Transportation> transportation = new ArrayList<>();
    private ArrayList<User> usersList= new ArrayList();
    private HashMap<ImpactType, ArrayList<? extends CO2Impacter>> impacterMap=new HashMap<>();



    private static DataHolder instance = null;


    private DataHolder(){
        impacterMap.put(ImpactType.TRANSPORTATIOIN,new ArrayList<Transportation>());
        impacterMap.put(ImpactType.FOOD,new ArrayList<Food>());
        impacterMap.put(ImpactType.ELECTRICAL,new ArrayList<ElectricalHouseSupplies>());
        impacterMap.put(ImpactType.SERVICES,new ArrayList<Services>());
    }


    public static DataHolder getInstance(){
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public ArrayList<Tip> getTips(){
        return this.tipsList;
    }
    public ArrayList<User> getUsers(){
        return this.usersList;
    }

    public HashMap<ImpactType, ArrayList<? extends CO2Impacter>> getImpacterMap() {
        return impacterMap;
    }

    public ArrayList<Transportation> getTransportation(){
        ArrayList<Transportation>list =new ArrayList<>();
        for(CO2Impacter item: impacterMap.get(ImpactType.TRANSPORTATIOIN)){
            list.add((Transportation)item);
        }

        return list;
    }
    public void setTransportation(ArrayList<Transportation> t) {
        transportation = t;
    }

    public ArrayList<Food> getFood(){
        ArrayList<Food>list =new ArrayList<>();
        for(CO2Impacter item: impacterMap.get(ImpactType.FOOD)){
            list.add((Food) item);
        }

        return list;
    }

    public ArrayList<ElectricalHouseSupplies> getElectricals(){
        ArrayList<ElectricalHouseSupplies>list =new ArrayList<>();
        for(CO2Impacter item: impacterMap.get(ImpactType.ELECTRICAL)){
            list.add((ElectricalHouseSupplies)item);
        }

        return list;
    }

    public ArrayList<Services> getServices(){
        ArrayList<Services>list =new ArrayList<>();
        for(CO2Impacter item: impacterMap.get(ImpactType.SERVICES)){
            list.add((Services) item);
        }

        return list;
    }


    public void addTip(String title, String content, Drawable img){
        this.tipsList.add(new Tip(title,content, img));
    }

    public void addUser(int userId,String firstName,String lastName,int points, Drawable img){
        this.usersList.add(new User(userId,firstName+lastName+"@g.com",firstName+lastName+"",firstName,lastName,points,img));
    }

    public void addUserDetails(String firstName2,String lastName2,String email2,String password2){

        this.usersList.add(new User(firstName2,lastName2,email2,password2));

    }
    public void addTransportaion(String name,String fuel,int amount){

        this.getTransportation().add(new Transportation(name,fuel,amount));

    }
    public void addAdminDetails(int id,String email2,String password2,String firstName,String lastName,Drawable img){

        this.usersList.add(new User(id,email2,password2,firstName,lastName,100,img));

    }

    public List<User> getAllLeaderboardUsers() {

        List<User> users= new ArrayList<User>(this.usersList);
        Collections.sort(users);
        return users;
    }

    public List<User> getLeaderboardUsers(int size) {

        List<User>leadUsers =new ArrayList<User>();
        List<User> users= getAllLeaderboardUsers();

        for(int i=0;i<size;i++){
            leadUsers.add(users.get(i));
        }
        return leadUsers;
    }

    public String getUserRank(int id){
        List<User> users= getAllLeaderboardUsers();
        int position=0;
        for(User u:users){
            if(u.getUserId()==id){
               position=users.indexOf(u);
            }
        }
        return String.valueOf(position+1)+"/"+String.valueOf(users.size());
    }

    public User getUserById(int id){
        for(User user:usersList){
            if(user.getUserId()==id){
                return user;
            }
        }
        return null;
    }
}
