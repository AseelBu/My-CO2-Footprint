package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Entry {
    private String id;
    private String userId= null;
    private ArrayList<TypeEntry> entries= new ArrayList<TypeEntry>();
    private Date date = Calendar.getInstance().getTime();

    public Entry() {

    }


    public Entry(String userId) {
        this.userId = userId;
    }

   public void addEntry(TypeEntry data){
        this.entries.add(data);
   }

   public void addEntryList(List<TypeEntry> data){
       Set<TypeEntry> entriesSet =new HashSet<>(entries);

           for(TypeEntry dataTe : data) {
               if (!entriesSet.add(dataTe)) {
                   entriesSet.remove(dataTe);
                   entriesSet.add(dataTe);
               }
           }

       entries=new ArrayList<>(entriesSet);
   }

    public ArrayList<TypeEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<TypeEntry> entries) {
        this.entries = entries;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return getId().equals(entry.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    //converts entries list to map for firestore
    public List<Map<String,Object>> entriesToMap(){
        List<Map<String,Object>> entriesList = new ArrayList<>();
        for(TypeEntry te:entries){
            Map<String,Object> etm= new HashMap<>();
            etm.put("id",te.getId());
            etm.put("value",te.getId());
            etm.put("impacter type",te.getType());
            entriesList.add(etm);
        }

        return entriesList;
    }
}
