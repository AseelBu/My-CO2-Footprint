package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.FoodListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.ServicesListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class FoodMain extends AppCompatActivity {

    private static final String TAG = "FoodMain";
    private static  final int Adding_REQ_CODE=111;
    private static  final int Edit_REQ_CODE=123;

    private Context context;
    private ListView list;
    private FoodListAdapter adapter;
    private DataHolder dh = DataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);
        context=this;
        List<Food> itemInfos = new ArrayList<Food>();
//        itemInfos.add(new Transportation(null,null,0,0));

        itemInfos.add(new Food("Fish",700,R.drawable.fish));
        itemInfos.add(new Food("Eggs",100,R.drawable.eggs));
        itemInfos.add(new Food("Chocolate",200,R.drawable.chocolate));
        itemInfos.add(new Food("Water",200,R.drawable.water));


        list = (ListView) findViewById(R.id.foodlist);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        adapter = new FoodListAdapter(this, itemInfos);

        list.setAdapter(adapter);




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
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Adding_REQ_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Food s = (Food) extras.get("data");
            Log.d("offfffffff",(String.valueOf(s.getCO2Amount())));
            adapter.addToList(s);
            adapter.notifyDataSetChanged();

        }
        if (requestCode == Edit_REQ_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Food se = (Food) extras.get("data");

            int position = (int) extras.get("positionb");
            adapter.removeFromList(position);
            adapter.addToList(se);
            adapter.notifyDataSetChanged();

        }
    }

    public void addClicked(View v) {
        Intent intent = new Intent(context, AddingItemActivityb.class);
        startActivityForResult(intent,Adding_REQ_CODE);


    }



}