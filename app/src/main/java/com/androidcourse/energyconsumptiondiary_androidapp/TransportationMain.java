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
 import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
 import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
 import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

 import java.util.ArrayList;
 import java.util.List;

public class TransportationMain extends AppCompatActivity {

    private static final String TAG = " TransportationMain  ";
    private static  final int Adding_REQ_CODE=111;
    private static  final int Edit_REQ_CODE=123;

    private Context context;
    private ListView list;
    private TransportationListAdapter adapter;
    private DataHolder dh = DataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transportation);
        context=this;
        List<Transportation> itemInfos = new ArrayList<>();
//        itemInfos.add(new Transportation(null,null,0,0));

        itemInfos.add(new Transportation("Plane","Diesel",700,R.drawable.travelling));
        itemInfos.add(new Transportation("Bus","Diesel",500,R.drawable.bus));
        itemInfos.add(new Transportation("Bycicle1","Diesel1",200,R.drawable.bycicle));
        itemInfos.add(new Transportation("Bycicle2","Diesel2",200,R.drawable.car2));
        itemInfos.add(new Transportation("Bycicle3","Diesel3",200,R.drawable.travelling));
        itemInfos.add(new Transportation("Bycicle4","Diesel4",200,R.drawable.bus));
//        for(Transportation t:dh.getTransportation())
//        {
////            Log.d("sssssssss", "aaaaaaa");
//            itemInfos.add(t);
//        }

        list = (ListView) findViewById(R.id.transportationlist);

        ActionBar ab = getSupportActionBar();
//        ab.setTitle(R.string.settings);
        ab.setDisplayHomeAsUpEnabled(true);
//
//        adapter = new TransportationListAdapter(this, itemInfos);
//
//
//        list.setAdapter(adapter);
        adapter = new TransportationListAdapter(this, itemInfos);

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
            Transportation trans = (Transportation) extras.get("data");
            Log.d("offfffffff",(String.valueOf(trans.getCO2Amount())));
            adapter.addToList(trans);
            adapter.notifyDataSetChanged();

        }
         if (requestCode == Edit_REQ_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Transportation trans = (Transportation) extras.get("data");

            int position = (int) extras.get("position");
            adapter.removeFromList(position);
            adapter.addToList(trans);
            adapter.notifyDataSetChanged();

        }
        }

    public void addClicked(View v) {
        Intent intent = new Intent(context, AddingItemActivity.class);
        startActivityForResult(intent,Adding_REQ_CODE);


    }



}