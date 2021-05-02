package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AdminHomePageActivity extends AppCompatActivity {
    private static final String TAG = "AdminHomePageActivity";

    private Button DataEdit=null;
    private Button enteryBtn = null;
    private Button prevResultsBtn = null;
    private Button leaderboardBtn = null;
    private Button settingsBtn = null;
    private Button logoutBtn = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);
        DataEdit=(Button)findViewById(R.id.DataEdit222);
        enteryBtn =(Button)findViewById(R.id.NewEnt222);
        prevResultsBtn=(Button)findViewById(R.id.previousResultsBtn222);
        leaderboardBtn=(Button)findViewById(R.id.leaderboardBtn22);
        settingsBtn=(Button)findViewById(R.id.settingsBtn32);
        logoutBtn=(Button)findViewById(R.id.logout22);

        context=this;
        ActionBar ab = getSupportActionBar();
//        ab.setTitle(R.string.yourResults);
        ab.setDisplayHomeAsUpEnabled(true);
    }


    public void entryBtnClicked(View v){
        Intent intent = new Intent(context, EntryActivity.class);
        startActivity(intent);

    }
    public void logoutBtnClicked(View v){
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }
    public void prevResultsBtnClicked(View v){

        Intent intent = new Intent(context, PreviousResultsActivity.class);
        startActivity(intent);

    }
    public void DataEditBtnClicked(View v){

        Intent intent = new Intent(context,EditMainActivity.class);
        startActivity(intent);

    }
    public void leaderboardBtnClicked(View v){

        Intent intent = new Intent(context, LeaderboardAndPointsActivity.class);
        startActivity(intent);

    }
    public void settingsBtnClicked(View v){

        Intent intent = new Intent(context, SettingsActivity.class);
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
}