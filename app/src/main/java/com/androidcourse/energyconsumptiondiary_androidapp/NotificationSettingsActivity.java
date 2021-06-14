package com.androidcourse.energyconsumptiondiary_androidapp;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.androidcourse.energyconsumptiondiary_androidapp.core.TimeReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationSettingsActivity extends AppCompatActivity implements TimePickerListener {
    private final static int NOTIFICATION_REMINDER=11;
    private Context context;
    private CheckBox entryReminder;
    private ImageButton returnbtn;
    private Button save;
    private TextView time;

    private SharedPreferences prefs = null;
    private boolean checkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Notification", getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        entryReminder= (CheckBox) findViewById(R.id.entryreminderCk);
        entryReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                if(entryReminder.isChecked()) {
                    showTimeDialog();
                }
                else if(!entryReminder.isChecked()){
                    time.setText(getString(R.string.emptyDash));
                    checkStatus=false;
                    editor.putBoolean(getString(R.string.isEntryNotificationSet), false);
                    editor.commit();
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
        if(prefs.getBoolean(getString(R.string.isEntryNotificationSet),false)) {
            checkStatus=true;
            entryReminder.setChecked(true);
            time.setText(prefs.getString(getString(R.string.entryNotificationTime),getString(R.string.emptyDash)));
        }else{
            checkStatus=false;
        }
        context=this;
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void returnBtnClicked(View v) {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);
    }
    public void save(View v) {
        String notiTime=null;
        if(prefs.getBoolean(getString(R.string.isEntryNotificationSet),false)) {

            notiTime = prefs.getString(getString(R.string.entryNotificationTime), getString(R.string.emptyDash));

            Intent notifyIntent = new Intent(this, TimeReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_REMINDER, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            long c =  milliseconds(notiTime);
            long interval =  1000 * 60 * 60* 24;
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c, interval, pendingIntent);
        }

        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.isEntryNotificationSet), true);
        editor.putString(getString(R.string.entryNotificationTime), time);

        if(editor.commit()){
            Log.i("Notification", getClass().getSimpleName() + "notification settings are saved");
        }
        checkStatus=true;
    }

    @Override
    public void onDialogNegativeClick(TimePickerDialogFragment dialog) {
        SharedPreferences.Editor editor = prefs.edit();
        if(!checkStatus){
           this.entryReminder.setChecked(false);
            editor.putBoolean(getString(R.string.isEntryNotificationSet), false);
            editor.commit();
        time.setText(getString(R.string.emptyDash));
        }
    }

    public long milliseconds(String time)
    {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");

        try
        {
            Date mDate = sdf.parse(time);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, mDate.getHours());
            c.set(Calendar.MINUTE,mDate.getMinutes());
            c.set(Calendar.SECOND,0);

            long timeInMilliseconds = c.getTimeInMillis();
            return timeInMilliseconds;
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
}
