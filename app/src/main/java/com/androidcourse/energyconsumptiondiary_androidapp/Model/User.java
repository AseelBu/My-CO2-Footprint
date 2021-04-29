package com.androidcourse.energyconsumptiondiary_androidapp.Model;

public class User {
    public int userID;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public long points;

    public User(int userID, String email, String password, String firstName, String lastName, long points) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = points;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getPoints() {
        return points;
    }

    //adding points to user
    public void addpoints(int points)
    {

    }

    //remove points to user
    public void removepoints(int points)
    {

    }
}
