package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class PreviousResultsActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();
    private static final String TAG = "PreviousResultsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_results);
    }
}