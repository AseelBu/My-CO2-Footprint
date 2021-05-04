package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=(EditText)findViewById(R.id.co2Amount);
        email.setText("Admin@gmail.com");
        password=(EditText)findViewById(R.id.oldpassword);
        password.setText("Admin");
        TextView forgetPassword = (TextView) findViewById(R.id.forgotPass);
        Button login = (Button) findViewById(R.id.edititem2);
        TextView signUp = (TextView) findViewById(R.id.loginLink);
        context=this;
    }
    //log in to the home page with check if email and password is correct,valid,and exists
    public void loginClicked(View v) {
        boolean flag = true;
        try {
            //if the user dosn't enter all the details
            if (TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString()))
             {
                flag = false;
                Toast.makeText(LogInActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        if(flag==true){
            User user=checkIfEmailExist();
            //if the email not found
            if (user == null) {
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Unfortunately Email Not Found ):");
                dlgAlert.setTitle("Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            } else {
               //if the password is not correct
                if (checkIfPasswordIsCorrect() == false) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Wrong Password,try again :)");
                    dlgAlert.setTitle("Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }  else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(getResources().getString(R.string.prefLoggedUser), user.getUserId());
                    if(editor.commit()){
                        Log.i(TAG, getClass().getSimpleName() + "logged user was save to memory");
                    }
                    if(email.getText().toString().equals("Admin@gmail.com")) {
                        Intent intent = new Intent(context, AdminHomePageActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(context, HomePageActivity.class);
                        startActivity(intent);
                    }
                }
            }
    }
    }
  //eneter to Forget Password activity
    public void ForgetPasswordClicked(View v){

        Intent intent = new Intent(context, ForgetPasswordActivity.class);
        startActivity(intent);

    }
    //enter to sign up activity
    public void SignUpClicked(View v){
        Intent intent = new Intent(context, SignUpActivity.class);
        startActivity(intent);

    }
    //check If Email is Exist
    public User checkIfEmailExist()
    {
        for (User u: dh.getUsers()) {
            if(email.getText().toString().equals(u.getEmail().toString()))
                return u;
        }
        return null;
    }
     //check If Password Is Correct
    public boolean checkIfPasswordIsCorrect()
    {
        for (User u: dh.getUsers()) {
          if((email.getText().toString().equals(u.getEmail()))&&
                  (!password.getText().toString().equals(u.getPassword())))
              return false;
        }
         return true;
    }





}
