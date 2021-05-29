package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.AdminEditRecyclerViewAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TipAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.R;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;

public class AdminEditListActivity extends AppCompatActivity {

    private static  final int ADDING_REQ_CODE =100;
    private static  final int EDIT_REQ_CODE =101;

    private DataHolder dh = DataHolder.getInstance();
    private static final String IMPACTERTYPE = "ImpacterType";
    private AdminEditRecyclerViewAdapter adapter;
    private FloatingActionButton addFab = null;
    private ImpactType type=ImpactType.TRANSPORTATIOIN;
    private TextView txtFuel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_list);
        txtFuel=(TextView)findViewById(R.id.txtFuelTypeAdminHeader);
        RecyclerView recList = (RecyclerView) findViewById(R.id.dataListAdmin);
//        if(ImpactType instanceof Transportation)
//         MyCo2FootprintManager.getInstance().readCO2Impacter(Transportation);
//        MyCo2FootprintManager.getInstance().readTransportation(id,item);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        Intent intent=this.getIntent();
        if(intent!=null) {
            type = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            if(!type.equals(ImpactType.TRANSPORTATIOIN)){
                txtFuel.setVisibility(View.GONE);
            }

        }
        adapter= new AdminEditRecyclerViewAdapter(this,type);
        recList.setAdapter(adapter);

        ActionBar ab = getSupportActionBar();
        ab.setTitle(type.name());
        ab.setDisplayHomeAsUpEnabled(true);

        addFab=(FloatingActionButton)findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtnClicked();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == ADDING_REQ_CODE ||requestCode==EDIT_REQ_CODE) && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Transportation2 trans = (Transportation2) extras.get("data");
//            adapter.addToList(trans);
            adapter.updateImpactersData();
            adapter.notifyDataSetChanged();
        }
//         if (requestCode == EDIT_REQ_CODE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Transportation2 trans = (Transportation2) extras.get("data");
//            int position = (int) extras.get("position");
//            adapter.removeFromList(position);
//            adapter.addToList(trans);
//            adapter.notifyDataSetChanged();
//        }
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


    public void addBtnClicked(){
        Intent intent = new Intent(this,AddingItemActivity.class);
        intent.putExtra(IMPACTERTYPE, type.name());
        startActivityForResult(intent,ADDING_REQ_CODE);
    }
    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }
}