package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class EntryActivity extends AppCompatActivity {

    private static final String TAG = "EntryActivity";

    private Button resultsBtn = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        resultsBtn=(Button)findViewById(R.id.resultsBtn);
        context = this;
    }

    public void resultsBtnClicked(View v){

        Intent intent = new Intent(context, ResultsActivity.class);
//        intent.putExtra();
        startActivity(intent);

    }
}