package com.androidcourse.energyconsumptiondiary_androidapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyPieData;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class PieChartFragment extends Fragment {

    private PieChart resultsPie =null;

    private static final String TAG = "PieChartFragment";

    private Result result=null;

    public PieChartFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_pie_chart, container, false);
        resultsPie=(PieChart) rootView.findViewById(R.id.resultsPie);
        // pie data
        loadPieChartData();
        animatePieChart();
        //appearance
        setupPieChart();


        return rootView;
    }

    private void setupPieChart(){
        resultsPie.setDrawHoleEnabled(false);
        resultsPie.setUsePercentValues(true);
        resultsPie.setEntryLabelTextSize(14);
        resultsPie.setEntryLabelColor(Color.BLACK);
//        resultsPie.setCenterText("CO2 footprint by category");
//        resultsPie.setCenterTextSize(24);
        resultsPie.getDescription().setEnabled(false);


        //setting legend
        Legend l =resultsPie.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(){
        ArrayList<PieEntry> entries =new ArrayList<>();

        MyPieData[] pieValues = {new MyPieData(0.2f,"Transportation2",getContext().getDrawable(R.drawable.ic_baseline_directions_car_24_white)),
                new MyPieData(0.3f,"Food2",getContext().getDrawable(R.drawable.ic_baseline_fastfood_24_food)),
                new MyPieData(0.4f,"Electricity",getContext().getDrawable(R.drawable.ic_baseline_flash_on_24_white)),
                new MyPieData(0.1f,"Services2",getContext().getDrawable(R.drawable.ic_baseline_wash_24_white))};

//        List<MyPieData> pieValues= createPieData(result);


        for (MyPieData data : pieValues) {
            // turn your data into Entry objects

            entries.add(new PieEntry(data.getValue(),data.getLabel(),data.getIcon()));
        }

        PieDataSet dataSet = new PieDataSet(entries,"");



        //setting colors
        ArrayList<Integer> colors = new ArrayList<>();
        for(int color : ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(60.f);
        dataSet.setValueLinePart1Length(0.40f);
        dataSet.setValueLinePart2Length(.1f);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setSliceSpace(2f);


        //configuring data appearance
        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(resultsPie));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        resultsPie.setDrawEntryLabels(false);
        resultsPie.setData(data);
        resultsPie.invalidate();

//        Legend legend=resultsPie.getLegend();
    }

    private void animatePieChart(){
        resultsPie.animateY(1600, Easing.EaseInQuad);
    }

    private List<MyPieData> createPieData(Result result){
        ArrayList<MyPieData> values= new ArrayList<>();
        values.add(new MyPieData(Double.valueOf(result.getTransportationResult()).floatValue(),"Transportation",getContext().getDrawable(R.drawable.ic_baseline_directions_car_24_white)));
        values.add(new MyPieData(Double.valueOf(result.getFoodResult()).floatValue(),"Food",getContext().getDrawable(R.drawable.ic_baseline_fastfood_24_food)));
        values.add(new MyPieData(Double.valueOf(result.getElectricsResult()).floatValue(),"Electrics",getContext().getDrawable(R.drawable.ic_baseline_flash_on_24_white)));
        values.add(new MyPieData(Double.valueOf(result.getServicesResult()).floatValue(),"Services",getContext().getDrawable(R.drawable.ic_baseline_wash_24_white)));
        return values;

    }
}