package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Entry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EntryActivity extends AppCompatActivity  {

    private static final String TAG = "EntryActivity";
    private static final int RESULT_REQ_CODE = 111;
    private static final int DATE = -1;
    private static final int TRANS = 0;
    private static final int FOOD = 1;
    private static final int ELECTRIC = 2;
    private static final int SERVICE = 3;

    private MyCo2FootprintManager dbManager = MyCo2FootprintManager.getInstance();
    private FirebaseFirestore dbCloud = FirebaseFirestore.getInstance();

    private Button nextBtn = null;
    private Button backBtn = null;

    private Entry entryData = new Entry();
    private Result result=null;
    private int currentIndex = DATE; //current fragment page number

    private EntryDataFragment transportFragment = EntryDataFragment.newInstance(ImpactType.TRANSPORTATION);
    private EntryDataFragment foodFragment = EntryDataFragment.newInstance(ImpactType.FOOD);
    private EntryDataFragment electricFragment = EntryDataFragment.newInstance(ImpactType.ELECTRICAL);
    private EntryDataFragment serviceFragment = EntryDataFragment.newInstance(ImpactType.SERVICES);
    private EntryDateFragment dateFragment = EntryDateFragment.newInstance();

    private boolean isResultsEnabled = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        super.onCreate(savedInstanceState);
        dbManager.openDataBase(EntryActivity.this);
        setContentView(R.layout.activity_entry);

        //get user Id
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        entryData.setUserId(userId);
        entryData.setId("entry_"+UUID.randomUUID());

        result = new Result(UUID.randomUUID().toString(),entryData.getUserId());
        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentNextClick(v, getCurrentFragmentEntries());
            }
        });
        backBtn = (Button) findViewById(R.id.backBtnEntry);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFragmentBackClick(getCurrentFragmentEntries());
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.root_layout, dateFragment)
                .commit();
        backBtn.setVisibility(View.GONE);
        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.newEntry));
        ab.setDisplayHomeAsUpEnabled(true);

        context = this;
        FirebaseFirestore dbCloud = FirebaseFirestore.getInstance();
        CollectionReference collRef = dbCloud.collection("co2 impacter");

        collRef.addSnapshotListener(EntryActivity.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    Toast.makeText(EntryActivity.this, "Listen failed." + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
//                    Toast.makeText(context, "Current data: " + snapshot.getDocuments(),
//                            Toast.LENGTH_LONG).show();
                    dbManager.openDataBase(EntryActivity.this);
                    dbManager.removeAllImpacters();
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

                        dbManager.createImpacterByType(cloudImpacter, ImpactType.valueOf(impacterType));
                    }
                    updateCurrFragmentAdapter();

                } else {
                    final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), getCurrentFragmentType().toString().toLowerCase()+" is empty", Snackbar.LENGTH_INDEFINITE);
                    bar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bar.dismiss();
                        }
                    });
                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                    bar.show();

                    dbManager.removeAllImpacters();
                    updateCurrFragmentAdapter();
                }
            }
        });
    }

    private ArrayList<TypeEntry> getCurrentFragmentEntries() {
        EntryDataFragment fr = null;
        ArrayList<TypeEntry> entries = new ArrayList<>();
        switch (currentIndex) {
            case TRANS:
                fr = transportFragment;
                entries = fr.getEntries();
                break;
            case FOOD:
                fr = foodFragment;
                entries = fr.getEntries();
                break;
            case ELECTRIC:
                fr = electricFragment;
                entries = fr.getEntries();
                break;
            case SERVICE:
                fr = serviceFragment;
                entries = fr.getEntries();
                break;
            case DATE:
                EntryDateFragment dateFr = dateFragment;
                entryData.setDate(dateFr.getSelectedDate());
                break;

        }
        return entries;
    }

    public void onFragmentNextClick(View v, List<TypeEntry> fragmentData) {
        Log.d("EntryActivity", "entered next clicked");
        int nextIndex = currentIndex + 1;
        currentIndex += 1;
        Log.d("EntryActivity", "next index =" + nextIndex);
        if (fragmentData.size() > 0) {
            entryData.addEntryList(fragmentData);
            nextBtn.setEnabled(true);
        }
        FragmentManager fm = getSupportFragmentManager();
        switch (nextIndex) {
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)
                        .commit();
                backBtn.setVisibility(View.VISIBLE);
                break;
            case FOOD:
                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)
                        .commit();
                break;
            case ELECTRIC:
                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)

                        .commit();
                break;
            case SERVICE:
                if (isResultsEnabled) {
                    enableResults();
                } else {
                    disableResults();
                }
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .commit();
                break;
            default:
                if (nextIndex == SERVICE + 1) {
                    currentIndex -= 1;

                    //go to results activity


                    Map<String,Object> entryMap=new HashMap<>();
                    entryMap.put("user uid",entryData.getUserId());
                    entryMap.put("date",entryData.getDate());
                    entryMap.put("entries",entryData.entriesToMap());
                    dbCloud.collection("entry")
                            .document(entryData.getId())
                            .set(entryMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dbManager.openDataBase(EntryActivity.this);
                                    // save the new entry
                                    saveEntryToDB(entryData.getUserId());

                                    //calculate result
//                                    Result result = new Result(entryData.getUserId(), entryData.getDate());
//                                    result.setId(UUID.randomUUID().toString());
                                    result.setDate(entryData.getDate());

                                    result.calculateAndSetResult(entryData.getEntries());
                                    int points = result.calculateResultPoints();
                                    //save points and result to cloud
                                    updateUserPointsinCloud(v, points,result);
                                dbManager.closeDataBase();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            final Snackbar barError = Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG);
                            barError.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    barError.dismiss();
                                }
                            });
                            barError.setActionTextColor(getResources().getColor(R.color.dangerRed));
                            barError.show();

                            //error in getting result
                            final Snackbar bar = Snackbar.make(v, "Something went wrong, can't calculate result", Snackbar.LENGTH_LONG);
                            bar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    barError.dismiss();
                                }
                            });
                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                            bar.show();

                        }
                    });


                }
        }
    }

    private void saveEntryToDB(String entryUid) {
        ArrayList<TypeEntry> entriesList = entryData.getEntries();
        dbManager.createEntry(entryData);
        //delete all type entries
        dbManager.deleteAllTypeEntryByEntryID(entryData.getId());
        for (TypeEntry entry : entriesList) {
            dbManager.createTypeEntry(entryData.getId(), entry);
        }


    }

    public void onFragmentBackClick(List<TypeEntry> fragmentData) {
        int prevIndex = currentIndex - 1;
        currentIndex -= 1;
        if (fragmentData.size() > 0) {
            this.entryData.addEntryList(fragmentData);
        }
        FragmentManager fm = getSupportFragmentManager();

        switch (prevIndex) {
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)
                        .commit();
                break;
            case FOOD:
                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)
                        .commit();
                break;
            case ELECTRIC:
                setNextBtn();
                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)
                        .commit();
                break;
            case SERVICE:
                if (isResultsEnabled) {
                    enableResults();
                } else {
                    disableResults();
                }
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .commit();
                break;
            case DATE:
                fm.beginTransaction()
                        .replace(R.id.root_layout, dateFragment)
                        .commit();
                backBtn.setVisibility(View.GONE);
                break;
            default:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onFragmentBackClick(getCurrentFragmentEntries());
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure ?")
                        .setMessage("By clicking exit the changes you made to this entry will be deleted")
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EntryActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

                return true;
            case R.id.menuEntryPrevResults:
                Intent intent1 = new Intent(this, PreviousResultsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuEntrySettings:
                Intent intent2 = new Intent(this, SettingsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuEntryLogout:
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EntryActivity.this, LogInActivity.class);

                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
        }
        return false;
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

    public Entry getEntryData() {
        return entryData;
    }

    public void enableResults() {
        nextBtn.setEnabled(true);
        nextBtn.setText(getResources().getString(R.string.showMyResults));
        nextBtn.setBackground(getDrawable(R.drawable.round_btn_dark_green));
    }

    public void disableResults() {
        nextBtn.setEnabled(false);
        nextBtn.setBackground(getDrawable(R.drawable.round_btn_dark_gray));
        nextBtn.setText(getResources().getString(R.string.entryForResults));

    }

    public void setNextBtn() {
        nextBtn.setText(getResources().getString(R.string.next));
        nextBtn.setEnabled(true);
        nextBtn.setBackground(getDrawable(R.drawable.round_btn_dark_green));

    }


    public void setResultsEnabled(boolean resultsEnabled) {
        isResultsEnabled = resultsEnabled;
    }

    public void removeEntryFromEntryData(TypeEntry cardData) {
        ArrayList<TypeEntry> tempEntries = entryData.getEntries();
        tempEntries.remove(cardData);
        entryData.setEntries(tempEntries);
    }


    private void updateUserPointsinCloud(View v, int resultPoints,Result result) {
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        String currUserId=currUser.getUid();
        //get users's current value of points
        dbCloud.collection("users")
                .document(currUserId)
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                dbManager.openDataBase(EntryActivity.this);
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Map<String, Object> user = document.getData();

                                        Number points = (Number) user.get("points");

//                                        Toast.makeText(context, "user current points: " + points,
//                                                Toast.LENGTH_LONG).show();
                                        int prevPoints = points.intValue();

                                        //update points
                                        dbCloud.collection("users").document(currUserId)
                                                .update("points", prevPoints + resultPoints)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dbManager.openDataBase(EntryActivity.this);
                                                        final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), "points updated successfully from "+prevPoints +"to "+(prevPoints + resultPoints)+" resultPoints "+resultPoints, Snackbar.LENGTH_SHORT);
                                                        bar.setAction("Dismiss", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                bar.dismiss();
                                                            }
                                                        });
                                                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                                        bar.show();

                                                        User user=new User(currUserId,currUser.getDisplayName(),prevPoints + resultPoints);
                                                        dbManager.replaceUserPoints(user);
                                                        //save result in cloud
                                                        saveResultToCloud(v,result);
                                                        dbManager.closeDataBase();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        final Snackbar bar = Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG);
                                                        bar.setAction("Dismiss", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                bar.dismiss();
                                                            }
                                                        });
                                                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                                        bar.show();
                                                    }
                                                });
                                    } else {

                                        final Snackbar bar = Snackbar.make(v, "No such document with uid "+ currUser, Snackbar.LENGTH_LONG);
                                        bar.setAction("Dismiss", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                bar.dismiss();
                                            }
                                        });
                                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                        bar.show();
                                    }
                                } else {
                                    final Snackbar bar = Snackbar.make(v, task.getException().getMessage(), Snackbar.LENGTH_LONG);
                                    bar.setAction("Dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bar.dismiss();
                                        }
                                    });
                                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                    bar.show();
                                }
                                dbManager.closeDataBase();
                            }
                        }
                );

    }

    private void saveResultToCloud(View v, Result result) {
        String currUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //get users's current value of points
        dbCloud.collection("results")
                .document(result.getId())
                .set(result)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dbManager.openDataBase(EntryActivity.this);
                        final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), "Result saved successfully", Snackbar.LENGTH_LONG);
                        bar.setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.dismiss();
                            }
                        });
                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                        bar.show();

