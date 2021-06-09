package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    public EditText firstName = null;
    public EditText lastName = null;
    public EditText txtEmail = null;
    public EditText txtPassword;
    public EditText confirmPassword;
    public Button signupBtn;
    private Context context;
    private FirebaseAuth mAuth;
    private DataHolder dh = DataHolder.getInstance();
    ArrayList<User> users = dh.getUsers();
    private FirebaseFirestore db;
    private View.OnClickListener createNewAccountListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = txtEmail.getText().toString();
            String password = txtPassword.getText().toString();
            String fullName = firstName.getText().toString() + " " + lastName.getText().toString();
            if (check() == true) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//
//                                    Toast.makeText(SignUpActivity.this, "save successfully!",
//                                            Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    saveUserDisplayName(v,fullName);

                                } else {

                                    // If sign in fails, display a message to the user.
                                    //TODO add snackbar
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    //updateUI(null);
                                }

                            }
                        });
            } else {
                //TODO what if not valid sign up
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.signup);
        firstName = (EditText) findViewById(R.id.type);
        lastName = (EditText) findViewById(R.id.fuel3);
        signupBtn = (Button) findViewById(R.id.edititem2);
        txtEmail = (EditText) findViewById(R.id.email222);
        txtPassword = (EditText) findViewById(R.id.oldpassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        context = this;
        getSupportActionBar().hide();
        signupBtn.setOnClickListener(createNewAccountListner);

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            MyCo2FootprintManager.getInstance().openDataBase(this);
            mAuth.signOut();
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void saveUserDisplayName(View v,String fullName) {
        FirebaseUser user = mAuth.getCurrentUser();
        //add user name to fireAuth
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName).build();

        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()) {


                    //TODO remove
                    Toast.makeText(SignUpActivity.this, "saved user name",
                            Toast.LENGTH_SHORT).show();
                    saveUserToFireStore(v,fullName);

                } else {
                    //TODO add snackbar
                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void saveUserToFireStore(View v,String fullName) {
        FirebaseUser fUser = mAuth.getCurrentUser();
        Map<String, Object> user = new HashMap<>();

        user.put("name", fullName);
        user.put("email", fUser.getEmail());
        user.put("is admin", false);
        user.put("points", 0);


        db.collection("users").document(fUser.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //TODO snackbar
                        final Snackbar bar = Snackbar.make(v, "congratulations,You are now a new member!", Snackbar.LENGTH_LONG);
                        bar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bar.dismiss();
                            }
                        });
                        bar.setActionTextColor(getResources().getColor(R.color.bestGreen));
                        bar.show();
//                        Toast.makeText(SignUpActivity.this,
//                                "congratulations,You are now a new member!",
//                                Toast.LENGTH_LONG).show();
                        updateUI(fUser);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //TODO snackbar
                        Toast.makeText(context, "Error writing document " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

    }
