package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class PreviousResultsActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();
    private static final String TAG = "PreviousResultsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_results);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.prevResultsTitle);
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
}