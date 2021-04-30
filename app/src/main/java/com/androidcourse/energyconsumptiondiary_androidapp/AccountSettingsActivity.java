package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;

public class AccountSettingsActivity extends AppCompatActivity {
    private Context context;
    private CustomListAdapter adapter;
    private TextView name;
    private TextView email;
    private Button nextToUpdatePassword;
    public ImageButton returnBtn = null;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);

        name = (TextView) findViewById(R.id.nameset);
        email = (TextView) findViewById(R.id.emailaccount);
        nextToUpdatePassword = (Button) findViewById(R.id.arrowbtn3);
        returnBtn = (ImageButton) findViewById(R.id.imageButton4);

        context = this;
        name.setText("Georg Klien");
        email.setText("Georg.Klien@gmail.com");

    }
    public void returnBtnClicked(View v){
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);

    }
    public void UpdatePasswordClicked(View v){
        Intent intent = new Intent(context, UpdatePasswordActivity.class);
        startActivity(intent);

    }
}
