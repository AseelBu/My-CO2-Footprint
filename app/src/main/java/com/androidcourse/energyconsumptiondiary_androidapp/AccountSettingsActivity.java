package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class AccountSettingsActivity extends AppCompatActivity {
    private Context context;
    private CustomListAdapter adapter;
    private TextView name;
    private TextView email;
    private Button nextToUpdatePassword;
    public ImageButton returnBtn = null;
    public ImageView userImg =null;
    RelativeLayout relativeLayout;
    SharedPreferences prefs =null;

    private DataHolder dh = DataHolder.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        name = (TextView) findViewById(R.id.nameset);
        email = (TextView) findViewById(R.id.emailaccount);
        nextToUpdatePassword = (Button) findViewById(R.id.arrowbtn3);
//        returnBtn = (ImageButton) findViewById(R.id.imageButton4);
        userImg=(ImageView) findViewById(R.id.userImgSettings);

        context = this;

        User user = dh.getUserById(prefs.getInt(getResources().getString(R.string.prefLoggedUser),-1));
        name.setText(user.getName());
        email.setText(user.getEmail());
        userImg.setImageDrawable(user.getImage());

        ActionBar ab = getSupportActionBar();
//        ab.setTitle(R.string.Acc);
        ab.setDisplayHomeAsUpEnabled(true);


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

//    public void returnBtnClicked(View v){
//        Intent intent = new Intent(context, SettingsActivity.class);
//        startActivity(intent);
//
//    }
    public void UpdatePasswordClicked(View v){
        Intent intent = new Intent(context, UpdatePasswordActivity.class);
        startActivity(intent);

    }
}
