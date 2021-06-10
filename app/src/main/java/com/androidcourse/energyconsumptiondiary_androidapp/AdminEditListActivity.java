package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.AdminImpacterListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminEditListActivity extends AppCompatActivity {

    private static  final int ADDING_REQ_CODE =100;
    private static  final int EDIT_REQ_CODE =101;

//    private DataHolder dh = DataHolder.getInstance();
    private static final String IMPACTERTYPE = "ImpacterType";
    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
//    private AdminEditRecyclerViewAdapter adapterRec;
    private AdminImpacterListAdapter adapter;
    private FloatingActionButton addFab = null;
    private ImpactType type=ImpactType.TRANSPORTATION;
    private TextView txtFuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_list);
        db.openDataBase(this);
        txtFuel=(TextView)findViewById(R.id.txtFuelTypeAdminHeader);

        RecyclerView recList = (RecyclerView) findViewById(R.id.dataListAdminRec);
        ListView vList=(ListView)findViewById(R.id.dataListAdmin);

        recList.setVisibility(View.GONE);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recList.setLayoutManager(llm);
        Intent intent=this.getIntent();
        if(intent!=null) {
            type = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));
            if(!type.equals(ImpactType.TRANSPORTATION)){
                txtFuel.setVisibility(View.GONE);
            }

        }
//        //recycler
//        adapterRec= new AdminEditRecyclerViewAdapter(this,type);
//        recList.setAdapter(adapterRec);

        //list
        List<Co2Impacter> impacters=db.getImpactersByType(type);
        adapter = new AdminImpacterListAdapter(this, impacters,type);
        vList.setAdapter(adapter);

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
            adapter.updateImpactersData();
            adapter.notifyDataSetChanged();
        }
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }


    public void addBtnClicked(){
        Intent intent = new Intent(this,AddingItemActivity.class);
        intent.putExtra(IMPACTERTYPE, type.name());
        startActivity(intent);
        finish();
//        startActivityForResult(intent,ADDING_REQ_CODE);
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