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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.regex.Pattern;
public class SignUpActivity extends AppCompatActivity{
    public static final String TAG = "SignUpActivity";
    public EditText firstName= null;
    public EditText lastName= null;
    public EditText email=null;
    public EditText password;
    public EditText confirmPassword;
    private Context context;
    private FirebaseAuth mAuth;
    public Button signup;
    private DataHolder dh = DataHolder.getInstance();
    ArrayList<User> users=dh.getUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.signup);
        firstName=(EditText)findViewById(R.id.type);
        lastName=(EditText)findViewById(R.id.fuel3);
        signup=(Button) findViewById(R.id.edititem2);
        email=(EditText)findViewById(R.id.email222);
        password=(EditText)findViewById(R.id.oldpassword);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        context=this;
        getSupportActionBar().hide();
        signup.setOnClickListener(createNewAccountListner);

    }
    //sign up to the app
//    public void signupClicked(View v) {
//        boolean flag = true;
//        try {
//            //if some if four inputs in empty
//            if (TextUtils.isEmpty(firstName.getText().toString()) ||
//                    TextUtils.isEmpty(lastName.getText().toString()) ||
//                    TextUtils.isEmpty(email.getText().toString()) ||
//                    TextUtils.isEmpty(password.getText().toString()) ||
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
//            //if the email not exists and email is valid,two password equals as add new user and show a toast
//            if (checkIfEmailExist() == false && checkIfEmailisValid() == true && checkIfTwoPasswordIsEquals() == true) {
//                dh.addUser(dh.getUsers().size()+1,firstName.getText().toString(), lastName.getText().toString(),email.getText().toString(), password.getText().toString());
//                Toast.makeText(SignUpActivity.this,
//                        "congratulations,You now a new member (: ",
//                        Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, LogInActivity.class);
//                startActivity(intent);
//            } else {
//                //if email not valid
//                if (checkIfEmailisValid() == false) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//
//                    dlgAlert.setMessage("Email must be like Email@email.com");
//                    dlgAlert.setTitle("Message...");
//                    dlgAlert.setPositiveButton("OK", null);
//                    dlgAlert.setCancelable(true);
//                    dlgAlert.create().show();
//                } else {
//                    //if email is already exist
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

    private View.OnClickListener createNewAccountListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email2 = email.getText().toString();
            String password2 = password.getText().toString();
            mAuth.createUserWithEmailAndPassword(email2, password2)
                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "save successfully!",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignUpActivity.this, "something wrong!",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                        }
                    });
        }
    };

    private void updateUI(FirebaseUser user) {
        if(user!=null){
            MyCo2FootprintManager.getInstance().openDataBase(this);
            Intent intent = new Intent(this, LogInActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
    }
    //return to the login activity if the user already member
    public void AlreadyAMemberClicked(View v){

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }
     //check If Email is Valid method
    public boolean checkIfEmailisValid()
    {
        String emailString;
        emailString=email.getText().toString();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if ( emailString == null)
            return false;
        return pat.matcher( emailString).matches();
    }
      //Check If Email Exist method
    public boolean checkIfEmailExist()
    {
        for (User u: users) {
            if(email.getText().toString().equals(u.getEmail().toString()))
                return true;
        }
        return false;
    }
     //check If Two Password Is Equals
    public boolean checkIfTwoPasswordIsEquals()
    {
           return password.getText().toString().equals(confirmPassword.getText().toString());
    }
}
