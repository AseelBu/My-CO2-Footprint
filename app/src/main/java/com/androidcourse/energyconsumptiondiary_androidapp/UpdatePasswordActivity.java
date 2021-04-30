package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;

public class UpdatePasswordActivity extends AppCompatActivity {
    private Context context;
    private CustomListAdapter adapter;
    private TextView oldPassword;
    private TextView newPassword;
    private TextView confirmpassword;
    private ImageButton returnBtn;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepassword);
        oldPassword = (TextView) findViewById(R.id.password);
        newPassword = (TextView) findViewById(R.id.newpassword);
        confirmpassword = (EditText) findViewById(R.id.confirmnewpassword);
        returnBtn = (ImageButton) findViewById(R.id.imageButton3);
        save = (Button) findViewById(R.id.sendEmail);

        context = this;
    }
    public void returnBtnClicked(View v){
        Intent intent = new Intent(context, AccountSettingsActivity.class);
        startActivity(intent);

    }
    public void saveClicked(View v){
        Intent intent = new Intent(context,SettingsActivity.class);
        startActivity(intent);

    }
}
