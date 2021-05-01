package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class MainActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();

    private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dh.addAdminDetails("Admin@gmail.com", "Admin");
            context=this;
        Intent intent = new Intent(context,LogInActivity.class);
        startActivity(intent);
        MainActivity.this.finish();

        }
        }