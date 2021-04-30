package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    ArrayList<User> users=new ArrayList<User>();
    public EditText email = null;
    public EditText password = null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email=(EditText)findViewById(R.id.email);
        email.setText("George@gmail.com");
        password=(EditText)findViewById(R.id.password);
        password.setText("12345678");
        TextView forgetPassword = (TextView) findViewById(R.id.forgotPass);
        Button login = (Button) findViewById(R.id.sendEmail);
        TextView signUp = (TextView) findViewById(R.id.loginLink);

    }
    public void loginClicked(View v){

        Intent intent = new Intent(context, HomePageActivity.class);
        startActivity(intent);

    }

    public void ForgetPasswordClicked(View v){

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        startActivity(intent);

    }
    public void SignUpClicked(View v){
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);

    }
    public boolean checkIfEmailisValid()
    {
        String emailString=null;
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
            if(email.equals(u.getEmail()))
                return true;
        }
        return false;
    }

    public boolean checkIfPasswordIsCorrect()
    {
        for (User u: users) {
          if((email.equals(u.getEmail()))&&(!password.equals(u.getPassword())))
              return false;
        }
         return true;
    }





}
