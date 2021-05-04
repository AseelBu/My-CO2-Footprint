package com.androidcourse.energyconsumptiondiary_androidapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class EntryDateFragment extends Fragment {


    private DatePicker datePicker;
    private Calendar date=Calendar.getInstance();




    public EntryDateFragment() {
        // Required empty public constructor
    }


    public static EntryDateFragment newInstance() {
        EntryDateFragment fragment = new EntryDateFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_entry_date, container, false);
        datePicker=(DatePicker)v.findViewById(R.id.entryDateCal);
//        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//               date=new Date(year,monthOfYear,dayOfMonth);
//            }
//        });
        return v;
    }
}