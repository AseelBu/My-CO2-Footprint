package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
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
    private Button editDataBtn = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_grid);

        enteryBtn=(Button)findViewById(R.id.newEnteryBtn);
        prevResultsBtn=(Button)findViewById(R.id.previousResultsBtn);
        leaderboardBtn=(Button)findViewById(R.id.leaderboardBtn);
        settingsBtn=(Button)findViewById(R.id.previousResultsBtn);
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
        ab.hide();

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
}