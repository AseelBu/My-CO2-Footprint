package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NoResultsFragment extends Fragment {

    private Button newEntryBtn;

    public NoResultsFragment() {
        // Required empty public constructor
    }

    public static NoResultsFragment newInstance(String param1, String param2) {
        NoResultsFragment fragment = new NoResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_no_results, container, false);
         newEntryBtn=(Button) view.findViewById(R.id.prevNewEntryBtn);
         newEntryBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), EntryActivity.class);
                 startActivity(intent);
                getActivity().finish();
             }
         });
        return view;
    }
}