//    //return to the login activity if the user already member
//    public void AlreadyAMemberClicked(View v){
//
//        Intent intent = new Intent(context, LogInActivity.class);
//        startActivity(intent);
//
//    }

    //check If Email is Valid method
    public boolean checkIfEmailIsValid() {
        String emailString;
        emailString = txtEmail.getText().toString();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (emailString == null)
            return false;
        return pat.matcher(emailString).matches();
    }

    //TODO no need output Firebase massage to snack bar
    //Check If Email Exist method
    public boolean checkIfEmailExist() {
        for (User u : users) {
            if (txtEmail.getText().toString().equals(u.getEmail().toString()))
                return true;
        }
        return false;
    }


    //check If Two Password Is Equals
    public boolean checkIfTwoPasswordIsEquals() {
        return txtPassword.getText().toString().equals(confirmPassword.getText().toString());
    }

    public boolean check() {
        boolean flag = true;

            //if some if four inputs in empty
            if (TextUtils.isEmpty(firstName.getText().toString()) ||
                    TextUtils.isEmpty(lastName.getText().toString()) ||
                    TextUtils.isEmpty(txtEmail.getText().toString()) ||
                    TextUtils.isEmpty(txtPassword.getText().toString()) ||
                    TextUtils.isEmpty(confirmPassword.getText().toString())
            ) {
                flag = false;
                Toast.makeText(SignUpActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        if (flag == true) {
            //flag=lenOfPassword();
            //if the email not exists and email is valid,two password equals as add new user and show a toast
            if (checkIfEmailIsValid() == true && checkIfTwoPasswordIsEquals() == true ) {
                dh.addUser(dh.getUsers().size() + 1, firstName.getText().toString(), lastName.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());

                return true;
            } else {
                //if email not valid
                if (checkIfEmailIsValid() == false) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                    dlgAlert.setMessage("Email must be like Email@email.com");
                    dlgAlert.setTitle("Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    flag=false;
                } else {
                    //if email is already exist
                        //if the two passwords are not the same
                        if (checkIfTwoPasswordIsEquals() == false) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                            dlgAlert.setMessage("The two passwords are not the same!");
                            dlgAlert.setTitle("Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                            flag=false;
                        }
                    }
                }
            }


        return flag;
    }


    public boolean lenOfPassword() {

        if (txtPassword.getText().toString().length() < 6 || confirmPassword.getText().toString().length() < 6) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("password should be 6 characters/numbers at least(:");
            dlgAlert.setTitle("Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return false;
        }
        return true;
    }
}
//sign up to the app
//    public void signupClicked(View v) {
//        boolean flag = true;
//        try {
//            //if some if four inputs in empty
//            if (TextUtils.isEmpty(firstName.getText().toString()) ||
//                    TextUtils.isEmpty(lastName.getText().toString()) ||
//                    TextUtils.isEmpty(txtEmail.getText().toString()) ||
//                    TextUtils.isEmpty(txtPassword.getText().toString()) ||
//                    TextUtils.isEmpty(confirmPassword.getText().toString())
//            ) {
//                flag = false;
//                Toast.makeText(SignUpActivity.this,
//                        "Please enter all details",
//                        Toast.LENGTH_SHORT).show();
//            }
//        } catch (NumberFormatException exception) {
//        }
//        if (flag == true) {
//            //if the txtEmail not exists and txtEmail is valid,two txtPassword equals as add new user and show a toast
//            if (checkIfEmailExist() == false && checkIfEmailisValid() == true && checkIfTwoPasswordIsEquals() == true) {
//                dh.addUser(dh.getUsers().size()+1,firstName.getText().toString(), lastName.getText().toString(),txtEmail.getText().toString(), txtPassword.getText().toString());
//                Toast.makeText(SignUpActivity.this,
//                        "congratulations,You now a new member (: ",
//                        Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, LogInActivity.class);
//                startActivity(intent);
//            } else {
//                //if txtEmail not valid
//                if (checkIfEmailisValid() == false) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//
//                    dlgAlert.setMessage("Email must be like Email@txtEmail.com");
//                    dlgAlert.setTitle("Message...");
//                    dlgAlert.setPositiveButton("OK", null);
//                    dlgAlert.setCancelable(true);
//                    dlgAlert.create().show();
//                } else {
//                    //if txtEmail is already exist
//                    if (checkIfEmailExist() == true) {
//                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//
//                        dlgAlert.setMessage("Email Alraedy Exists");
//                        dlgAlert.setTitle("Message...");
//                        dlgAlert.setPositiveButton("OK", null);
//                        dlgAlert.setCancelable(true);
//                        dlgAlert.create().show();
//                    } else {
//                        //if the two passwords are not the same
//                        if (checkIfTwoPasswordIsEquals() == false) {
//                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//
//                            dlgAlert.setMessage("The two passwords are not the same!");
//                            dlgAlert.setTitle("Message...");
//                            dlgAlert.setPositiveButton("OK", null);
//                            dlgAlert.setCancelable(true);
//                            dlgAlert.create().show();
//                        }
//                    }
//                }
//            }
//        }
//    }