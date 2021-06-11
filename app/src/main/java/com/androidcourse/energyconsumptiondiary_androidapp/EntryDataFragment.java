package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.EntryRecyclerAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class EntryDataFragment extends Fragment {
    private static final String ENTRY_TYPE = "entryType";
    EntryRecyclerAdapter eAdapter = null;
    EntryDataFragmentListener mListener;
    private MyCo2FootprintManager db = MyCo2FootprintManager.getInstance();
    private Activity activity;
    private ImpactType type;
    private Co2Impacter data;
    private ArrayList<TypeEntry> entries = new ArrayList<>();
    private TextView title;
    private RecyclerView recList;

    public EntryDataFragment() {
    }

    public static EntryDataFragment newInstance(ImpactType entryType) {

        EntryDataFragment fragment = new EntryDataFragment();
        Bundle args = new Bundle();
        args.putString(ENTRY_TYPE, entryType.toString());
        fragment.setArguments(args);
        return fragment;
    }

    public ArrayList<TypeEntry> getEntries() {
        entries = new ArrayList<>(eAdapter.getEntries());
        return entries;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = getActivity();
        try {
            mListener = (EntryDataFragmentListener) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }

        FirebaseFirestore dbCloud = FirebaseFirestore.getInstance();
        CollectionReference collRef = dbCloud.collection("co2 impacter");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    //TODO snackbar
                    Toast.makeText(activity, "Listen failed." + e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
//                    Toast.makeText(context, "Current data: " + snapshot.getDocuments(),
//                            Toast.LENGTH_LONG).show();
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
                        if (impacterType.equals(ImpactType.TRANSPORTATION)) {
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
                        cloudImpacter.setName(name);
                        cloudImpacter.setQuestion(question);
                        cloudImpacter.setUnit(Units.valueOf(unit));
                        cloudImpacter.setUrlImage(urlImage);
                        cloudImpacter.setCo2Amount(co2Amount.intValue());
                        db.createImpacterByType(cloudImpacter, ImpactType.valueOf(impacterType));
                        eAdapter = new EntryRecyclerAdapter(getContext(), db.getImpactersByType(type), type);
                        recList.setAdapter(eAdapter);
                    }

                } else {
                    Toast.makeText(activity, "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entry_data, container, false);
        title = v.findViewById(R.id.entryTitle);
        recList = (RecyclerView) v.findViewById(R.id.impactersList);

        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        //set adapter for recycler
        if (getArguments() != null) {
            type = ImpactType.valueOf(getArguments().getString(ENTRY_TYPE));
            setTitle();
            //add data to recycler depending on Entry Type
            eAdapter = new EntryRecyclerAdapter(this.getContext(), db.getImpactersByType(type), type);
            recList.setAdapter(eAdapter);
        }
        return v;
    }


    public void nextClicked() {
        ArrayList<TypeEntry> data = new ArrayList<>(eAdapter.getEntries());
        data.add(new TypeEntry(1, 1, type));

        mListener.onFragmentNextClick(EntryDataFragment.this, data);
    }

    public void setTitle() {
        switch (type) {
            case TRANSPORTATION:
                title.setText(getString(R.string.transportationTitle));
                break;
            case FOOD:
                title.setText(getString(R.string.foodTitle));
                break;
            case ELECTRICAL:
                title.setText(getString(R.string.electricsTitle));
                break;
            case SERVICES:
                title.setText(getString(R.string.servicesTitle));

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        eAdapter = new EntryRecyclerAdapter(this.getContext(), db.getImpactersByType(type), type);
        recList.setAdapter(eAdapter);
    }
}