package com.androidcourse.energyconsumptiondiary_androidapp.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.system.Os;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.androidcourse.energyconsumptiondiary_androidapp.MainActivity;

public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast toast = Toast.makeText(context,
//                "inside receiver",
//                Toast.LENGTH_SHORT);
//
//        toast.show();
//        Intent intent1 = new Intent(context, NotificationService.class);
//        context.startService(intent1);

//        Notification.Builder builder = new Notification.Builder(context);
//        builder.setContentTitle("Entry Reminder");
//        builder.setContentText("Time to add new entry in my co2 footprint");
//        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
//        Intent notifyIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //to be able to launch your activity from the notification
//        builder.setContentIntent(pendingIntent);
//        Notification notificationCompat = builder.build();
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//
//        managerCompat.notify(3, notificationCompat);

        // Define the Notification's expanded message and Intent:
        // Notification Sound and Vibration on Arrival
        Uri soundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] mVibratePattern = { 0, 200, 200, 300 };

        // Notification Text Elements
        String contentTitle = "Entry Reminder";
        int unicode=0x1F4C5;//calender emoji
        String contentText = "Time to add new entry! "+ new String(Character.toChars(unicode));


        Intent mNotificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
       PendingIntent mContentIntent = PendingIntent.getActivity(context.getApplicationContext(),
                0, mNotificationIntent, Intent.FILL_IN_ACTION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "ch");
        notificationBuilder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
        notificationBuilder.setAutoCancel(false);
        notificationBuilder.setContentTitle(contentTitle);
        notificationBuilder.setContentText(contentText);
        notificationBuilder.setContentIntent(mContentIntent);
        notificationBuilder.setSound(soundURI);
        notificationBuilder.setVibrate(mVibratePattern);

        // Pass the Notification to the NotificationManager:
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel("ch", "a",importance);
        mChannel.enableLights(true);
        mNotificationManager.createNotificationChannel(mChannel);
        mNotificationManager.notify(5, notificationBuilder.build());



    }
}
