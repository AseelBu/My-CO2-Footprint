package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Entry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import java.util.List;

public class EntryActivity extends AppCompatActivity implements EntryDataFragmentListener {

    private static final String TAG = "EntryActivity";
    private static final int TRANS = 0;
    private static final int FOOD = 1;
    private static final int ELECTRIC = 2;
    private static final int SERVICE = 3;
    private Button resultsBtn = null;
    private Entry entryData= new Entry();
    private int currentIndex=TRANS; //current fragment page number
    private  EntryDataFragment transportFragment = EntryDataFragment.newInstance(ImpactType.TRANSPORTATIOIN);
    private  EntryDataFragment foodFragment = EntryDataFragment.newInstance(ImpactType.FOOD);
    private  EntryDataFragment electricFragment = EntryDataFragment.newInstance(ImpactType.ELECTRICAL);
    private  EntryDataFragment serviceFragment = EntryDataFragment.newInstance(ImpactType.SERVICES);
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        resultsBtn=(Button)findViewById(R.id.resultsBtn);
        FragmentManager fm = getFragmentManager();

        fm.beginTransaction()
                .replace(R.id.root_layout, transportFragment)
                .addToBackStack(null)
                .commit();
        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        context = this;
    }
    //open result activity
    public void resultsBtnClicked(View v){
        Intent intent = new Intent(context, ResultsActivity.class);
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
    public void onFragmentNextClick(EntryDataFragment efragment,List<TypeEntry> entryData) {
        int nextIndex = currentIndex+1;
        FragmentManager fm = getFragmentManager();
        switch (nextIndex){
            case FOOD:

                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case ELECTRIC:
                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case SERVICE:
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                if(nextIndex==SERVICE+1){
                    Intent intent = new Intent(context, ResultsActivity.class);
                    startActivity(intent);
                }
        }
    }
    @Override
    public void onFragmentBackClick(EntryDataFragment efragment, List<TypeEntry> entryData) {
        int prevIndex = currentIndex-1;
        switch (prevIndex){
            case TRANS:
            case FOOD:
                break;
            case ELECTRIC:
                break;
            default:
                if(prevIndex==TRANS-1){
                }
        }
    }
}