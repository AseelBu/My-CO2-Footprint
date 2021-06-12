package com.androidcourse.energyconsumptiondiary_androidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1);
    }
}