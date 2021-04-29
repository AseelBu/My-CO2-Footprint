package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TipAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class TipsActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();
    private static final String TAG = "TipsActivity";

    private String lorem="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ultrices tincidunt nisl eget semper. Nulla dictum feugiat tortor ac venenatis. Mauris id leo nisl. Donec vitae risus mauris. Suspendisse bibendum, nibh ac bibendum commodo, justo nisl eleifend tortor, sed rhoncus orci est nec risus. Maecenas scelerisque venenatis tellus vel porta. Vestibulum sed eros sodales, tincidunt neque in, consectetur dolor. Aenean eu nisi et massa suscipit lacinia. Integer hendrerit sapien viverra ante vulputate, non posuere augue condimentum. Sed mauris sapien, sollicitudin nec ullamcorper a, blandit ut sapien. ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        RecyclerView recList = (RecyclerView) findViewById(R.id.tipsList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        createList(10);
        TipAdapter ta= new TipAdapter(dh.getTips());
        recList.setAdapter(ta);
    }

    private void createList(int size) {

        List<Tip> result = new ArrayList<Tip>();
        Drawable img=getResources().getDrawable(R.drawable.car);
        for (int i = 1; i <= size; i++) {
            dh.addTip("Title",lorem,img);

        }
    }
}