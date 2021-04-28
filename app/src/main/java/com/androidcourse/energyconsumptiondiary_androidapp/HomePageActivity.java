package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    private static final String TAG = "HomePageActivity";

    private Button enteryBtn = null;
    private Button prevResultsBtn = null;
    private Button leaderboardBtn = null;
    private Button settingsBtn = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        enteryBtn=(Button)findViewById(R.id.newEnteryBtn);
        prevResultsBtn=(Button)findViewById(R.id.previousResultsBtn);
        leaderboardBtn=(Button)findViewById(R.id.leaderboardBtn);
        settingsBtn=(Button)findViewById(R.id.settingsBtn);

        context=this;
    }

    public void entryBtnClicked(View v){
        Intent intent = new Intent(context, EntryActivity.class);
        startActivity(intent);

    }
    public void prevResultsBtnClicked(View v){

        Intent intent = new Intent(context, PreviousResultsActivity.class);
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
}