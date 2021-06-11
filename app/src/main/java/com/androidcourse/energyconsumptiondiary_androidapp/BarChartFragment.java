package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class BarChartFragment extends Fragment implements OnChartValueSelectedListener {
    private static final String TAG = "BarChartFragment";
    MyCo2FootprintManager dbManager= MyCo2FootprintManager.getInstance();
    List<Result> allResults=new ArrayList<>();
    private BarChart barChart=null;
    private ArrayList<Date> datesLabels=new ArrayList<>();
    PrevResultsFragmentListener mListener;
    private Activity activity;

    public BarChartFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
        mListener = (PrevResultsFragmentListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        View rootView= inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart=(BarChart) rootView.findViewById(R.id.barChart);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        allResults=dbManager.getAllResults(userId,7);
        //set colors
        setupBarChart();
        //set data
        loadBarChartData();
        return rootView;
    }

    private void loadBarChartData() {

        ArrayList<BarEntry> results = new ArrayList<>();
        Collections.reverse(allResults);
        for(Result result:allResults){
            int index=allResults.size()-allResults.indexOf(result)-1;
            results.add(new BarEntry(allResults.indexOf(result),Double.valueOf(result.getTotal()).floatValue(),result));
        }

        BarDataSet set= new BarDataSet(results,"");

        for(Result result:allResults){

            datesLabels.add(result.getDate());
        }
        set.setColor(getActivity().getResources().getColor(R.color.darkGreen));
        set.setValueTextColor(Color.BLACK);
        set.setValueTextSize(12f);

        BarData data= new BarData(set);
        barChart.setData(data);
        barChart.setOnChartValueSelectedListener(this);

    }

    private void setupBarChart() {
        XAxis xAxis= barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12f);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setValueFormatter(

    new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                if (value >= 0) {
                    if (value <= datesLabels.size() - 1) {
                        String[] words = datesLabels.get((int) value).toString().split(" ");
                        return words[2]+" "+words[1]+" "+words[5];
                    }
                }
                return "";
            }

        }
        );
        barChart.setFitBars(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2600);
        barChart.setDrawValueAboveBar(true);
        barChart.getLegend().setEnabled(false);
    }
    private final RectF onValueSelectedRectF = new RectF();
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        RectF bounds = onValueSelectedRectF;
        barChart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = barChart.getPosition(e, YAxis.AxisDependency.LEFT);
        String resultId=((Result)e.getData()).getId();
        mListener.onResultSelected(BarChartFragment.this,resultId);
        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
        mListener.onNothingSelected(BarChartFragment.this);
    }
}