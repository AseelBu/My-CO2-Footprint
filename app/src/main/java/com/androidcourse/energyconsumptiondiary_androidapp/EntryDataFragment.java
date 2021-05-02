package com.androidcourse.energyconsumptiondiary_androidapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.EntryRecyclerAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.TipAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDataFragment extends Fragment {


DataHolder dh = DataHolder.getInstance();
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
    public static EntryDataFragment newInstance(String entryType) {
        EntryDataFragment fragment = new EntryDataFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recList = (RecyclerView) getActivity().findViewById(R.id.impactersList);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        createList(10);


//        if (getArguments() != null) {

//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        EntryRecyclerAdapter ea = new EntryRecyclerAdapter(dh.getTransportation());
        recList.setAdapter(ea);
//        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_data, container, false);
    }
    private void createList(int i) {
    }
}