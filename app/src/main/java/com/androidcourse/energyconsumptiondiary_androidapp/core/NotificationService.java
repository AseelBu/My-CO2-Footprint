package com.androidcourse.energyconsumptiondiary_androidapp.core;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.androidcourse.energyconsumptiondiary_androidapp.MainActivity;

public class NotificationService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        Notification.Builder builder = new Notification.Builder(getApplicationContext());
//        builder.setContentTitle("Entry Reminder");
//        builder.setContentText("Time to add new entry in my co2 footprint");
//        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
//        Intent notifyIntent = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //to be able to launch your activity from the notification
//        builder.setContentIntent(pendingIntent);
//        Notification notificationCompat = builder.build();
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//
//        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

//        Uri soundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        long[] mVibratePattern = { 0, 200, 200, 300 };
//
//        // Notification Text Elements
//        String contentTitle = "Entry Reminder";
//        String contentText = "Time to add new entry in my co2 footprint";
//
//
//        Intent mNotificationIntent = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent mContentIntent = PendingIntent.getActivity(getApplicationContext(),
//                0, mNotificationIntent, Intent.FILL_IN_ACTION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "ch");
//        notificationBuilder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
//        notificationBuilder.setAutoCancel(false);
//        notificationBuilder.setContentTitle(contentTitle);
//        notificationBuilder.setContentText(contentText);
//        notificationBuilder.setContentIntent(mContentIntent);
//        notificationBuilder.setSound(soundURI);
//        notificationBuilder.setVibrate(mVibratePattern);
//
//        // Pass the Notification to the NotificationManager:
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        NotificationChannel mChannel = new NotificationChannel("ch", "a",importance);
//        mChannel.enableLights(true);
//        mNotificationManager.createNotificationChannel(mChannel);
//        mNotificationManager.notify(5, notificationBuilder.build());
    }
}