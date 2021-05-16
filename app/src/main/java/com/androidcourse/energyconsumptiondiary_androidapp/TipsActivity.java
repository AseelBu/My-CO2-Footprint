package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TipAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();
    private static final String TAG = "TipsActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        RecyclerView recList = (RecyclerView) findViewById(R.id.tipsList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

//        createList(10);
        TipAdapter ta= new TipAdapter(this,dh.getTips());
        recList.setAdapter(ta);

        ActionBar ab = getSupportActionBar();
//        ab.setTitle(R.string.yourResults);
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