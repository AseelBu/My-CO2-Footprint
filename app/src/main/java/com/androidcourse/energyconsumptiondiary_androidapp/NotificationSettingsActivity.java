package com.androidcourse.energyconsumptiondiary_androidapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationSettingsActivity extends AppCompatActivity {
    private Context context;
    private CheckBox entryreminderr;
    private CheckBox tips;
    private ImageButton returnbtn;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Notificatin", getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
       entryreminderr= (CheckBox) findViewById(R.id.entryreminderr);
        tips = (CheckBox) findViewById(R.id.tips);
        returnbtn = (ImageButton) findViewById(R.id.imageButton6);
        save = (Button) findViewById(R.id.save);
        context=this;


    }

    public void returnBtnClicked(View v) {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);

    }
    public void save(View v) {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);

    }
}
