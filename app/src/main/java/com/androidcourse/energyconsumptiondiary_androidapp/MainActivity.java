package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context=this;
        Intent intent = new Intent(context,LogInActivity.class);
        startActivity(intent);
        MainActivity.this.finish();

        }
        }