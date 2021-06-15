package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class PreviousResultsActivity extends AppCompatActivity implements PrevResultsFragmentListener {
    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    private static final String TAG = "PreviousResultsActivity";
    private CardView cv;
    private PieChartFragment pf;
    private BarChartFragment prevResultsBars;
    private String userId;
    private FirebaseFirestore dbc;
    private CollectionReference collRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_results);
        db.openDataBase(PreviousResultsActivity.this);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.prevResultsTitle);
        ab.setDisplayHomeAsUpEnabled(true);
        cv=(CardView)findViewById(R.id.dayResultCard);
        cv.setVisibility(View.GONE);
        FragmentManager fm =getSupportFragmentManager();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
         userId= FirebaseAuth.getInstance().getCurrentUser().getUid();

         dbc = FirebaseFirestore.getInstance();
         collRef = dbc.collection("results");

        collRef.addSnapshotListener(PreviousResultsActivity.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                db.openDataBase(PreviousResultsActivity.this);
                if (e != null) {

                    Toast.makeText(PreviousResultsActivity.this, "Listen failed."+ e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
//                    Toast.makeText(context, "Current data: " + snapshot.getDocuments(),
//                            Toast.LENGTH_LONG).show();

                    db.removeAllResults();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Result result = document.toObject(Result.class);
                        result.setId(document.getId());
                        db.createResult(result);
                    }


                    if(db.getAllResults(userId,7).isEmpty()){
                        FragmentManager fm2 =getSupportFragmentManager();
                        NoResultsFragment noResults = new NoResultsFragment();
                        fm2.beginTransaction()
                                .replace(R.id.barChartFragment, noResults)
                                .commit();
                    }else{
                        FragmentManager fm3 =getSupportFragmentManager();
                        BarChartFragment newPrevResultsBars=new BarChartFragment();
                        fm3.beginTransaction()
                                .replace(R.id.barChartFragment, newPrevResultsBars)
                                .commit();
                    }
                    onNothingSelected(prevResultsBars);

                } else {

                    db.removeAllResults();
                    if(db.getAllResults(userId,7).isEmpty()){
                        FragmentManager fm4 =getSupportFragmentManager();
                        NoResultsFragment noResults = new NoResultsFragment();
                        fm4.beginTransaction()
                                .replace(R.id.barChartFragment, noResults)
                                .commit();
                    }else{
                        FragmentManager fm5 =getSupportFragmentManager();
                        BarChartFragment newPrevResultsBars=new BarChartFragment();
                        fm5.beginTransaction()
                                .replace(R.id.barChartFragment, newPrevResultsBars)
                                .commit();
                    }
                    onNothingSelected(prevResultsBars);
                }
                db.closeDataBase();
            }
        });

        db.openDataBase(this);
        if(db.getAllResults(userId,7).isEmpty()){
            NoResultsFragment noResults = new NoResultsFragment();
            fm.beginTransaction()
                    .replace(R.id.barChartFragment, noResults)
                    .commit();
        }else{
            prevResultsBars=new BarChartFragment();
            fm.beginTransaction()
                    .replace(R.id.barChartFragment, prevResultsBars)
                    .commit();
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

    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
//        if(db.getAllResults(userId,7).isEmpty()){
//            FragmentManager fm4 =getSupportFragmentManager();
//            NoResultsFragment noResults = new NoResultsFragment();
//            fm4.beginTransaction()
//                    .replace(R.id.barChartFragment, noResults)
//                    .commit();
//        }else{
//            FragmentManager fm5 =getSupportFragmentManager();
//            BarChartFragment newPrevResultsBars=new BarChartFragment();
//            fm5.beginTransaction()
//                    .replace(R.id.barChartFragment, newPrevResultsBars)
//                    .commit();
//        }
//        onNothingSelected(prevResultsBars);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }

    @Override
    public void onResultSelected(BarChartFragment prevFragment, String resultId) {
        cv.setVisibility(View.GONE);
        Bundle argsPie =new Bundle();
        argsPie.putString("resultId",resultId);
        PieChartFragment pieFragment=new PieChartFragment();
        pieFragment.setArguments(argsPie);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.pieChartPrevFragment, pieFragment)
                 .commit();
        cv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(BarChartFragment prevFragment) {
        cv.setVisibility(View.GONE);
    }
}