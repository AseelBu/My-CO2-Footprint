package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomePageActivity extends AppCompatActivity {
    private static final String TAG = "HomePageActivity";

    private Button editDataBtn = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_grid);

        editDataBtn=(Button)findViewById(R.id.homeEditDataBtn);
        Intent intent = getIntent();
        if(intent!=null) {
           boolean isAdmin= intent.getBooleanExtra("Admin",false);
            if(!isAdmin){
                editDataBtn.setVisibility(View.GONE);
            }
        }

        context=this;

        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
//        ab.setLogo(R.drawable.logo_co2);
//        ab.setDisplayUseLogoEnabled(true);


    }
    //open new entry activity
    public void entryBtnClicked(View v){
        Intent intent = new Intent(context, EntryActivity.class);
        startActivity(intent);

    }
    //logout
    public void logoutBtnClicked(View v){
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
        finish();

    }
    //open previous result activity
    public void prevResultsBtnClicked(View v){
        Intent intent = new Intent(context, PreviousResultsActivity.class);
        startActivity(intent);

    }
    //open leaderboard and points activity
    public void leaderboardBtnClicked(View v){
        Intent intent = new Intent(context, LeaderboardAndPointsActivity.class);
        startActivity(intent);

    }
    //open settings activity
    public void settingsBtnClicked(View v){
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);

    }

    //enter to edit data activity
    public void editDataBtnClicked(View v){

        Intent intent = new Intent(context,EditMainActivity.class);
        startActivity(intent);
    }

//menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuLogout:
                Intent intent = new Intent(this,LogInActivity.class);
                startActivity(intent);
               finish();
                return true;

        }
        return false;
    }
}