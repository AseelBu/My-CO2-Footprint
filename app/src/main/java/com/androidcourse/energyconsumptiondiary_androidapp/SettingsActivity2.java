//package com.androidcourse.energyconsumptiondiary_androidapp;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.UiModeManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.Toast;
//import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
//import com.androidcourse.energyconsumptiondiary_androidapp.AccountSettingsActivity;
//import com.androidcourse.energyconsumptiondiary_androidapp.LogInActivity;
//import com.androidcourse.energyconsumptiondiary_androidapp.NotificationSettingsActivity;
//import java.util.ArrayList;
//import java.util.List;
//
//public class SettingsActivity extends AppCompatActivity {
//
//    private Context context;
//    private ListView list;
//    private CustomListAdapter adapter;
//    private Button accountSettings=null;
//    private Button notificationsettings=null;
//    private Button shareWithFriends = null;
//    public ImageButton returnBtn;
//    private Switch btnToggleDark;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings);
//        accountSettings = (Button) findViewById(R.id.accountsettings);
//        notificationsettings = (Button) findViewById(R.id.notificationSet);
//        shareWithFriends = (Button) findViewById(R.id.sharewithfriends);
//        returnBtn = (ImageButton) findViewById(R.id.returnbtn);
//        btnToggleDark = (Switch) findViewById(R.id.dark);
//        context = this;
//
////        btnToggleDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
////                toggleTheme(isChecked);
////            }
////        });
//        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
//        itemInfos.add(new ItemInfo("Account Settings", R.drawable.user__1_));
//        itemInfos.add(new ItemInfo("Notification Settings", R.drawable.bell));
//        itemInfos.add(new ItemInfo("Share With Friends", R.drawable.share));
//        itemInfos.add(new ItemInfo());
////        itemInfos.add(new ItemInfo("Dark Mode",R.drawable.darkmode));
//        list = (ListView) findViewById(R.id.list);
//
//        adapter = new CustomListAdapter(this, itemInfos);
//
//
//        list.setAdapter(adapter);
//
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                ItemInfo selecteditem = adapter.getItem(position);
//                Toast.makeText(getApplicationContext(), selecteditem.getName(),
//                        Toast.LENGTH_SHORT).show();
//                //adapter.remove(selecteditem);
//            }
//        });
//    }
//
//    public void returnBtnClicked(View v){
//        Intent intent = new Intent(context, HomePageActivity.class);
//        startActivity(intent);
//
//    }
//    public void accountSettingsBtnClicked(View v){
//        Intent intent = new Intent(context, AccountSettingsActivity.class);
//        startActivity(intent);
//
//    }
//    public void notificationSettingsBtnClicked(View v){
//        Intent intent = new Intent(context, NotificationSettingsActivity.class);
//        startActivity(intent);
//
//    }
//    public void logoutBtnClicked(View v){
//        Intent intent = new Intent(context, LogInActivity.class);
//        startActivity(intent);
//
//    }
////
////    public void darkMode(View v){
////        setTheme(android.R.style.ThemeOverlay_Material_Dark);
////        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
////        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
////
////        if (useDarkTheme) {
////            setTheme(android.R.style.ThemeOverlay_Material_Dark);
////        }
////
////
////        btnToggleDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
////                toggleTheme(isChecked);
////            }
////        });
////    }
//
////    private void toggleTheme(boolean darkTheme) {
////        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
////        editor.putBoolean(PREF_DARK_THEME, darkTheme);
////        editor.apply();
////
////        Intent intent = getIntent();
////        finish();
////
////        startActivity(intent);
////    }
//
//    public void share(View v){
////        Intent i=new Intent(this, CameraDemoActivity.class);
////        startActivity(i);
//        try {
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain");
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CO2 Footprint");
//            String shareMessage= "\nLet me recommend you this application\n\n";
//            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
//            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//            startActivity(Intent.createChooser(shareIntent, "choose one"));
//        } catch(Exception e) {
//            //e.toString();
//        }
//    }
//
//    }



package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity2 extends AppCompatActivity {

    private Context context;
    private ListView list;
    private CustomListAdapter adapter;

    private Button accountSettings=null;
    private Button notificationsettings=null;
    private Button shareWithFriends = null;
    public ImageButton returnBtn;
    private Switch btnToggleDark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        context=this;
        List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
        itemInfos.add(new ItemInfo("Account Settings",R.drawable.user__1_));
        itemInfos.add(new ItemInfo("Notification Settings",R.drawable.bell));
        itemInfos.add(new ItemInfo("share with Friend",R.drawable.share));
        itemInfos.add(new ItemInfo());
//        itemInfos.add(new ItemInfo("Dark Mode",R.drawable.darkmode));
        list = (ListView) findViewById(R.id.list);
        accountSettings=(Button)findViewById(R.id.accountsettings);
        notificationsettings=(Button)findViewById(R.id.notificationSet);
        shareWithFriends=(Button)findViewById(R.id.sharewithfriends);
        returnBtn=(ImageButton)findViewById(R.id.returnbtn);
        btnToggleDark=(Switch)findViewById(R.id.dark);


        adapter = new CustomListAdapter(this, itemInfos);


        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ItemInfo selecteditem = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), selecteditem.getName(),
                        Toast.LENGTH_SHORT).show();
                //adapter.remove(selecteditem);
            }
        });


    }


        public void returnBtnClicked(View v){
        Intent intent = new Intent(context, HomePageActivity.class);
        startActivity(intent);

    }
    public void accountSettingsBtnClicked(View v){

        Intent intent = new Intent(context, AccountSettingsActivity.class);
        startActivity(intent);

    }
    public void notificationSettingsBtnClicked(View v){
        Intent intent = new Intent(context, NotificationSettingsActivity.class);
        startActivity(intent);

    }
    public void logoutBtnClicked(View v){
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);

    }
//
//    public void darkMode(View v){
//        setTheme(android.R.style.ThemeOverlay_Material_Dark);
//        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
//
//        if (useDarkTheme) {
//            setTheme(android.R.style.ThemeOverlay_Material_Dark);
//        }
//
//
//        btnToggleDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
//                toggleTheme(isChecked);
//            }
//        });
//    }

//    private void toggleTheme(boolean darkTheme) {
//        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putBoolean(PREF_DARK_THEME, darkTheme);
//        editor.apply();
//
//        Intent intent = getIntent();
//        finish();
//
//        startActivity(intent);
//    }

    public void share(View v){

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CO2 Footprint");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}