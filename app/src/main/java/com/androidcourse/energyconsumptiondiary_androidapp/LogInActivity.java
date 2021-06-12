package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    public EditText email;
    public EditText password;
    TextToSpeech t1;
    private FirebaseAuth mAuth;
    private Context context;
//    private SharedPreferences prefs;
    private View.OnClickListener singInUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email2 = email.getText().toString();
            String password2 = password.getText().toString();
            if (check() == true) {
                mAuth.signInWithEmailAndPassword(email2, password2)
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {

                                    final Snackbar bar = Snackbar.make(v, task.getException().getMessage(), Snackbar.LENGTH_INDEFINITE);
                                    bar.setAction("Dismiss", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bar.dismiss();
                                        }
                                    });
                                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                                    bar.show();

                                }

                            }
                        });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email = (EditText) findViewById(R.id.email222);
        email.setText("admin@gmail.com");
        password = (EditText) findViewById(R.id.oldpassword);
        password.setText("Admin12");
        Button login = (Button) findViewById(R.id.edititem2);
        TextView signUp = (TextView) findViewById(R.id.loginLink);
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        context = this;
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(singInUserListener);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            MyCo2FootprintManager.getInstance().openDataBase(this);
            Intent intent = new Intent(this, HomePageActivity.class);
            if (user.getEmail().toLowerCase().equals("admin@gmail.com")) {
                intent.putExtra("Admin", true);
            } else {
                intent.putExtra("Admin", false);
            }
            startActivity(intent);
            finish();
        }
    }

    public boolean check() {
        boolean flag = true;
        try {
            //if the user dosn't enter all the details
            if (TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString())) {
                flag = false;
                String toSpeak = "Please enter all details";
                View parentLayout = findViewById(android.R.id.content);
                final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
                bar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bar.dismiss();
                    }
                });
                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
                bar.show();
            }
        } catch (NumberFormatException exception) {
        }

        return flag;
    }

    //enter to sign up activity
    public void SignUpClicked(View v) {
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }
}
