package com.androidcourse.energyconsumptiondiary_androidapp;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import java.util.List;

public interface EntryDataFragmentListener {
    public void onFragmentNextClick(EntryDataFragment efragment, List<TypeEntry> entryData);
    public void onFragmentBackClick(EntryDataFragment efragment,List<TypeEntry> entryData);

}
