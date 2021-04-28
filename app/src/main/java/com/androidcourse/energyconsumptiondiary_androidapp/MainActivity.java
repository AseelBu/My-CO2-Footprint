package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button homeBtn=null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        Intent intent = new Intent(context, HomePageActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
        //homeBtn=findViewById(R.id.homePage);



    }

    public void homeBtnClicked(View v){
        Intent intent = new Intent(context, HomePageActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
    }
}