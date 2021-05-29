package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class PreviousResultsActivity extends AppCompatActivity implements PrevResultsFragmentListener {
    private DataHolder dh = DataHolder.getInstance();


    private static final String TAG = "PreviousResultsActivity";
    private CardView cv;
    private PieChartFragment pf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_results);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.prevResultsTitle);
        ab.setDisplayHomeAsUpEnabled(true);
        cv=(CardView)findViewById(R.id.dayResultCard);
        cv.setVisibility(View.GONE);

//        pf=(PieChartFragment)findViewById(R.id.pieChartPrevFragment);





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

    @Override
    public void onResultSelected(BarChartFragment prevFragment,int resultId) {
        cv.setVisibility(View.GONE);
        Bundle argsPie =new Bundle();
        argsPie.putInt("resultId",resultId);
        PieChartFragment pieFragment=new PieChartFragment();
        pieFragment.setArguments(argsPie);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.pieChartPrevFragment, pieFragment)
                 .commit();
        cv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(BarChartFragment prevFragment) {
        cv.setVisibility(View.GONE);
    }
}