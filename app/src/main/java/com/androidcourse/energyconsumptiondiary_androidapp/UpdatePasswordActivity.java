package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.User;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class UpdatePasswordActivity extends AppCompatActivity {
    private Context context;
    private CustomListAdapter adapter;
    private TextView email;
    private TextView oldPassword;
    private TextView newPassword;
    private TextView confirmpassword;
    private ImageButton returnBtn;
    private Button save;
    private DataHolder dh = DataHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepassword);
        email= (TextView) findViewById(R.id.emailaccount2);
        oldPassword = (TextView) findViewById(R.id.oldpassword);
        newPassword = (TextView) findViewById(R.id.newpassword);
        confirmpassword = (EditText) findViewById(R.id.confirmnewpassword);
        save = (Button) findViewById(R.id.edititem2);
        email.setText("Admin@gmail.com");
        context = this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
    //return to the previous activity
    public void returnBtnClicked(View v){
        Intent intent = new Intent(context, AccountSettingsActivity.class);
        startActivity(intent);
    }
   //check If Email Exist
    public boolean checkIfEmailExist()
    {
        for (User u: dh.getUsers()) {
            if(email.getText().toString().equals(u.getEmail().toString()))
                return true;
        }
        return false;
    }
    //check If Password Is Correct
    public boolean checkIfPasswordIsCorrect()
    {
        for (User u: dh.getUsers()) {
            if((email.getText().toString().equals(u.getEmail()))&&
                    (!oldPassword.getText().toString().equals(u.getPassword())))
                return false;
        }
        return true;
    }
    //check If Two Password Is Equals
    public boolean checkIfTwoPasswordIsEquals()
    {
        return newPassword.getText().toString().equals(confirmpassword.getText().toString());
    }
    //check If new Password Equal To Old Password
    public boolean checkIfnewPasswordEqualToOldPassword()
    {
        return newPassword.getText().toString().equals(oldPassword.getText().toString());
    }
    //save the new password
    public void saveClicked(View v){
        boolean flag = true;
        try {
            if (TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(oldPassword.getText().toString()) ||
                    TextUtils.isEmpty(newPassword.getText().toString()) ||
                    TextUtils.isEmpty(confirmpassword.getText().toString())
            ) {
                flag = false;
                Toast.makeText(UpdatePasswordActivity.this,
                        "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException exception) {
        }
        if (flag == true) {
            String emailTheAccount, oldpass, newpass;
            emailTheAccount = email.getText().toString();
            oldpass = oldPassword.getText().toString();
            newpass = newPassword.getText().toString();
            if (checkIfEmailExist() == false) {
                Toast.makeText(UpdatePasswordActivity.this,
                        "Email Not Found ",
                        Toast.LENGTH_SHORT).show();
            } else
                {
                if (checkIfTwoPasswordIsEquals() == false)
                {
                    Toast.makeText(UpdatePasswordActivity.this,
                            "Two Passwords you enter not equals,try again. ",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    {
                    if (checkIfnewPasswordEqualToOldPassword() == true) {
                        Toast.makeText(UpdatePasswordActivity.this,
                                "New password you enter like the old password.Please enter a different password ",
                                Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                        if (checkIfPasswordIsCorrect() == false) {
                            Toast.makeText(UpdatePasswordActivity.this,
                                    "your currently password not correct",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            for (User u : dh.getUsers()) {
                                if (emailTheAccount.equals(u.getEmail())) {
                                    u.setPassword(newpass);
                                }
                            }
                        Toast.makeText(UpdatePasswordActivity.this,
                                "Password changed successfully ",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, SettingsActivity.class);
                        startActivity(intent);
                    }
                }
            }
                }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }
}
