package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSettingsActivity extends AppCompatActivity {
    private Context context;
    private CustomListAdapter adapter;
    private TextView name;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsettings);
        name = (TextView) findViewById(R.id.nameset);
        email = (TextView) findViewById(R.id.emailaccount);
        context = this;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
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


}
