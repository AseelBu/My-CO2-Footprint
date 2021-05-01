package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    private DataHolder dh = DataHolder.getInstance();
    public EditText email = null;
    public EditText password = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email=(EditText)findViewById(R.id.email);
        email.setText("Admin@gmail.com");
        password=(EditText)findViewById(R.id.oldpassword);
        password.setText("Admin");
        TextView forgetPassword = (TextView) findViewById(R.id.forgotPass);
        Button login = (Button) findViewById(R.id.sendEmail);
        TextView signUp = (TextView) findViewById(R.id.loginLink);
        context=this;

    }
    public void loginClicked(View v) {
        boolean flag = true;

        try {
            if (TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString()))
             {
                flag = false;
//                Log.d("email:", "something is empty");
                Toast.makeText(LogInActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {

        }
//
        if(flag==true)
        {
        if (checkIfEmailExist() == false) {

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

            dlgAlert.setMessage("Unfortenatlly Email Not Found ):");
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();

        } else {
            if (checkIfPasswordIsCorrect() == false) {

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                dlgAlert.setMessage("Wrong Password,try again :)");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }  else {
                Intent intent = new Intent(context, HomePageActivity.class);
                startActivity(intent);

            }
            }
    }

    }

    public void ForgetPasswordClicked(View v){

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        startActivity(intent);

    }
    public void SignUpClicked(View v){
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);

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
        for (User u: dh.getUsers()) {
            if(email.getText().toString().equals(u.getEmail().toString()))
                return true;
        }
        return false;
    }

    public boolean checkIfPasswordIsCorrect()
    {
//        Log.d("44444444444444","$44444444444444");
        for (User u: dh.getUsers()) {
//            Log.d("1:",email.getText().toString());
//            Log.d("2:",u.getEmail());
//            Log.d("1:",password.getText().toString());
//            Log.d("2",u.getPassword());
          if((email.getText().toString().equals(u.getEmail()))&&
                  (!password.getText().toString().equals(u.getPassword())))
              return false;
        }
         return true;
    }





}
