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
        setContentView(R.layout.login);
        addDataToDataHolder();
        context=this;
        Intent intent = new Intent(context,LogInActivity.class);
        startActivity(intent);
        MainActivity.this.finish();

        }

        private void addDataToDataHolder(){
            addUsers();
            createTransportation(2);
            createFood(4);
            createElectricals(5);
            createService(2);

        }

        private void addUsers() {
            dh.addAdminDetails(1,"Admin@gmail.com", "Admin","Admin","Admin",getResources().getDrawable(R.drawable.ic_baseline_person_24));
            createUsers(9);
        }
        private void createUsers(int size){
            Drawable img= getResources().getDrawable(R.drawable.ic_baseline_person_24);
            for(int i=0;i<=size;i++){
                dh.addUser((i+1)*100,"go "+i,"go "+i,i*10,img);
            }
        }

    private void createTransportation(int size){
        Drawable img= getResources().getDrawable(R.drawable.ic_baseline_directions_car_24_dark);
        for(int i=0;i<=size;i++){
            dh.addTransportation(i+1,"car"+i,"car "+i+"?",img);
        }
    }
    private void createFood(int size){
        Drawable img= getResources().getDrawable(R.drawable.ic_baseline_fastfood_24);
        for(int i=0;i<=size;i++){
            dh.addFood(i+1,"food"+i,"food "+i+"?",img);
        }
    }

    private void createElectricals(int size){
        Drawable img= getResources().getDrawable(R.drawable.ic_baseline_flash_on_24);
        for(int i=0;i<=size;i++){
            dh.addElectrics(i+1,"elec"+i,"elec "+i+"?",img);
        }
    }

    private void createService(int size){
        Drawable img= getResources().getDrawable(R.drawable.ic_baseline_wash_24);
        for(int i=0;i<=size;i++){
            dh.addService(i+1,"service"+i,"service "+i+"?",img);
        }
    }


}