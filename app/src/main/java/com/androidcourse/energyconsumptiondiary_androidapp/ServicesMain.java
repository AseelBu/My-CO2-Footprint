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
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.ServicesListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Services;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import java.util.ArrayList;
import java.util.List;

public class ServicesMain extends AppCompatActivity {
    private static final String TAG = "ServicesMain";
    private static  final int Adding_REQ_CODE=111;
    private static  final int Edit_REQ_CODE=123;
    private Context context;
    private ListView list;
    private ServicesListAdapter adapter;
    private DataHolder dh = DataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services);
        context=this;
        List<Services> itemInfos = new ArrayList<>();
        itemInfos.add(new Services("water consumption ",700,getDrawable(R.drawable.wat)));
        itemInfos.add(new Services("gas1",100,getDrawable(R.drawable.gas)));
        itemInfos.add(new Services("gas2",100,getDrawable(R.drawable.gas)));
        itemInfos.add(new Services("water consumption1 ",700,getDrawable(R.drawable.wat)));
        itemInfos.add(new Services("water consumption2 ",700,getDrawable(R.drawable.wat)));
        list = (ListView) findViewById(R.id.serviceslist);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        adapter = new ServicesListAdapter(this, itemInfos);
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
            Services f = (Services) extras.get("data");
            adapter.addToList(f);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == Edit_REQ_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Services foo = (Services) extras.get("data");
            int position = (int) extras.get("positionc");
            adapter.removeFromList(position);
            adapter.addToList(foo);
            adapter.notifyDataSetChanged();
        }
    }
           //open AddingItemActivity activity
    public void addClicked(View v) {
        Intent intent = new Intent(context, AddingItemActivityc.class);
        startActivityForResult(intent,Adding_REQ_CODE);


    }



}