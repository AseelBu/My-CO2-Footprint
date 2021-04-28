package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.PieChart;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    private PieChart resultPie = null;
    private HalfGauge resultGauge = null;
    private Button tipsBtn = null;

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);
        resultPie=(PieChart) findViewById(R.id.resultsPie);
        resultGauge=(HalfGauge) findViewById(R.id.halfGauge);
        tipsBtn=(Button)findViewById(R.id.tipsBtn);
        Intent intent = getIntent();
//        if(intent!=null){
//
//        }
        context=this;

    }

    public void tipsBtnClicked(View v){
        Intent intent = new Intent(context, TipsActivity.class);
        startActivity(intent);
        
    }
}