//                        Toast.makeText(context,"DocumentSnapshot successfully written!",
//                                Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, ResultsActivity.class);
                        dbManager.createResult(result);
                        intent.putExtra("resultId", result.getId());
                        intent.putExtra("date", entryData.getDate());
                        startActivity(intent);
                        dbManager.closeDataBase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        final Snackbar bar = Snackbar.make(findViewById(android.R.id.content), e.getMessage(), Snackbar.LENGTH_LONG);
                        bar.setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.dismiss();
                            }
                        });
                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                        bar.show();
                    }
                });


    }

    private  void updateCurrFragmentAdapter(){
        switch (currentIndex) {
            case TRANS:
                transportFragment.updateUdapter();
                break;
            case FOOD:
                foodFragment.updateUdapter();
                break;
            case ELECTRIC:
                electricFragment.updateUdapter();
                break;
            case SERVICE:
                serviceFragment.updateUdapter();
                break;

        }
    }

    private  ImpactType getCurrentFragmentType(){
        switch (currentIndex) {
            case TRANS:
                return ImpactType.TRANSPORTATION;
            case FOOD:
                return ImpactType.FOOD;
            case ELECTRIC:
                return ImpactType.ELECTRICAL;
            case SERVICE:
                return ImpactType.SERVICES;

        }
        return ImpactType.TRANSPORTATION;
    }
}