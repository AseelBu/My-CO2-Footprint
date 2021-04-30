package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ForgetPasswordActivity extends Activity  {
    private static final String TAG = "ForgetpasswordActivity";
    private EditText phone = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        phone=(EditText) findViewById(R.id.email);
        context=this;

    }
    public void sendSmsOnClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:")); // This ensures only SMS apps respond   
        intent.putExtra("sms_body", "Hello");
        intent.putExtra("subject", "helo sir");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }
    public void returnClicked(View v){

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }
}
