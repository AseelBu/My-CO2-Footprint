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

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditMainActivity extends AppCompatActivity {
    private static final String TAG = "AdminHomePageActivity";
    private static final String IMPACTERTYPE = "ImpacterType";

//    private Button DataEdit=null;
//    private Button enteryBtn = null;
//    private Button prevResultsBtn = null;
//    private Button leaderboardBtn = null;
//    private Button settingsBtn = null;
//    private Button logoutBtn = null;

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_options);

//        DataEdit=(Button)findViewById(R.id.DataEdit222);
//        enteryBtn =(Button)findViewById(R.id.NewEnt222);
//        prevResultsBtn=(Button)findViewById(R.id.previousResultsBtn222);
//        leaderboardBtn=(Button)findViewById(R.id.leaderboardBtn22);
//        settingsBtn=(Button)findViewById(R.id.settingsBtn32);
//        logoutBtn=(Button)findViewById(R.id.logout22);
        context=this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
    //open transportation main activity
    public void transportationBtnClicked(View v)
    {
        Intent intent = new Intent(context, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, ImpactType.TRANSPORTATIOIN.name());
        startActivity(intent);

    }
    //open food main activity
    public void foodBtnClicked(View v)
    {
        Intent intent = new Intent(context, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, ImpactType.FOOD.name());
        startActivity(intent);

    }
    //open electric main activity
    public void electricBtnClicked(View v)
    {
        Intent intent = new Intent(context, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, ImpactType.ELECTRICAL.name());
        startActivity(intent);

    }
    //open services main activity
    public void servicesBtnClicked(View v)
    {
        Intent intent = new Intent(context, AdminEditListActivity.class);
        intent.putExtra(IMPACTERTYPE, ImpactType.SERVICES.name());
        startActivity(intent);

    }
    //logout
//    public void logoutBtnClicked(View v)
//    {
//        Intent intent = new Intent(context, LogInActivity.class);
//        startActivity(intent);
//
//    }




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