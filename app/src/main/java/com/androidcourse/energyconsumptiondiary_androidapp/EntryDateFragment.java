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
import java.util.GregorianCalendar;


public class EntryDateFragment extends Fragment {


    private DatePicker datePicker;
    private Date selectedDate =Calendar.getInstance().getTime();



    public Date getSelectedDate() {
        return selectedDate;
    }

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
        datePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
               selectedDate =new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
//                Toast.makeText(getContext(),
//                        "changed selectedDate "+selectedDate,
//                        Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


}