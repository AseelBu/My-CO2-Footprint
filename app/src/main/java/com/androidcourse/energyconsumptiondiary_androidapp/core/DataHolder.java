package com.androidcourse.energyconsumptiondiary_androidapp.core;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class DataHolder {


    private ArrayList<Tip> tipsList = new ArrayList<>();
    private ArrayList<User> usersList= new ArrayList();
    private TreeSet<Transportation> transList = new TreeSet<>();
    private TreeSet<Food> foodList = new TreeSet<>();
    private TreeSet<ElectricalHouseSupplies> electricsList= new TreeSet<>();
    private TreeSet<Service> serviceList= new TreeSet<>();
    private static DataHolder instance = null;

    private DataHolder(){

    }

    public static DataHolder getInstance(){
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public ArrayList<Tip> getTips()
    {
        return this.tipsList;
    }

    public ArrayList<User> getUsers()
    {
        return this.usersList;
    }

    public ArrayList<Transportation> getTransportation()
    {
        return  new ArrayList<Transportation>(this.transList);
    }

    public ArrayList<Food> getFood()
    {
        return  new ArrayList<Food>(this.foodList);
    }

    public ArrayList<ElectricalHouseSupplies> getElectrics()
    {
        return  new ArrayList<ElectricalHouseSupplies>(this.electricsList);
    }

    public ArrayList<Service> getServices()
    {
        return  new ArrayList<Service>(this.serviceList);
    }

    // ---------------Tips---------
    public void addTip(String title, String content, Bitmap img){
        this.tipsList.add(new Tip(title,content, img));
    }
    // ---------------Users---------
    //with points + image
//    public void addUser(int userId,String firstName,String lastName,String email, String password,int points, Drawable img){
//        this.usersList.add(new User(userId,email,password,firstName,lastName,points,img));
//    }
//    //with points+no image
//    public void addUser(int userId,String firstName,String lastName,String email, String password,int points){
//        this.usersList.add(new User(userId,email,password,firstName,lastName,points));
//    }
//
//    //without points+image
//    public void addUser(int userId,String firstName,String lastName,String email, String password, Drawable img){
//        this.usersList.add(new User(userId,email,password,firstName,lastName,img));
//    }
//    //without points+ no image
//    public void addUser(int userId,String firstName,String lastName,String email, String password){
//        this.usersList.add(new User(userId,email,password,firstName,lastName));
//    }

//    public void addAdmin(int id, String email, String password, String firstName, String lastName, Drawable img){
//        User user= (new User(id,email,password,firstName,lastName,100,img));
//        user.setAdmin(true);
//        this.usersList.add(user);
//
//    }

    /**
     * returns user by id
     * @param id of the wanted user
     * @return User if found,else returns null
     */
//    public User getUserById(int id){
//        for(User user:usersList){
//            if(user.getUserId()==id){
//                return user;
//            }
//        }
//        return null;
//    }

    /**
     * get registered admins
     * @return ArrayList<User> list of admins if there is any,else returns null
     */
    public ArrayList<User> getAdmins(){
        ArrayList<User> adminsList=new ArrayList<>();
        for(User user:usersList){
            if(user.isAdmin()){

                adminsList.add(user);
            }
        }
        if(adminsList.isEmpty()){
            return null;
        }
        return adminsList;
    }

    // ---------------Leaderboard---------

    /**
     * Gets all the users sorted by points DESC
     * @return  List<User> -all users sorted by amount of points DESC
     */
    public List<User> getAllLeaderboardUsers() {

        List<User> users= new ArrayList<User>(this.usersList);
        Collections.sort(users);    //sorts users by number of points DESC
        return users;
    }

    /**
     * gets the first k users in leaderboard ,sorted by points DESC
     * @param k -value of the k as in the first k users in leaderboard
     * @return List<User> -k long list of leaderboard users;
     */
    public List<User> getLeaderboardUsers(int k) {

        List<User>leadUsers =new ArrayList<User>();
        List<User> users= getAllLeaderboardUsers();

        for(int i=0;i<k;i++){
            leadUsers.add(users.get(i));
        }
        return leadUsers;
    }

//    public String getUserRank(int id){
//        List<User> users= getAllLeaderboardUsers();
//        int position=0;
//        for(User u:users){
//            if(u.getUserId()==id){
//               position=users.indexOf(u);
//            }
//        }
//        return String.valueOf(position+1)+"/"+String.valueOf(users.size());
//    }

    public void addTransportation(Transportation transportation) {
        transList.add(transportation);
    }
    public void addTransportation(String id, String name, String question, Units unit, int co2Amount, Bitmap img, String fuelType) {
        transList.add(new Transportation(id,name,question,unit,co2Amount ,img,fuelType));
    }
    public void addTransportation( String name, String question,Units unit, int co2Amount, Bitmap img, String fuelType) {
        transList.add(new Transportation(name, question, unit, co2Amount ,img,fuelType));
    }

    public void addFood(Food food) {
        foodList.add(food);
    }
    public void addFood(String id,String name, String question,Units unit,  int co2Amount,Bitmap img) {
        foodList.add(new Food(id,name,question, unit,co2Amount,img));
    }

    public void addFood(String name, String question,Units unit,int co2Amount, Bitmap img) {
        foodList.add(new Food(name,question, unit,co2Amount,img));
    }


    public void addElectrics(ElectricalHouseSupplies electric) {
        electricsList.add(electric);
    }
    public void addElectrics(String id,String name, String question,Units unit, int co2Amount,Bitmap img) {
        electricsList.add(new ElectricalHouseSupplies(id,name,question, unit,co2Amount,img));
    }

    public void addElectrics(String name, String question, Units unit,int co2Amount,Bitmap img) {
        electricsList.add(new ElectricalHouseSupplies(name,question,unit,co2Amount,img));
    }

    public void addService(Service service) {
        serviceList.add(service);
    }
    public void addService(String id,String name, String question,Units unit, int co2Amount,Bitmap img) {
        serviceList.add(new Service(id,name,question,unit,co2Amount,img));
    }

    public void addService(String name, String question,Units unit,int co2Amount, Bitmap img) {
        serviceList.add(new Service(name,question,unit,co2Amount,img));
    }

    public ArrayList<? extends Co2Impacter> getImpactersByType(ImpactType type){
        ArrayList<? extends Co2Impacter> data=new ArrayList<>();
    switch (type){
        case TRANSPORTATION:
            data=getTransportation();
            break;
        case FOOD:
            data=getFood();
            break;
        case ELECTRICAL:
            data=getElectrics();
            break;
        case SERVICES:
            data=getServices();
            break;
    }
    return data;

    }

    public void addImpacter(ImpactType type, Co2Impacter impacter){
        switch (type){
            case TRANSPORTATION:
                transList.add((Transportation) impacter);
                break;
            case FOOD:
                foodList.add((Food) impacter);
                break;
            case ELECTRICAL:
                electricsList.add((ElectricalHouseSupplies) impacter);
                break;
            case SERVICES:
                serviceList.add((Service) impacter);
                break;
        }
    }

    public void removeImpacter(ImpactType type, Co2Impacter impacter){
        switch (type){
            case TRANSPORTATION:
                transList.remove(impacter);
                break;
            case FOOD:
                foodList.remove(impacter);
                break;
            case ELECTRICAL:
                electricsList.remove(impacter);
                break;
            case SERVICES:
                serviceList.remove(impacter);
                break;
        }
    }

    public Co2Impacter getImpacterByid(ImpactType type, int id) {
        ArrayList<Co2Impacter> impacterList=new ArrayList<>();
        switch (type){
            case TRANSPORTATION:
                impacterList=new ArrayList<>(getTransportation());
                break;
            case FOOD:
                impacterList=new ArrayList<>(getFood());
                break;
            case ELECTRICAL:
                impacterList=new ArrayList<>(getElectrics());
                break;
            case SERVICES:
                impacterList=new ArrayList<>(getServices());
                break;
        }
        for(Co2Impacter im: impacterList){
            if(im.getImpacterID().equals(id)){
                return im;
            }
        }
        return null;
    }

}
