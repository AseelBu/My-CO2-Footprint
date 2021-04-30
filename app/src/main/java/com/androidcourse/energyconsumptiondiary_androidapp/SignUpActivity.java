package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;
public class SignUpActivity extends AppCompatActivity{
    public static final String TAG = "SignUpActivity";
    ArrayList<User> users=new ArrayList<User>();
    public EditText firstName= null;
    public EditText lastName= null;
    public EditText email=null;
    public EditText password= null;
    public EditText confirmPassword= null;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        firstName=(EditText)findViewById(R.id.editFirstName);
        firstName.setText("George");
        lastName=(EditText)findViewById(R.id.editLastName);
        firstName.setText("klien");
        email=(EditText)findViewById(R.id.email);
        email.setText("George_Klien@gmail.com");
        password=(EditText)findViewById(R.id.password);
        password.setText("12345678");
        confirmPassword=(EditText)findViewById(R.id.editPasswordSignup2);
        confirmPassword.setText("12345678");
    }
    public void signupClicked(View v){

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }

    public void AlreadyAMemberClicked(View v){

        Intent intent = new Intent(context, LogInActivity.class);
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

    public boolean checkIfTwoPasswordIsEquals()
    {
           return password.equals(confirmPassword);

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
