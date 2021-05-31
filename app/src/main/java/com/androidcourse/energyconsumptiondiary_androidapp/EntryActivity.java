package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Entry;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Result;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.TypeEntry;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity  {

    private static final String TAG = "EntryActivity";
    private static final int RESULT_REQ_CODE=111;
    private static final int DATE =-1;
    private static final int TRANS = 0;
    private static final int FOOD = 1;
    private static final int ELECTRIC = 2;
    private static final int SERVICE = 3;

    private MyCo2FootprintManager dbManager=MyCo2FootprintManager.getInstance();

    private Button nextBtn = null;
    private Button backBtn = null;

    private Entry entryData= new Entry();
    private int currentIndex=DATE; //current fragment page number

    private  EntryDataFragment transportFragment = EntryDataFragment.newInstance(ImpactType.TRANSPORTATIOIN);
    private  EntryDataFragment foodFragment = EntryDataFragment.newInstance(ImpactType.FOOD);
    private  EntryDataFragment electricFragment = EntryDataFragment.newInstance(ImpactType.ELECTRICAL);
    private  EntryDataFragment serviceFragment = EntryDataFragment.newInstance(ImpactType.SERVICES);
    private EntryDateFragment dateFragment=EntryDateFragment.newInstance();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        //get user Id
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId=sharedPref.getInt(getResources().getString(R.string.prefLoggedUser),-1);
        entryData.setUserId(userId);
        nextBtn=(Button)findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFragmentNextClick(getCurrentFragmentEntries());
            }
        });
        backBtn=(Button)findViewById(R.id.backBtnEntry);
        backBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               onFragmentBackClick(getCurrentFragmentEntries());
           }
       });
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.root_layout, dateFragment)
                .commit();
        backBtn.setVisibility(View.GONE);
        //action bar set up
        ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.newEntry));
        ab.setDisplayHomeAsUpEnabled(true);

        context = this;
    }

    private ArrayList<TypeEntry> getCurrentFragmentEntries(){
        EntryDataFragment fr=null;
        ArrayList<TypeEntry>entries=new ArrayList<>();
        switch (currentIndex){
            case TRANS:
                fr=transportFragment;
                entries=fr.getEntries();
                break;
            case FOOD:
                fr=foodFragment;
                entries=fr.getEntries();
                break;
            case ELECTRIC:
                fr=electricFragment;
                entries=fr.getEntries();
                break;
            case SERVICE:
                fr=serviceFragment;
                entries=fr.getEntries();
                break;
            case DATE:
               EntryDateFragment dateFr=dateFragment;
              entryData.setDate(dateFr.getSelectedDate());
                break;

        }
        return entries;
    }

    public void onFragmentNextClick(List<TypeEntry> fragmentData) {
        Log.d("EntryActivity","entered next clicked");
        int nextIndex = currentIndex+1;
        currentIndex+=1;
        Log.d("EntryActivity","next index =" +nextIndex);
        if(fragmentData.size()>0) {
            this.entryData.addEntryList(fragmentData);
            nextBtn.setEnabled(true);
        }
        FragmentManager fm = getSupportFragmentManager();
        switch (nextIndex){
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)
                        .commit();
                backBtn.setVisibility(View.VISIBLE);
                break;
            case FOOD:
                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)
                        .commit();
                break;
            case ELECTRIC:

                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)

                        .commit();
                break;
            case SERVICE:
                nextBtn.setText(getResources().getString(R.string.showMyResults));
                if(entryData.getEntries().size()==0){
                    nextBtn.setEnabled(false);
                    nextBtn.setBackground(getDrawable(R.drawable.round_btn_dark_gray));
                    nextBtn.setText(getResources().getString(R.string.entryForResults));
                }
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .commit();
                break;
            default:
                if(nextIndex==SERVICE+1){
                    currentIndex-=1;
                    //go to results activity
                    Intent intent = new Intent(context, ResultsActivity.class);
                    // save the new entry
                   saveEntryToDB();
                    Result result=new Result(entryData.getUserId(),entryData.getDate());
                    // result calculations
                    result.calculateAndSetResult(entryData.getEntries());
                    int resultId= dbManager.createResult(result);
                    intent.putExtra("resultId",resultId);
                    intent.putExtra("date",this.entryData.getDate());
                    startActivity(intent);
                }
        }
    }

    private void saveEntryToDB() {
        ArrayList<TypeEntry> entriesList=entryData.getEntries();
        int entryId=dbManager.createEntry(entryData);
        for(TypeEntry entry : entriesList){
            dbManager.createTypeEntry(entryId,entry);
        }

    }

    public void onFragmentBackClick( List<TypeEntry> fragmentData) {
        int prevIndex = currentIndex-1;
        currentIndex-=1;
        if(fragmentData.size()>0) {
            this.entryData.addEntryList(fragmentData);
        }
        FragmentManager fm = getSupportFragmentManager();

        switch (prevIndex){
            case TRANS:
                fm.beginTransaction()
                        .replace(R.id.root_layout, transportFragment)
                        .commit();
                break;
            case FOOD:
                fm.beginTransaction()
                        .replace(R.id.root_layout, foodFragment)
                        .commit();
                break;
            case ELECTRIC:
                nextBtn.setText(getResources().getString(R.string.next));
                nextBtn.setEnabled(true);
                nextBtn.setBackground(getDrawable(R.drawable.round_btn_dark_green));
                fm.beginTransaction()
                        .replace(R.id.root_layout, electricFragment)
                        .commit();
                break;
            case SERVICE:
                nextBtn.setText(getResources().getString(R.string.showMyResults));
                fm.beginTransaction()
                        .replace(R.id.root_layout, serviceFragment)
                        .commit();
                break;
            case DATE:
                    fm.beginTransaction()
                            .replace(R.id.root_layout, dateFragment)
                            .commit();
                    backBtn.setVisibility(View.GONE);
                    break;
            default:
                finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onFragmentBackClick(getCurrentFragmentEntries());
                finish();
                return true;
            case R.id.menuEntryPrevResults:
                Intent intent1 = new Intent(this,PreviousResultsActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.menuEntrySettings:
                Intent intent2 = new Intent(this,SettingsActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menuEntryLogout:
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure ?")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EntryActivity.this, LogInActivity.class);

                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("No", null)
                        .show();
                return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        MyCo2FootprintManager.getInstance().openDataBase(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MyCo2FootprintManager.getInstance().closeDataBase();
        super.onPause();
    }
}