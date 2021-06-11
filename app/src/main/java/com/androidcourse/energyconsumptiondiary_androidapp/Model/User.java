package com.androidcourse.energyconsumptiondiary_androidapp.Model;


import android.graphics.drawable.Drawable;

import com.androidcourse.energyconsumptiondiary_androidapp.R;

import java.util.List;
import java.util.Objects;

public class User implements Comparable {
    private String userId;
    private String name = "";
    private int points = 0;
    private boolean isAdmin = false;

    public User() {
    }

    public User(String userId, String name, int points) {
        this.userId = userId;
        this.name = name;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public int compareTo(Object o) {
        return ((User) o).getPoints() - this.getPoints();
    }

    public String getUserRank() {
        List<User> allUsers = MyCo2FootprintManager.getInstance().getAllUsers();
        int index = -1;
        for (User user : allUsers) {
            if (user.getUserId().equals(userId)) {
                index = (allUsers.indexOf(user) + 1);
            }
        }

        return index + "/" + allUsers.size();
    }


}
