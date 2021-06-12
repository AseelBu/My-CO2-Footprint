package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.github.mikephil.charting.charts.PieChart;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ResultsActivity extends AppCompatActivity {
    private static final String TAG = "ResultsActivity";
    private  MyCo2FootprintManager db=MyCo2FootprintManager.getInstance();
    private String resultId=null;
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
            resultId =intent.getStringExtra("resultId");
        }

        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.yourResults);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setIcon(R.drawable.ic_baseline_insert_chart_24);
        FragmentManager fm= getSupportFragmentManager();
        //gauge fragment
        Bundle argsGauge =new Bundle();
        argsGauge.putString("resultId",resultId);
        GaugeChartFragment gaugeFragment=new GaugeChartFragment();
        gaugeFragment.setArguments(argsGauge);
        FragmentTransaction t1 = fm.beginTransaction();
        t1.replace(R.id.fragmentGaugeChart,gaugeFragment);
        t1.commit();
        //pie fragment
        Bundle argsPie =new Bundle();
        argsPie.putString("resultId",resultId);
        PieChartFragment pieFragment=new PieChartFragment();
        pieFragment.setArguments(argsPie);
        FragmentTransaction t2 = fm.beginTransaction();
        t2.replace(R.id.fragmentPieResults,pieFragment);
        t2.commit();
        tipsBtn.setVisibility(View.GONE);
        context=this;


    }

    public void tipsBtnClicked(View v){
        Intent intent = new Intent(context, TipsActivity.class);
        startActivity(intent);
    }

    //menu creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menuEntryPrevResults:
                Intent intent1 = new Intent(this,PreviousResultsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuEntrySettings:
                Intent intent2 = new Intent(this,SettingsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuEntryLogout:
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ResultsActivity.this, LogInActivity.class);

                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null)
                        .show();
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