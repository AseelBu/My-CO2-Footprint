package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private CustomListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingsmain);

        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
        itemInfos.add(new ItemInfo("Account Settings",R.drawable.user__1_));
        itemInfos.add(new ItemInfo("Notification Settings",R.drawable.bell));
        itemInfos.add(new ItemInfo("share with Friend",R.drawable.share));
        itemInfos.add(new ItemInfo());
//        itemInfos.add(new ItemInfo("Dark Mode",R.drawable.darkmode));
        list = (ListView) findViewById(R.id.list);

        adapter = new CustomListAdapter(this, itemInfos);


        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ItemInfo selecteditem = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), selecteditem.getName(),
                        Toast.LENGTH_SHORT).show();
                //adapter.remove(selecteditem);
            }
        });
    }
}
