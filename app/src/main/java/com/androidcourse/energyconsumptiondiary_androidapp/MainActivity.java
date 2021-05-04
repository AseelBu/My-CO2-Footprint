package com.androidcourse.energyconsumptiondiary_androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;

public class MainActivity extends AppCompatActivity {
    private DataHolder dh = DataHolder.getInstance();

    private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transportation);
        addDataToDataHolder();
        context=this;
        Intent intent = new Intent(context, TransportationMain.class);
        startActivity(intent);
        MainActivity.this.finish();

        }

        private void addDataToDataHolder(){
            addUsers();

        }

        private void addUsers() {
            dh.addAdminDetails(1,"Admin@gmail.com", "Admin","Admin","Admin",getResources().getDrawable(R.drawable.ic_baseline_person_24));
            createUsers(9);
        }
    public void createUsers(int size){
        Drawable img= getResources().getDrawable(R.drawable.ic_baseline_person_24);
        for(int i=0;i<=size;i++){
            dh.addUser((i+1)*100,"go "+i,"go "+i,i*10,img);
        }
    }

}