package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.AdminImpacterListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class AdminEditListActivity extends AppCompatActivity {

    private static  final int ADDING_REQ_CODE =100;
    private static  final int EDIT_REQ_CODE =101;
    private static final String IMPACTERTYPE = "ImpacterType";
    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    private AdminImpacterListAdapter adapter;
    private FloatingActionButton addFab = null;
    private ImpactType type=ImpactType.TRANSPORTATION;
    private TextView txtFuel;
    private ListView vList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_list);
        db.openDataBase(this);
        txtFuel=(TextView)findViewById(R.id.txtFuelTypeAdminHeader);
        RecyclerView recList = (RecyclerView) findViewById(R.id.dataListAdminRec);
         vList=(ListView)findViewById(R.id.dataListAdmin);
        recList.setVisibility(View.GONE);
        Intent intent=this.getIntent();
        if(intent!=null) {
            type = ImpactType.valueOf(intent.getStringExtra(IMPACTERTYPE));

            if(!type.equals(ImpactType.TRANSPORTATION)){
                txtFuel.setVisibility(View.GONE);
            }

            String comebackName=intent.getStringExtra("comebackName");
            if(comebackName!=null){
                String action=intent.getStringExtra("action");
                final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), comebackName+" was "+action+" successfully", Snackbar.LENGTH_LONG);
                bar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bar.dismiss();
                    }
                });
                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                bar.show();
            }
        }
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

        FirebaseFirestore dbCloud = FirebaseFirestore.getInstance();
        CollectionReference collRef = dbCloud.collection("co2 impacter");

        collRef.addSnapshotListener(AdminEditListActivity.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                db.openDataBase(AdminEditListActivity.this);
                if (e != null) {

                    Toast.makeText(AdminEditListActivity.this, "Listen failed." + e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    db.openDataBase(AdminEditListActivity.this);
                    db.removeAllImpacters();
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        Map<String, Object> impacter = document.getData();
                        String id = document.getId();
                        String name = (String) impacter.get("name");
                        String question = (String) impacter.get("question");
                        Number co2Amount = (Number) impacter.get("co2Amount");
                        String unit = (String) impacter.get("unit");
                        String urlImage = (String) impacter.get("urlImage");
                        String impacterType = (String) impacter.get("impacterType");
                        String fuelType = null;
                        if (impacterType.equals(ImpactType.TRANSPORTATION.toString())) {
                            fuelType = (String) impacter.get("fuelType");
                        }
                        Co2Impacter cloudImpacter = null;
                        switch (ImpactType.valueOf(impacterType)) {
                            case TRANSPORTATION:
                                cloudImpacter = new Transportation();
                                ((Transportation) cloudImpacter).setFuelType(fuelType);
                                break;
                            case FOOD:
                                cloudImpacter = new Food();
                                break;
                            case ELECTRICAL:
                                cloudImpacter = new ElectricalHouseSupplies();
                                break;
                            case SERVICES:
                                cloudImpacter = new Service();
                                break;
                        }
                        cloudImpacter.setImpacterID(id);
                        cloudImpacter.setName(name);
                        cloudImpacter.setQuestion(question);
                        cloudImpacter.setUnit(Units.valueOf(unit));
                        cloudImpacter.setUrlImage(urlImage);
                        cloudImpacter.setCo2Amount(co2Amount.intValue());
                        db.createImpacterByType(cloudImpacter, ImpactType.valueOf(impacterType));
                    }

//                    Toast.makeText(AdminEditListActivity.this, "impacters updated",
//                            Toast.LENGTH_LONG).show();
                    List<Co2Impacter> impacters2=db.getImpactersByType(type);
                    adapter = new AdminImpacterListAdapter(AdminEditListActivity.this, impacters2,type);
                    vList.setAdapter(adapter);

                } else {
                    db.removeAllImpacters();
                    final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), type.toString().toLowerCase()+" is empty", Snackbar.LENGTH_INDEFINITE);
                    bar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bar.dismiss();
                        }
                    });
                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                    bar.show();

                    List<Co2Impacter> impacters2=db.getImpactersByType(type);
                    adapter = new AdminImpacterListAdapter(AdminEditListActivity.this, impacters2,type);
                    vList.setAdapter(adapter);
                }
                db.closeDataBase();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == ADDING_REQ_CODE ||requestCode==EDIT_REQ_CODE) && resultCode == RESULT_OK) {
            adapter.updateImpactersData();
            adapter.notifyDataSetChanged();
            String impacterName= data.getStringExtra("impacterName");
//            if(requestCode == ADDING_REQ_CODE&& resultCode == RESULT_OK){ final Snackbar bar = Snackbar.make(,impacterName+" was added successfully", Snackbar.LENGTH_INDEFINITE);
//                bar.setAction("Dismiss", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bar.dismiss();
//                    }
//                });
//                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                bar.show();
//
//
//            }else if(requestCode == EDIT_REQ_CODE&& resultCode == RESULT_OK){
//                final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), impacterName+" was edited successfully", Snackbar.LENGTH_INDEFINITE);
//                bar.setAction("Dismiss", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bar.dismiss();
//                    }
//                });
//                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                bar.show();
//
//            }
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
        startActivityForResult(intent,ADDING_REQ_CODE);
        finish();
    }
    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
        List<Co2Impacter> impacters=db.getImpactersByType(type);
        adapter = new AdminImpacterListAdapter(this, impacters,type);
        vList.setAdapter(adapter);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();

    }

    public void showDeleteSnackBar(String impacterName){
        final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), impacterName+" was deleted successfully", Snackbar.LENGTH_LONG);
        bar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
            }
        });
        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
        bar.show();
    }
}