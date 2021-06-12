package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    public Co2Impacter impacter;
    public ImpactType impacterType;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MyCo2FootprintManager mg = MyCo2FootprintManager.getInstance();
    private Context context;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        MyCo2FootprintManager.getInstance().openDataBase(this);
    }

    public static Context getContext() {

        return getContext();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            mg.openDataBase(this);
            Intent intent = new Intent(this, HomePageActivity.class);
            if (user.getEmail().toLowerCase().equals("admin@gmail.com")) {
                intent.putExtra("Admin", true);
            } else {
                intent.putExtra("Admin", false);
            }
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(context, LogInActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    @Override
    protected void onResume() {
        mg.openDataBase(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mg.closeDataBase();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}