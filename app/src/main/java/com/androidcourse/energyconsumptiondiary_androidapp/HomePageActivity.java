package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePageActivity extends AppCompatActivity {
    private static final String TAG = "HomePageActivity";
    public static final int PERMISSIONS_REQUEST = 1;
    public static final String PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private Button editDataBtn = null;
    private Context context;
    private Activity activity;
    public  boolean isAdmin;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_grid);
        editDataBtn=(Button)findViewById(R.id.homeEditDataBtn);
        Intent intent = getIntent();
        if(intent!=null) {

            isAdmin= intent.getBooleanExtra("Admin",false);
//           if(isAdmin==false)
//            Log.d("lll","llll");
            if(!isAdmin){

                editDataBtn.setVisibility(View.GONE);
            }
        }

        context=this;
        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        //permissions
        if(!hasPermission()){
            requestPermission();
        }
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
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(HomePageActivity.this, LogInActivity.class);

                                HomePageActivity.this.startActivity(intent);

                                finish();
//                                Intent intent = new Intent(HomePageActivity.this, UserActivity.class);
//                                startActivity(intent);

                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
        }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(){
        return checkSelfPermission(PERMISSION_READ_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        if(shouldShowRequestPermissionRationale(PERMISSION_READ_STORAGE)){
            Toast.makeText(HomePageActivity.this,"Read storage permission is required",Toast.LENGTH_LONG).show();
        }
        requestPermissions(new String[]{PERMISSION_READ_STORAGE},PERMISSIONS_REQUEST);
    }
}