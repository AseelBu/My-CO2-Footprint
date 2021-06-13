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
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.EntryRecyclerAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import java.util.ArrayList;
import java.util.Map;

public class EntryDataFragment extends Fragment {
    private static final String ENTRY_TYPE = "entryType";
    EntryRecyclerAdapter eAdapter = null;

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
            eAdapter = new EntryRecyclerAdapter(getActivity(), db.getImpactersByType(type), type);
            recList.setAdapter(eAdapter);
        }
        return v;
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

    public void updateUdapter(){
        eAdapter = new EntryRecyclerAdapter(getActivity(), db.getImpactersByType(type), type);
        recList.setAdapter(eAdapter);
    }
    @Override
    public void onResume() {
        updateUdapter();
        super.onResume();

    }
}