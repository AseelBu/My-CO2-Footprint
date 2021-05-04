package com.androidcourse.energyconsumptiondiary_androidapp;
 import androidx.appcompat.app.ActionBar;
 import androidx.appcompat.app.AppCompatActivity;
 import android.content.Context;
 import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.ListView;
 import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TransportationListAdapter;
 import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
 import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
 import java.util.ArrayList;
 import java.util.List;

public class TransportationMain extends AppCompatActivity {
    private static final String TAG = " TransportationMain ";
    private static  final int Adding_REQ_CODE=111;
    private static  final int Edit_REQ_CODE=123;
    public static final int GET_FROM_GALLERY = 3;
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
        itemInfos.add(new Transportation("Plane","Diesel",700,getDrawable(R.drawable.travelling)));
        itemInfos.add(new Transportation("Bus","Diesel",500,getDrawable(R.drawable.bus)));
        itemInfos.add(new Transportation("Bycicle1","Diesel1",200,getDrawable(R.drawable.bycicle)));
        itemInfos.add(new Transportation("Bycicle2","Diesel2",200,getDrawable(R.drawable.car2)));
        itemInfos.add(new Transportation("Bycicle3","Diesel3",200,getDrawable(R.drawable.travelling)));
        itemInfos.add(new Transportation("Bycicle4","Diesel4",200,getDrawable(R.drawable.bus)));
        list = (ListView) findViewById(R.id.transportationlist);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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
        //open Adding transpotation Item Activity
    public void addClicked(View v) {
        Intent intent = new Intent(context, AddingItemActivity.class);
        startActivityForResult(intent,Adding_REQ_CODE);
    }

}