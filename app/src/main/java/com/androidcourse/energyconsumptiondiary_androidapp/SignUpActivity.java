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
        firstName=(EditText)findViewById(R.id.type);
        lastName=(EditText)findViewById(R.id.fuel3);
        email=(EditText)findViewById(R.id.co2Amount);
        password=(EditText)findViewById(R.id.oldpassword);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        context=this;
        getSupportActionBar().hide();
    }
    //sign up to the app
    public void signupClicked(View v) {
        boolean flag = true;
        try {
            //if some if four inputs in empty
            if (TextUtils.isEmpty(firstName.getText().toString()) ||
                    TextUtils.isEmpty(lastName.getText().toString()) ||
                    TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString()) ||
                    TextUtils.isEmpty(confirmPassword.getText().toString())
            ) {
                flag = false;
                Toast.makeText(SignUpActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        if (flag == true) {
            //if the email not exists and email is valid,two password equals as add new user and show a toast
            if (checkIfEmailExist() == false && checkIfEmailisValid() == true && checkIfTwoPasswordIsEquals() == true) {
                dh.addUser(dh.getUsers().size()+1,firstName.getText().toString(), lastName.getText().toString(),email.getText().toString(), password.getText().toString());
                Toast.makeText(SignUpActivity.this,
                        "congratulations,You now a new member (: ",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LogInActivity.class);
                startActivity(intent);
            } else {
                //if email not valid
                if (checkIfEmailisValid() == false) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                    dlgAlert.setMessage("Email must be like Email@email.com");
                    dlgAlert.setTitle("Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                } else {
                    //if email is already exist
                    if (checkIfEmailExist() == true) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                        dlgAlert.setMessage("Email Alraedy Exists");
                        dlgAlert.setTitle("Message...");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    } else {
                        //if the two passwords are not the same
                        if (checkIfTwoPasswordIsEquals() == false) {
                            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                            dlgAlert.setMessage("The two passwords are not the same!");
                            dlgAlert.setTitle("Message...");
                            dlgAlert.setPositiveButton("OK", null);
                            dlgAlert.setCancelable(true);
                            dlgAlert.create().show();
                        }
                    }
                }
            }
        }
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
