package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Locale;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = "LogInActivity";
    private DataHolder dh = DataHolder.getInstance();
    private FirebaseAuth mAuth;
    public EditText email ;
    public EditText password ;
    private Context context;
    private SharedPreferences prefs ;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email=(EditText)findViewById(R.id.email222);
        email.setText("Admin@gmail.com");
        password=(EditText)findViewById(R.id.oldpassword);
        password.setText("Admin12");
        TextView forgetPassword = (TextView) findViewById(R.id.forgotPass);
        Button login = (Button) findViewById(R.id.edititem2);
        TextView signUp = (TextView) findViewById(R.id.loginLink);
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        context=this;
        getSupportActionBar().hide();
        login.setOnClickListener(singInUserListener);
    }

    private View.OnClickListener singInUserListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            hideKeyboard();
            String email2 = email.getText().toString();
            String password2 = password.getText().toString();
            mAuth.signInWithEmailAndPassword(email2, password2)
                    .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                //TODO add snackbar
                                Toast.makeText(LogInActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
//                                Toast.makeText(LogInActivity.this, task.getException().toString(),
//                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };





    private void updateUI(FirebaseUser user) {
        if(user!=null){
            user.getEmail();
            MyCo2FootprintManager.getInstance().openDataBase(this);
            Intent intent = new Intent(this, HomePageActivity.class);
            if(user.getEmail().toLowerCase().equals("admin@gmail.com")) {
                intent.putExtra("Admin", true);
            }
            else
            {
                intent.putExtra("Admin",false);
            }
            startActivity(intent);
            finish();
        }
    }



    //log in to the home page with check if txtEmail and txtPassword is correct,valid,and exists
//    public void loginClicked(View v) {
//        boolean flag = true;
//        try {
//            //if the user dosn't enter all the details
//            if (TextUtils.isEmpty(txtEmail.getText().toString()) ||
//                    TextUtils.isEmpty(txtPassword.getText().toString()))
//             {
//                flag = false;
//                Toast.makeText(LogInActivity.this,
//                        "Please enter all details",
//                        Toast.LENGTH_SHORT).show();
//            }
//        } catch (NumberFormatException exception) {
//        }
//        if(flag==true){
//            User user=checkIfEmailExist();
//            //if the txtEmail not found
//            if (user == null) {
//                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//                dlgAlert.setMessage("Unfortunately Email Not Found ):");
//                dlgAlert.setTitle("Message...");
//                dlgAlert.setPositiveButton("OK", null);
//                dlgAlert.setCancelable(true);
//                dlgAlert.create().show();
//            } else {
//               //if the txtPassword is not correct
//                if (checkIfPasswordIsCorrect() == false) {
//                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
//                    dlgAlert.setMessage("Wrong Password,try again :)");
//                    dlgAlert.setTitle("Message...");
//                    dlgAlert.setPositiveButton("OK", null);
//                    dlgAlert.setCancelable(true);
//                    dlgAlert.create().show();
//                }  else {
//                    SharedPreferences.Editor editor = prefs.edit();
//                    editor.putInt(getResources().getString(R.string.prefLoggedUser), user.getUserId());
//                    if(editor.commit()){
//                        Log.i(TAG, getClass().getSimpleName() + "logged user was save to memory");
//                    }
//                    if(txtEmail.getText().toString().equals("Admin@gmail.com")) {
//                        Intent intent = new Intent(context, HomePageActivity.class);
//                        intent.putExtra("Admin",true);
//                        String toSpeak = "Welcome Admin";
//                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
//                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else
//                    {
//                        Intent intent = new Intent(context, HomePageActivity.class);
//                        intent.putExtra("Admin",false);
//                        String toSpeak = "Welcome";
//                        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
//                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }
//        }
//    }
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
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
