package com.androidcourse.energyconsumptiondiary_androidapp;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class NavigationBar extends Fragment {

    public NavigationBar() {
    }

    public static NavigationBar newInstance(String param1, String param2) {
        NavigationBar fragment = new NavigationBar();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_bar, container, false);
    }
}