package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Tip;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    private String lorem="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ultrices tincidunt nisl eget semper. Nulla dictum feugiat tortor ac venenatis. Mauris id leo nisl. Donec vitae risus mauris. Suspendisse bibendum, nibh ac bibendum commodo, justo nisl eleifend tortor, sed rhoncus orci est nec risus. Maecenas scelerisque venenatis tellus vel porta. Vestibulum sed eros sodales, tincidunt neque in, consectetur dolor. Aenean eu nisi et massa suscipit lacinia. Integer hendrerit sapien viverra ante vulputate, non posuere augue condimentum. Sed mauris sapien, sollicitudin nec ullamcorper a, blandit ut sapien. ";
    private DataHolder dh = DataHolder.getInstance();

    private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.login);
        addDataToDataHolder();

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
        MainActivity.this.finish();

        }

        private void addDataToDataHolder(){
            addUsers();
            createTransportation(2);
            createFood(4);
            createElectricals(5);
            createService(2);
            createTips(4);

        }



    //add users method
        private void addUsers() {
            dh.addAdmin(1,"Admin@gmail.com", "Admin","Admin","Admin",getResources().getDrawable(R.drawable.ic_baseline_person_24));
            dh.addAdmin(2,"User@gmail.com", "User","User","regular",getResources().getDrawable(R.drawable.ic_baseline_person_24));
            createUsers(9);
        }
    //create users method
        private void createUsers(int size){
            Drawable img= getDrawable(R.drawable.ic_baseline_person_24);
            for(int i=0;i<=size;i++){
                dh.addUser((i+1)*100,"go "+i,"go "+i,"go"+i+"@g.com","go",i*10,img);
            }
        }

        //create Co2 impacters
    private void createTransportation(int size){
        Bitmap img= BitmapFactory.decodeResource(getResources(),R.drawable.ic_baseline_directions_car_24_dark);
        int i;
        for(i=0;i<=size;i++){
            dh.addTransportation(i+40,"car"+i,"car "+i+"?", Units.HOUR,100, img,null);
        }

        dh.addTransportation((i+40),"Plane","How many hours did you fly today?",Units.HOUR,700, BitmapFactory.decodeResource(context.getResources(),R.drawable.travelling),null);
        i++;
        dh.addTransportation(i+40,"Bus","How many hours did you travel today using the bus?",Units.HOUR,500,BitmapFactory.decodeResource(context.getResources(),R.drawable.bus),"Diesel");
        i++;
        dh.addTransportation(i+40,"Bicycle","How many hours did you travel today using the bicycle?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.bycicle),null);
        i++;
        dh.addTransportation(i+40,"Bicycle2","",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.car2),"Diesel2");
        i++;
        dh.addTransportation(i+40,"Bicycle3","",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.travelling),"Diesel3");
        i++;
        dh.addTransportation(i+40,"Bicycle4","",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.bus),"Diesel4");

    }

    private void createFood(int size){
        Bitmap img= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_baseline_fastfood_24);
        int i;
        for(i=0;i<=size;i++){
            dh.addFood(i+10,"food"+i,"food "+i+"?",Units.UNIT,i,img);
        }

        dh.addFood(i+10,"Fish","",Units.UNIT,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.fish));
        i++;
        dh.addFood(i+10,"Eggs","",Units.UNIT,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.eggs));
        i++;
        dh.addFood(i+10,"Chocolate","",Units.UNIT,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.chocolate));
        i++;
        dh.addFood(i+10,"Water","",Units.UNIT,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.water));
    }

    private void createElectricals(int size){
        Bitmap img= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_baseline_flash_on_24);
        int i;
        for( i=0;i<=size;i++){
            dh.addElectrics(i+20,"elec"+i,"elec "+i+"?",Units.HOUR,i,img);
        }
        i++;
        dh.addElectrics(i+20,"Washing machine","?",Units.HOUR,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.washingmachine));
        i++;
        dh.addElectrics(i+20,"Dish washer","?",Units.HOUR,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.dishwasher));
        i++;
        dh.addElectrics(i+20,"Kettele","?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.kett));
        i++;
        dh.addElectrics(i+20,"Lamps","?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.lamps));
    }

    private void createService(int size){
        Bitmap img=BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_baseline_wash_24);
        int i;
        for(i=0;i<=size;i++){
            dh.addService(i+30,"service"+i,"service "+i+"?",Units.HOUR,i,img);
        }
        i++;
        dh.addService(i+30,"water consumption ","?",Units.HOUR,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.wat));
        i++;
        dh.addService(i+30,"gas1","?",Units.HOUR,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.gas));
        i++;
        dh.addService(i+30,"gas2","?",Units.HOUR,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.gas));
        i++;
        dh.addService(i+30,"water consumption1 ","?",Units.HOUR,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.wat));
        i++;
        dh.addService(i+30,"water consumption2 ","?",Units.HOUR,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.wat));
    }

//-----create tips
    private void createTips(int size) {
        Bitmap img=BitmapFactory.decodeResource(context.getResources(),R.drawable.car);
        for (int i= 0; i <= size; i++) {
            dh.addTip("Title","some Tip",img);
        }
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