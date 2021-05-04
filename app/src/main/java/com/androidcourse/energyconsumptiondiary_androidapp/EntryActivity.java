package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Entry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity  {

    private static final String TAG = "EntryActivity";
    private static final int RESULT_REQ_CODE=111;
    private static final int DATE =-1;
    private static final int TRANS = 0;
    private static final int FOOD = 1;
    private static final int ELECTRIC = 2;
    private static final int SERVICE = 3;


    private Button nextBtn = null;
    private Button backBtn = null;

    private Entry entryData= new Entry();
    private int currentIndex=DATE; //current fragment page number

    private  EntryDataFragment transportFragment = EntryDataFragment.newInstance(ImpactType.TRANSPORTATIOIN);
    private  EntryDataFragment foodFragment = EntryDataFragment.newInstance(ImpactType.FOOD);
    private  EntryDataFragment electricFragment = EntryDataFragment.newInstance(ImpactType.ELECTRICAL);
    private  EntryDataFragment serviceFragment = EntryDataFragment.newInstance(ImpactType.SERVICES);
    private EntryDateFragment dateFragment=EntryDateFragment.newInstance();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        nextBtn=(Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentNextClick(getCurrentFragmentEntries());
            }
        });

        backBtn=(Button)findViewById(R.id.backBtnEntry);
        backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               onFragmentBackClick(getCurrentFragmentEntries());
           }
       });

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .replace(R.id.root_layout, dateFragment)

                .commit();

        backBtn.setVisibility(View.GONE);

        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.newEntry));
        ab.setDisplayHomeAsUpEnabled(true);

        context = this;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onFragmentBackClick(getCurrentFragmentEntries());
                return true;
        }
        return false;
    }

    private ArrayList<TypeEntry> getCurrentFragmentEntries(){
        EntryDataFragment fr=null;
        ArrayList<TypeEntry>entries=new ArrayList<>();
        switch (currentIndex){
            case TRANS:
                fr=transportFragment;
                entries=fr.getEntries();
                break;
            case FOOD:
                fr=foodFragment;
                entries=fr.getEntries();
                break;
            case ELECTRIC:
                fr=electricFragment;
                entries=fr.getEntries();
                break;
            case SERVICE:
                fr=serviceFragment;
                entries=fr.getEntries();
                break;
            case DATE:
                //TODO get date
                break;

        }
        return entries;
    }
    public void onFragmentNextClick(List<TypeEntry> fragmentData) {
        Log.d("EntryActivity","entered next clicked");
        int nextIndex = currentIndex+1;
        currentIndex+=1;
        Log.d("EntryActivity","next index =" +nextIndex);
        if(fragmentData.size()>0) {
            this.entryData.addEntryList(fragmentData);
        }
        FragmentManager fm = getSupportFragmentManager();

        switch (nextIndex){
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)

                        .commit();

                backBtn.setVisibility(View.VISIBLE);
                break;
            case FOOD:

                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)

                        .commit();

                break;
            case ELECTRIC:

                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)

                        .commit();

                break;
            case SERVICE:
                nextBtn.setText(getResources().getString(R.string.showMyResults));
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .commit();

                break;
            default:
                if(nextIndex==SERVICE+1){
                    currentIndex-=1;
                    //resultspage
                    Intent intent = new Intent(context, ResultsActivity.class);
//                    calculateResult(entry);
                    intent.putExtra("date",this.entryData.getDate());
                    startActivity(intent);
                }


        }

    }

    public void onFragmentBackClick( List<TypeEntry> fragmentData) {
        int prevIndex = currentIndex-1;
        currentIndex-=1;
        if(fragmentData.size()>0) {
            this.entryData.addEntryList(fragmentData);
        }
        FragmentManager fm = getSupportFragmentManager();

        switch (prevIndex){
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)

                        .commit();

                break;
            case FOOD:

                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)

                        .commit();

                break;
            case ELECTRIC:
                nextBtn.setText(getResources().getString(R.string.next));
                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)

                        .commit();

                break;
            case SERVICE:
                nextBtn.setText(getResources().getString(R.string.showMyResults));
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)

                        .commit();

                break;
            case DATE:
                    //date
                    //nextBtn.setText(getResources().getString(R.string.showMyResults));
                    fm.beginTransaction()
                            .replace(R.id.root_layout, dateFragment)

                            .commit();

                    backBtn.setVisibility(View.GONE);
                    break;
            default:
                finish();

        }
    }


//    // TODO
//    @Override
//    public void onFragmentNextClick(EntryDataFragment efragment,List<TypeEntry> entryData) {
//        int nextIndex = currentIndex+1;
//        FragmentManager fm = getSupportFragmentManager();
//
//        switch (nextIndex){
//            case TRANS:
//                fm.beginTransaction()
//                        .replace(R.id.root_layout, transportFragment)
//
//                        .commit();
//                break;
//            case FOOD:
//
//                fm.beginTransaction()
//                        .replace(R.id.root_layout, foodFragment)
//
//                        .commit();
//                break;
//            case ELECTRIC:
//
//                fm.beginTransaction()
//                        .replace(R.id.root_layout, electricFragment)
//
//                        .commit();
//                break;
//            case SERVICE:
//                fm.beginTransaction()
//                        .replace(R.id.root_layout, serviceFragment)
//
//                        .commit();
//                break;
//            default:
//                if(nextIndex==SERVICE+1){
//                    //resultspage
//                    Intent intent = new Intent(context, ResultsActivity.class);
////                    calculateResult(entry);
//                    //        intent.putExtra();
//                    startActivity(intent);
//                }
//
//        }
//
//    }
//
//    @Override
//    public void onFragmentBackClick(EntryDataFragment efragment, List<TypeEntry> entryData) {
//        int prevIndex = currentIndex-1;
//        switch (prevIndex){
//            case TRANS:
//
////                break;
//            case FOOD:
//                break;
//            case ELECTRIC:
//                break;
////            case SERVICE:
////                break;
//            default:
//                if(prevIndex==TRANS-1){
//                    //date
//                }
//
//        }
//    }
}