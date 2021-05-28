package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.PieChart;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";

    MyCo2FootprintManager dbManager= MyCo2FootprintManager.getInstance();

    private Result result= null;
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
        if(intent!=null){
            int resultId =intent.getIntExtra("resultId",-1);
            result=dbManager.getResultById(resultId);

        }
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.yourResults);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setIcon(R.drawable.ic_baseline_insert_chart_24);
        context=this;

    }

    public void tipsBtnClicked(View v){
        Intent intent = new Intent(context, TipsActivity.class);
        startActivity(intent);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }

}