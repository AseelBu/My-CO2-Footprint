package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Entry {
    int userId= -1;
    ArrayList<TypeEntry> entries= new ArrayList<TypeEntry>();
    Date date = Calendar.getInstance().getTime();

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
}
