package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

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
    private DataHolder dh = DataHolder.getInstance();
    ArrayList<User> users=dh.getUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firstName=(EditText)findViewById(R.id.editFirstName);
//        firstName.setText("George");
        lastName=(EditText)findViewById(R.id.editLastName);
//        lastName.setText("klien");
        email=(EditText)findViewById(R.id.email);
//        email.setText("George_Klien@gmail.com");
        password=(EditText)findViewById(R.id.oldpassword);
//        password.setText("12345678");
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
//        confirmPassword.setText("12345678");
        context=this;
    }
    public void signupClicked(View v) {
        boolean flag = true;
//          Log.d("firstName:",firstName.getText().toString());
//        Log.d("lastName:",lastName.getText().toString());
//        Log.d("email:",email.getText().toString());
//        Log.d("password:",password.getText().toString());
//        Log.d("confirmPassword:",confirmPassword.getText().toString());

        try {
            if (TextUtils.isEmpty(firstName.getText().toString()) ||
                    TextUtils.isEmpty(lastName.getText().toString()) ||
                    TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString()) ||
                    TextUtils.isEmpty(confirmPassword.getText().toString())
            ) {
                flag = false;
//                Log.d("email:", "something is empty");
                Toast.makeText(SignUpActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
//            Toast.makeText(context, lastName + "something wrong", Toast.LENGTH_SHORT).show();
        }
        if (flag == true) {
            if (checkIfEmailExist() == false && checkIfEmailisValid() == true && checkIfTwoPasswordIsEquals() == true) {
//                Log.d("email:", "yeaaaaaaaaaaaaaaaaaaaaaah");
                dh.addUserDetails(firstName.getText().toString(), lastName.getText().toString(),email.getText().toString(), password.getText().toString());
                Toast.makeText(SignUpActivity.this,
                        "congratulations,You now a new member (: ",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LogInActivity.class);
                startActivity(intent);
            } else {
                if (checkIfEmailisValid() == false) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                    dlgAlert.setMessage("Email must be like Email@email.com");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                } else {
                    if (checkIfEmailExist() == true) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                        dlgAlert.setMessage("Email Alraedy Exists");
                        dlgAlert.setTitle("Error Message...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    } else {
                        if (checkIfTwoPasswordIsEquals() == false) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                            dlgAlert.setMessage("The two passwords are not the same!");
                            dlgAlert.setTitle("Error Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                        }
                    }
                }
            }
        }

    }
    public void AlreadyAMemberClicked(View v){

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }

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
    //    public boolean checkIfPasswordisValid()
//    {
//        String passwordString=null;
//        passwordString=password.getText().toString();
//        if(passwordString.length()<8) return false;
//        return true;
//    }
    public boolean checkIfEmailExist()
    {
        for (User u: users) {
            if(email.getText().toString().equals(u.getEmail().toString()))
                return true;
        }
        return false;
    }

    public boolean checkIfTwoPasswordIsEquals()
    {

           return password.getText().toString().equals(confirmPassword.getText().toString());

    }

    public boolean checkIfFirstNameNotContainNumber()
    {
        String str=firstName.getText().toString();
        for(int i=0; i < str.length(); i++) {
            Boolean flag = Character.isDigit(str.charAt(i));
            if(flag) return false;
        }
        return true;
    }

    public boolean checkIflastNameNotContainNumber()
    {
        String str=firstName.getText().toString();
        for(int i=0; i < str.length(); i++) {
            Boolean flag = Character.isDigit(str.charAt(i));
            if(flag) return false;
        }
        return true;
    }
}
