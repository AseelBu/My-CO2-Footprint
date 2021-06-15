package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.MyLeaderboardRecyclerViewAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;


public class LeaderboardFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private MyCo2FootprintManager dbMngr=MyCo2FootprintManager.getInstance();
    private int mColumnCount = 1;
    private Context context;
    private MyLeaderboardRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public LeaderboardFragment() {
    }

    public static LeaderboardFragment newInstance(int columnCount) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbMngr.openDataBase(context);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.leaderboardRec);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter=new MyLeaderboardRecyclerViewAdapter(this.getContext(),dbMngr.getTopkUsers(10));
            recyclerView.setAdapter(adapter);

        return view;
    }


    public void updateLeaderBoard(){
        adapter=new MyLeaderboardRecyclerViewAdapter(this.getContext(),dbMngr.getTopkUsers(10));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        dbMngr.openDataBase(context);
        super.onResume();
    }

    @Override
    public void onPause() {
        dbMngr.closeDataBase();
        super.onPause();
    }
}