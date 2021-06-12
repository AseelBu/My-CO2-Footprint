package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PointsFragment extends Fragment {

    private static final String SPF_NAME = "AppData"; //shared preference file name
    //    private DataHolder dh = DataHolder.getInstance();
    private MyCo2FootprintManager dbMngr = MyCo2FootprintManager.getInstance();
    private User user = null;

    private TextView pointsTxt;
    private TextView rankTxt;
    //    private SharedPreferences prefs = null;
    private Context context = null;
    private FirebaseUser fUser;

    public PointsFragment() {
        // Required empty public constructor
    }

    public static PointsFragment newInstance() {
        PointsFragment fragment = new PointsFragment();


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
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        user = dbMngr.getUserInfoPoints(fUser.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_points, container, false);
        pointsTxt = (TextView) view.findViewById(R.id.pointsValue);
        rankTxt = (TextView) view.findViewById(R.id.rankValue);
        setData();
        return view;
    }

    public void setData() {
        if (user != null) {
            //get logged user points and rank
            int points = user.getPoints();

            String rank = user.getUserRank();
            // update view
            pointsTxt.setText(String.valueOf(points));
            rankTxt.setText(rank);

        } else {
            // TODO error messsage
        }
    }

    public void updatePointsAndRank() {
        user = dbMngr.getUserInfoPoints(fUser.getUid());
        if (user != null) {
            int points = user.getPoints();
            String rank = user.getUserRank();
            // update view
            pointsTxt.setText(String.valueOf(points));
            rankTxt.setText(rank);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePointsAndRank();
    }
}