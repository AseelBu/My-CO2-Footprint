//package com.androidcourse.energyconsumptiondiary_androidapp;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
//import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
//import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
//import com.google.android.material.snackbar.Snackbar;
//
//public class UpdatePasswordActivity extends AppCompatActivity {
//    private Context context;
//    private CustomListAdapter adapter;
//    private TextView email;
//    private TextView oldPassword;
//    private TextView newPassword;
//    private TextView confirmpassword;
//    private ImageButton returnBtn;
//    private Button save;
//    private DataHolder dh = DataHolder.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.updatepassword);
//        email= (TextView) findViewById(R.id.emailaccount2);
//        oldPassword = (TextView) findViewById(R.id.oldpassword);
//        newPassword = (TextView) findViewById(R.id.newpassword);
//        confirmpassword = (EditText) findViewById(R.id.confirmnewpassword);
//        save = (Button) findViewById(R.id.edititem2);
//        email.setText("Admin@gmail.com");
//        context = this;
//        ActionBar ab = getSupportActionBar();
//        ab.setDisplayHomeAsUpEnabled(true);
//    }
//    //return to the previous activity
//    public void returnBtnClicked(View v){
//        Intent intent = new Intent(context, AccountSettingsActivity.class);
//        startActivity(intent);
//    }
//   //check If Email Exist
//    public boolean checkIfEmailExist()
//    {
//        for (User u: dh.getUsers()) {
//            if(email.getText().toString().equals(u.getEmail().toString()))
//                return true;
//        }
//        return false;
//    }
//    //check If Password Is Correct
//    public boolean checkIfPasswordIsCorrect()
//    {
//        for (User u: dh.getUsers()) {
//            if((email.getText().toString().equals(u.getEmail()))&&
//                    (!oldPassword.getText().toString().equals(u.getPassword())))
//                return false;
//        }
//        return true;
//    }
//    //check If Two Password Is Equals
//    public boolean checkIfTwoPasswordIsEquals()
//    {
//        return newPassword.getText().toString().equals(confirmpassword.getText().toString());
//    }
//    //check If new Password Equal To Old Password
//    public boolean checkIfnewPasswordEqualToOldPassword()
//    {
//        return newPassword.getText().toString().equals(oldPassword.getText().toString());
//    }
//    //save the new txtPassword
//    public void saveClicked(View v){
//        boolean flag = true;
//        try {
//            if (TextUtils.isEmpty(email.getText().toString()) ||
//                    TextUtils.isEmpty(oldPassword.getText().toString()) ||
//                    TextUtils.isEmpty(newPassword.getText().toString()) ||
//                    TextUtils.isEmpty(confirmpassword.getText().toString())
//            ) {
//                flag = false;
//                String toSpeak = "Please enter all details";
//                View parentLayout = findViewById(android.R.id.content);
//                final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                bar.setAction("Dismiss", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bar.dismiss();
//                    }
//                });
//                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                bar.show();
//
//
//            }
//        } catch (NumberFormatException exception) {
//        }
//        if (flag == true) {
//            String emailTheAccount, oldpass, newpass;
//            emailTheAccount = email.getText().toString();
//            oldpass = oldPassword.getText().toString();
//            newpass = newPassword.getText().toString();
//            if (checkIfEmailExist() == false) {
//                String toSpeak = "Email Not Found";
//                View parentLayout = findViewById(android.R.id.content);
//                final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                bar.setAction("Dismiss", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        bar.dismiss();
//                    }
//                });
//                bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                bar.show();
//
//            } else
//                {
//                if (checkIfTwoPasswordIsEquals() == false)
//                {
//                    String toSpeak = "Two Passwords you enter not equals,try again.";
//                    View parentLayout = findViewById(android.R.id.content);
//                    final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                    bar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            bar.dismiss();
//                        }
//                    });
//                    bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                    bar.show();
//
//                }
//                else
//                    {
//                    if (checkIfnewPasswordEqualToOldPassword() == true) {
//                        String toSpeak = "New txtPassword you enter like the old txtPassword.Please enter a different txtPassword";
//                        View parentLayout = findViewById(android.R.id.content);
//                        final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                        bar.setAction("Dismiss", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                bar.dismiss();
//                            }
//                        });
//                        bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                        bar.show();
//                    }
//                    else
//                        {
//                        if (checkIfPasswordIsCorrect() == false) {
//                            String toSpeak = "your currently txtPassword not correct";
//                            View parentLayout = findViewById(android.R.id.content);
//                            final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                            bar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    bar.dismiss();
//                                }
//                            });
//                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                            bar.show();
//                        } else{
//                            for (User u : dh.getUsers()) {
//                                if (emailTheAccount.equals(u.getEmail())) {
//                                    u.setPassword(newpass);
//                                }
//                            }
//                            String toSpeak = "Password changed successfully";
//                            View parentLayout = findViewById(android.R.id.content);
//                            final Snackbar bar = Snackbar.make(parentLayout, toSpeak, Snackbar.LENGTH_INDEFINITE);
//                            bar.setAction("Dismiss", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    bar.dismiss();
//                                }
//                            });
//                            bar.setActionTextColor(getResources().getColor(R.color.dangerRed));
//                            bar.show();
//                        Intent intent = new Intent(context, SettingsActivity.class);
//                        startActivity(intent);
//                        this.finish();
//                    }
//                }
//            }
//                }
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return false;
//    }
//}
