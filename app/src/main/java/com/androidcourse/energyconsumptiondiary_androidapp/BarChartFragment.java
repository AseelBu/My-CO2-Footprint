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
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;


public class BarChartFragment extends Fragment implements OnChartValueSelectedListener {
    private static final String TAG = "BarChartFragment";
    MyCo2FootprintManager dbManager= MyCo2FootprintManager.getInstance();
    List<Result> allResults;
    private BarChart barChart=null;
    private ArrayList<String> datesLabels=new ArrayList<>();
//    String[] datesArr={"12.2", "15.2","18.2"};

    PrevResultsFragmentListener mListener;
    private Activity activity;

    public BarChartFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
        activity=getActivity();
        mListener = (PrevResultsFragmentListener) activity;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_bar_chart, container, false);
        barChart=(BarChart) rootView.findViewById(R.id.barChart);

        //get user Id
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());
        int userId=sharedPref.getInt(getResources().getString(R.string.prefLoggedUser),-1);
        allResults=dbManager.getAllResults(userId,7);
        //TODO result empty
//        if(allResults.isEmpty()){
//
//        }
        //set colors
        setupBarChart();
        //set data
        loadBarChartData();

        return rootView;

    }

    private void loadBarChartData() {

        ArrayList<BarEntry> results = new ArrayList<>();
//        for(int i=1; i<=4; i++){
//        results.add(new BarEntry(i,i+1));
//        }
        //TODO uncomment
        for(Result result:allResults){
            results.add(new BarEntry(allResults.indexOf(result),Double.valueOf(result.getTotal()).floatValue(),result));
        }

        BarDataSet set= new BarDataSet(results,"");
        //TODO uncomment
//        for(Result result:allResults){
//            datesLabels.add(result.getDate());
//        }

        datesLabels.add("12.2.21");
        datesLabels.add("13.2.21");
//        datesLabels.add("14.2.21");
//        datesLabels.add("15.2.21");

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
//        xAxis.setTypeface(tfRegular);
        xAxis.setEnabled(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(13f);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(datesLabels.size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(-90);

        xAxis.setValueFormatter(
//                new IndexAxisValueFormatter(datesLabels)
    new ValueFormatter() {
//
//            @Override
//            public String getBarLabel(BarEntry barEntry) {
////                super.getBarLabel(barEntry);
//                return ((Result)(barEntry.getData())).getDate().toString();
//            }


//            @Override
//            public String getAxisLabel(float value, AxisBase axis) {
////                super.getAxisLabel(value, axis)
//               if (value >= 0) {
//                    if (value <= datesLabels.size() - 1) {
//                        return datesLabels.get((int) value);
//                    }
//                }
//               return "";
//            }
            @Override
            public String getFormattedValue(float value) {
                if (value >= 0) {
                    if (value <= datesLabels.size() - 1) {
                        return datesLabels.get((int) value);
                    }
                }
                return "";

            }

        }
        );
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1600);
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

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + barChart.getLowestVisibleX() + ", high: "
                        + barChart.getHighestVisibleX());

//        Toast.makeText(getContext(),
//                "result clicked "+e.getY() ,
//                Toast.LENGTH_SHORT).show();
//        Log.i("resultId", "resultId: "+((Result)e.getData()).getId());

        int resultId=((Result)e.getData()).getId();
        mListener.onResultSelected(BarChartFragment.this,resultId);

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
        mListener.onNothingSelected(BarChartFragment.this);
    }


}