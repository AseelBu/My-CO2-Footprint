package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.EntryRecyclerAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TipAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.CO2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDataFragment extends Fragment {

    private static final String ENTRY_TYPE="entryType";

    private DataHolder dh = DataHolder.getInstance();
    private Activity activity;

   private ImpactType type;
   private CO2Impacter data;


   private Button nextBtn;
   private Button backBtn;

    EntryDataFragmentListener mListener;


    public EntryDataFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EntryDataFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recList = (RecyclerView) getActivity().findViewById(R.id.impactersList);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        createList(10);


        if (getArguments() != null) {

            type = ImpactType.valueOf(getArguments().getString(ENTRY_TYPE));
            EntryRecyclerAdapter ea=null;
            switch (type) {
                case TRANSPORTATIOIN:
                 ea = new EntryRecyclerAdapter(dh.getTransportation());
                recList.setAdapter(ea);
                break;
                case FOOD:
                   ea = new EntryRecyclerAdapter(dh.getFood());
                    recList.setAdapter(ea);
                    break;
                case ELECTRICAL:
                 ea = new EntryRecyclerAdapter(dh.getElectricals());
                    recList.setAdapter(ea);
                    break;
                case SERVICES:
                     ea = new EntryRecyclerAdapter(dh.getServices());
                    recList.setAdapter(ea);
                    break;
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_entry_data, container, false);
        nextBtn = (Button) v.findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nextClicked();
            }
        });
//        backBtn = (Button) v.findViewById(R.id.backBtn);
       return v;
    }
    private void createList(int i) {

    }

    public void nextClicked(){
       ArrayList <TypeEntry> data=new ArrayList<>();
       data.add(new TypeEntry(1,1,type));

        mListener.onFragmentNextClick(EntryDataFragment.this,data);
    }
}