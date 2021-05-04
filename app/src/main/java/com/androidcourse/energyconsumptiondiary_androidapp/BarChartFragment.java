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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class BarChartFragment extends Fragment {
    private static final String TAG = "BarChartFragment";
    private BarChart barChart=null;
    private ArrayList<String> datesLabels=new ArrayList<>();
    String[] datesArr={"12.2", "15.2","18.2"};


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
        for(int i=1; i<=4; i++){
        results.add(new BarEntry(i,i+1));
        }
        BarDataSet set= new BarDataSet(results,"");
        datesLabels.add("12.2.21");
        datesLabels.add("13.2.21");
        datesLabels.add("14.2.21");
        datesLabels.add("15.2.21");

        set.setColor(getActivity().getResources().getColor(R.color.green1));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(12f);

        BarData data= new BarData(set);
        barChart.setData(data);

    }

    private void setupBarChart() {

        XAxis xAxis= barChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(datesLabels.size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-90);
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getBarLabel(BarEntry barEntry) {
                return super.getBarLabel(barEntry);
            }


            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return super.getAxisLabel(value, axis);
            }
        });
        barChart.setFitBars(true);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1600);
    }

}