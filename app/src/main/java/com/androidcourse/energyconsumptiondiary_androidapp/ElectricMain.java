package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.ElectricListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.util.ArrayList;
import java.util.List;

public class ElectricMain extends AppCompatActivity {
    private static final String TAG = "ElectricMain";
    private static  final int Adding_REQ_CODE=111;
    private static  final int Edit_REQ_CODE=123;
    private Context context;
    private ListView list;
    private ElectricListAdapter adapter;
    private DataHolder dh = DataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_electric);
        context=this;
        List<ElectricalHouseSupplies> itemInfos = new ArrayList<>();
        itemInfos.add(new ElectricalHouseSupplies("Washing machine",700,getDrawable(R.drawable.washingmachine)));
        itemInfos.add(new ElectricalHouseSupplies("Dish washer",100,getDrawable(R.drawable.dishwasher)));
        itemInfos.add(new ElectricalHouseSupplies("Kettele",200,getDrawable(R.drawable.kett)));
        itemInfos.add(new ElectricalHouseSupplies("Lamps",200,getDrawable(R.drawable.lamps)));
        list = (ListView) findViewById(R.id.electriclist);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        adapter = new ElectricListAdapter(this, itemInfos);
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
            ElectricalHouseSupplies elec = (ElectricalHouseSupplies) extras.get("data");
            adapter.addToList(elec);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == Edit_REQ_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            ElectricalHouseSupplies e = (ElectricalHouseSupplies) extras.get("data");

            int position = (int) extras.get("positiona");
            adapter.removeFromList(position);
            adapter.addToList(e);
            adapter.notifyDataSetChanged();
        }
    }
    //open adding item activity
    public void addClicked(View v) {
        Intent intent = new Intent(context, AddingItemActivitya.class);
        startActivityForResult(intent,Adding_REQ_CODE);
    }
}