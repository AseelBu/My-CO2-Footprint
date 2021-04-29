package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AcoountSettingsActivity extends AppCompatActivity {

    public TextView name;
    public TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);
        name=(TextView)findViewById(R.id.nameset);
        name.setText("Georg Klien");
        email=(TextView)findViewById(R.id.emailaccount);
        email.setText("Georg.Klien@gmail.com");

    }
}