package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import com.androidcourse.energyconsumptiondiary_androidapp.Adapters.CustomListAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "EntryActivity";
    private final static  int  ACCOUNT_SETTINGS=0;
    private final static  int  NOTIFICATION_SETTINGS=1;
    private final static  int  SHARE=2;
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
        list = (ListView) findViewById(R.id.leaderboardRec);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        adapter = new CustomListAdapter(this, itemInfos);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ItemInfo selecteditem = adapter.getItem(position);
                Intent intent=null;
                switch (position){
                    case ACCOUNT_SETTINGS:
                        intent = new Intent(context, AccountSettingsActivity.class);
                        startActivity(intent);

                        break;
                    case NOTIFICATION_SETTINGS:
                        intent = new Intent(context, NotificationSettingsActivity.class);
                        startActivity(intent);

                        break;
                    case SHARE:
                        share(view);
                        break;
                }
            }
        });
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menuLogout:
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(SettingsActivity.this, LogInActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
        }
        return false;
    }
    //logout method
    public void logoutBtnClicked(View v){
        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
    }
      //share link of app in appstore
    public void share(View v){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CO2 Footprint");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" +"com.androidcourse.energyconsumptiondiary_androidapp"+"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}