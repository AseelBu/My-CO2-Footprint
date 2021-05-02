package com.androidcourse.energyconsumptiondiary_androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;


public class PointsFragment extends Fragment {

    private DataHolder dh = DataHolder.getInstance();

    private static final String SPF_NAME="AppData"; //shared preference file name
    private User  user = null;

    private TextView pointsTxt ;
    private TextView rankTxt;

    private SharedPreferences prefs = null;
    private Context context = null;

    public PointsFragment() {
        // Required empty public constructor
    }


    public static PointsFragment newInstance(String param1, String param2) {
        PointsFragment fragment = new PointsFragment();

        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_points, container, false);
        pointsTxt = (TextView) view.findViewById(R.id.pointsValue);
        rankTxt = (TextView) view.findViewById(R.id.rankValue);
        setData();
        return view;
    }

    public void setData(){
        int userId=prefs.getInt(getResources().getString(R.string.prefLoggedUser), -1);;
        // get logged user

        this.user=dh.getUserById(userId);

        if (user != null){
            //get logged user points and rank
            int points = user.getPoints();
            String rank = dh.getUserRank(userId);

            // update view
            pointsTxt.setText(String.valueOf(points));
            rankTxt.setText(rank);

        }else{
            // TODO error messsage
        }
    }
}