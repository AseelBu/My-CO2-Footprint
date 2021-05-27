package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Entry {
    private int id;
    private int userId= -1;
    private ArrayList<TypeEntry> entries= new ArrayList<TypeEntry>();
    private Date date = Calendar.getInstance().getTime();

    public Entry() {

    }


    public Entry(int userId) {
        this.userId = userId;
    }

   public void addEntry(TypeEntry data){
        this.entries.add(data);
   }
   public void addEntryList(List<TypeEntry> data){
        this.entries.addAll(data);
   }

    public ArrayList<TypeEntry> getEntries() {
        return entries;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return getId() == entry.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
