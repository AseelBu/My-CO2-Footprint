package com.androidcourse.energyconsumptiondiary_androidapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class BarChartFragment extends Fragment {
    private static final String TAG = "BarChartFragment";
    private BarChart barChart=null;


    public BarChartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart=(BarChart) rootView.findViewById(R.id.barChart);

        //set colors
        setupBarChart();
        //set data
        loadBarChartData();

        return rootView;

    }

    private void loadBarChartData() {
        ArrayList<BarEntry> results = new ArrayList<>();
        for(int i=1; i<=10; i++){
        results.add(new BarEntry(i,i+1));
        }
        BarDataSet set= new BarDataSet(results,"");
        set.setColor(Color.BLUE);
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(12f);

        BarData data= new BarData(set);
        barChart.setData(data);

    }

    private void setupBarChart() {
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1600);
    }

}