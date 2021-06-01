package com.androidcourse.energyconsumptiondiary_androidapp;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class NotificationSettingsActivity extends AppCompatActivity implements TimePickerListener {
    private Context context;
    private CheckBox entryReminder;
    private ImageButton returnbtn;
    private Button save;
    private TextView time;
    final static int req1=1;
    public String a = "0"; // initialize this globally at the top of your class.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Notification", getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        entryReminder= (CheckBox) findViewById(R.id.entryreminderCk);
        entryReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(entryReminder.isChecked()) {
                    showTimeDialog();
                }
                else if(!entryReminder.isChecked()){
                    time.setText(getString(R.string.emptyDash));
                }
            }
        });
        time=(TextView)findViewById(R.id.reminderTime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });
        save = (Button) findViewById(R.id.save);
        context=this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void returnBtnClicked(View v) {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);
    }
    public void save(View v) {
//        Intent intent = new Intent(context, SettingsActivity.class);
//        startActivity(intent);
        Intent intent = new Intent(context, SettingsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), req1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time.getDrawingTime(), pendingIntent);
        a ="1";
        startActivity(intent);
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
    private void showTimeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TimePickerDialogFragment timeDialog = TimePickerDialogFragment.newInstance(getString(R.string.whenNotification));
        timeDialog.show(fm, "timeSettingsDialog");

    }

    @Override
    public void onDialogPositiveClick(TimePickerDialogFragment dialog) {
        String time= dialog.getChosenTime() ;
        this.time.setText(time);
        this.entryReminder.setChecked(true);
    }

    @Override
    public void onDialogNegativeClick(TimePickerDialogFragment dialog) {
        if(!entryReminder.isChecked()){
           this.entryReminder.setChecked(false);
        time.setText(getString(R.string.emptyDash));
        }
    }
}
