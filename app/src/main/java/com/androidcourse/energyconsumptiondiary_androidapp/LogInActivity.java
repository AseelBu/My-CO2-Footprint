package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    public EditText email;
    public EditText password;
    TextToSpeech t1;
    private DataHolder dh = DataHolder.getInstance();
    private FirebaseAuth mAuth;
    private Context context;
//    private SharedPreferences prefs;
    private View.OnClickListener singInUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            hideKeyboard();
            String email2 = email.getText().toString();
            String password2 = password.getText().toString();
            //TODO no need for check
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


//        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = (EditText) findViewById(R.id.email222);
        email.setText("admin@gmail.com");
        password = (EditText) findViewById(R.id.oldpassword);
        password.setText("Admin12");
        TextView forgetPassword = (TextView) findViewById(R.id.forgotPass);
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
//        if (flag == true) {
//            User user = checkIfEmailExist();
//            //if the email not found
//            if (user == null) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//                dlgAlert.setMessage("Unfortunately Email Not Found ):");
//                dlgAlert.setTitle("Message...");
//                dlgAlert.setPositiveButton("OK", null);
//                dlgAlert.setCancelable(true);
//                dlgAlert.create().show();
//            } else {
//                //if the password is not correct
//                if (checkIfPasswordIsCorrect() == false) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//                    dlgAlert.setMessage("Wrong Password,try again :)");
//                    dlgAlert.setTitle("Message...");
//                    dlgAlert.setPositiveButton("OK", null);
//                    dlgAlert.setCancelable(true);
//                    dlgAlert.create().show();
//                }
////                else {
////                    SharedPreferences.Editor editor = prefs.edit();
////                    editor.putInt(getResources().getString(R.string.prefLoggedUser), user.getUserId());
////                    if (editor.commit()) {
////                        Log.i(TAG, getClass().getSimpleName() + "logged user was save to memory");
////                    }
////                    if(email.getText().toString().equals("Admin@gmail.com")) {
////                        Intent intent = new Intent(context, HomePageActivity.class);
////                        intent.putExtra("Admin",true);
////                        String toSpeak = "Welcome Admin";

////                        startActivity(intent);
////                        finish();
////                    }
////                    else
////                    {
////                        Intent intent = new Intent(context, HomePageActivity.class);
////                        intent.putExtra("Admin",false);
////                        String toSpeak = "Welcome";
////                        startActivity(intent);
////                        finish();
////                    }
//                }
//            }
//        }
        return flag;
    }

    //eneter to Forget Password activity
    public void ForgetPasswordClicked(View v) {
        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    //enter to sign up activity
    public void SignUpClicked(View v) {
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);
    }

    //check If Email is Exist
    //TODO no need
    public User checkIfEmailExist() {
        for (User u : dh.getUsers()) {
            if (email.getText().toString().equals(u.getEmail().toString()))
                return u;
        }
        return null;
    }

    //TODO no need
    //check If Password Is Correct
    public boolean checkIfPasswordIsCorrect() {
        for (User u : dh.getUsers()) {
            if ((email.getText().toString().equals(u.getEmail())) &&
                    (!password.getText().toString().equals(u.getPassword())))
                return false;
        }
        return true;
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
