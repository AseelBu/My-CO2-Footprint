package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LeaderboardAndPointsActivity extends AppCompatActivity {
      private MyCo2FootprintManager dbMngr=MyCo2FootprintManager.getInstance();
      private Context context;
      private LeaderboardFragment leaderboardFragment;
      private PointsFragment pointsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_and_points);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        context=this;
        dbMngr=MyCo2FootprintManager.getInstance();
        dbMngr.openDataBase(LeaderboardAndPointsActivity.this);
        leaderboardFragment=LeaderboardFragment.newInstance(1);
        pointsFragment=PointsFragment.newInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("users");

        collRef.addSnapshotListener(LeaderboardAndPointsActivity.this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                dbMngr.openDataBase(LeaderboardAndPointsActivity.this);
                if (e != null) {

                    Toast.makeText(context, "Listen failed."+ e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    dbMngr.removeAllUsers();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        User user = document.toObject(User.class);
                        user.setUserId(document.getId());
                        dbMngr.replaceUserPoints(user);
                    }
                    pointsFragment.updatePointsAndRank();
                    leaderboardFragment.updateLeaderBoard();
                } else {
                    Toast.makeText(context, "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
                dbMngr.closeDataBase();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.leaderboardFrame, leaderboardFragment)
                .replace(R.id.pointsFrame,pointsFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbMngr.openDataBase(this);
//        pointsFragment.updateRank();
//        leaderboardFragment.updateLeaderBoard();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbMngr.closeDataBase();

    }
}