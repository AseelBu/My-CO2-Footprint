package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.EntryRecyclerAdapter;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EntryDataFragment extends Fragment {

    private static final String ENTRY_TYPE="entryType";

//    private DataHolder dh = DataHolder.getInstance();
    private MyCo2FootprintManager db =MyCo2FootprintManager.getInstance();

    private Activity activity;

   private ImpactType type;
   private Co2Impacter data;
    private ArrayList <TypeEntry> entries=new ArrayList<>();

    EntryRecyclerAdapter eAdapter=null;


   private TextView title;

    EntryDataFragmentListener mListener;


    public EntryDataFragment() {
        // Required empty public constructor

    }

    public ArrayList<TypeEntry> getEntries() {
        entries=new ArrayList<>(eAdapter.getEntries());
        return entries;
    }

    public static EntryDataFragment newInstance(ImpactType entryType) {

        EntryDataFragment fragment = new EntryDataFragment();

        Bundle args = new Bundle();
        args.putString(ENTRY_TYPE, entryType.toString());
        fragment.setArguments(args);
        return fragment;
    }

    // Override the Fragment.onAttach() method to instantiate the MyAlertDialogFragmentListener
    @Override
    public void onAttach(Activity activity) {
        Log.i("EntryFragment","entering On attach view");
        super.onAttach(activity);
        this.activity = activity;
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the MyAlertDialogFragmentListener so we can send events to the host
            mListener = (EntryDataFragmentListener) activity;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("EntryFragment","entering On create view");
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_entry_data, container, false);
       title=v.findViewById(R.id.entryTitle);
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.impactersList);

        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        //set adapter for recycler
        if (getArguments() != null) {

            type = ImpactType.valueOf(getArguments().getString(ENTRY_TYPE));


            //add data to recycler depending on Entry Type
            eAdapter = new EntryRecyclerAdapter(this.getContext(),db.getImpactersByType(type),type);
            switch (type) {
                case TRANSPORTATIOIN:
                    title.setText(getString(R.string.transportationTitle));
                    break;
                case FOOD:
//                    eAdapter = new EntryRecyclerAdapter(this.getContext(),db.getAllFood(),type);
                    title.setText(getString(R.string.foodTitle));

                    break;
                case ELECTRICAL:
//                    eAdapter = new EntryRecyclerAdapter(this.getContext(),db.getAllElectrics(),type);
                    title.setText(getString(R.string.electricsTitle));

                    break;
                case SERVICES:
//                    eAdapter = new EntryRecyclerAdapter(this.getContext(),db.getAllServices(),type);
                    title.setText(getString(R.string.servicesTitle));

                    break;
            }
            recList.setAdapter(eAdapter);
        }
//        nextBtn = (Button) v.findViewById(R.id.nextBtn);
//
//        backBtn = (Button) v.findViewById(R.id.backBtn);
       return v;
    }


    public void nextClicked(){
       ArrayList <TypeEntry> data=new ArrayList<>(eAdapter.getEntries());
       data.add(new TypeEntry(1,1,type));

        mListener.onFragmentNextClick(EntryDataFragment.this,data);
    }


}