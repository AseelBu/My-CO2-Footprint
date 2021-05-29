package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.PieChart;

public class ResultsActivity extends AppCompatActivity {

    private static final String TAG = "ResultsActivity";



    private int resultId=-1;
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
            resultId =intent.getIntExtra("resultId",-1);


        }
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.yourResults);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setIcon(R.drawable.ic_baseline_insert_chart_24);


        FragmentManager fm= getSupportFragmentManager();

        //gauge fragment
        Bundle argsGauge =new Bundle();
        argsGauge.putInt("resultId",resultId);
        GaugeChartFragment gaugeFragment=new GaugeChartFragment();
        gaugeFragment.setArguments(argsGauge);
        FragmentTransaction t1 = fm.beginTransaction();
        t1.replace(R.id.fragmentGaugeChart,gaugeFragment);
        t1.commit();
        //pie fragment
        Bundle argsPie =new Bundle();
        argsPie.putInt("resultId",resultId);
        PieChartFragment pieFragment=new PieChartFragment();
        pieFragment.setArguments(argsPie);
        FragmentTransaction t2 = fm.beginTransaction();
        t2.replace(R.id.fragmentPieResults,pieFragment);
        t2.commit();
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