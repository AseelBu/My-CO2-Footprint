package com.androidcourse.energyconsumptiondiary_androidapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.androidcourse.energyconsumptiondiary_androidapp.Model.Co2Impacter;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.ElectricalHouseSupplies;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Food;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.MyCo2FootprintManager;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Service;
import com.androidcourse.energyconsumptiondiary_androidapp.Model.Transportation;
import com.androidcourse.energyconsumptiondiary_androidapp.core.DataHolder;
import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;

public class MainActivity extends AppCompatActivity {
//    private String lorem="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ultrices tincidunt nisl eget semper. Nulla dictum feugiat tortor ac venenatis. Mauris id leo nisl. Donec vitae risus mauris. Suspendisse bibendum, nibh ac bibendum commodo, justo nisl eleifend tortor, sed rhoncus orci est nec risus. Maecenas scelerisque venenatis tellus vel porta. Vestibulum sed eros sodales, tincidunt neque in, consectetur dolor. Aenean eu nisi et massa suscipit lacinia. Integer hendrerit sapien viverra ante vulputate, non posuere augue condimentum. Sed mauris sapien, sollicitudin nec ullamcorper a, blandit ut sapien. ";
    private DataHolder dh = DataHolder.getInstance();
    private MyCo2FootprintManager mg=MyCo2FootprintManager.getInstance();
    public Co2Impacter impacter;
    public ImpactType impacterType;

    private Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.login);
            MyCo2FootprintManager.getInstance().openDataBase(this);

            addDataToDataHolder();

        Intent intent = new Intent(context, LogInActivity.class);
        startActivity(intent);
        MainActivity.this.finish();
            hideKeyboard();


        }

        private void addDataToDataHolder(){
            addUsers();
            createTransportation(2);
            createFood(4);
            createElectricals(5);
            createService(2);
            createTips(4);

        }
//    builder for the impacter by type
    private void createImpacter(ImpactType t) {
        switch (t){
            case TRANSPORTATIOIN:
                impacter= new Transportation();
                break;
            case FOOD:
                impacter= new Food();
                break;
            case ELECTRICAL:
                impacter= new ElectricalHouseSupplies();
                break;
            case SERVICES:
                impacter= new Service();
                break;
        }
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
            int id;
        createImpacter(ImpactType.TRANSPORTATIOIN);
        impacter=new Transportation("Plane","How many hours did you fly today?",Units.HOUR,700, BitmapFactory.decodeResource(context.getResources(),R.drawable.travelling),"Diesel");
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
             id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createTransportation(id, (Transportation) impacter);
        }
        impacter=new Transportation("Bus","How many hours did you travel today using the bus?",Units.HOUR,500,BitmapFactory.decodeResource(context.getResources(),R.drawable.bus),"Diesel");
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createTransportation(id, (Transportation) impacter);
        }
        impacter=new Transportation("Bicycle","How many hours did you travel today using the bicycle?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.bycicle),"Diesel");
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createTransportation(id, (Transportation) impacter);
        }
        impacter=new Transportation("delivery truck","How many hours did you travel today using the delivery truck?",Units.HOUR,600,BitmapFactory.decodeResource(context.getResources(),R.drawable.delivery),"Diesel");
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createTransportation(id, (Transportation) impacter);
        }
    }

    private void createFood(int size){
            int id;
        createImpacter(ImpactType.FOOD);
        impacter=new Food("Eggs","How many grams did you eat?",Units.UNIT,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.eggs));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter=new Food("cake","How many grams did you eat?",Units.UNIT,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.cake));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter=new Food("Chocolate","How many grams did you eat?",Units.UNIT,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.chocolate));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter=new Food("hotdog","How many grams did you eat?",Units.UNIT,300,BitmapFactory.decodeResource(context.getResources(),R.drawable.hotdog));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter=new Food("salad","How many grams did you eat?",Units.UNIT,140,BitmapFactory.decodeResource(context.getResources(),R.drawable.salad));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter=new Food("pizza","How many grams did you eat?",Units.UNIT,400,BitmapFactory.decodeResource(context.getResources(),R.drawable.pizza));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        impacter= new Food("cake","How many grams did you eat?",Units.UNIT,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.cake));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createFood(id, (Food) impacter);
        }
        }

    private void createElectricals(int size){
            int id;
        createImpacter(ImpactType.ELECTRICAL);
        impacter=new ElectricalHouseSupplies("Washing machine","How many hours you use it?",Units.HOUR,700,BitmapFactory.decodeResource(context.getResources(),R.drawable.washingmachine));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createElectric(id, (ElectricalHouseSupplies) impacter);
        }
        impacter=new ElectricalHouseSupplies("Dish washer","How many hours you use it?",Units.HOUR,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.dishwasher));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createElectric(id, (ElectricalHouseSupplies) impacter);
        }
        impacter=new ElectricalHouseSupplies("Kettele","How many hours you use it?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.kett));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createElectric(id, (ElectricalHouseSupplies) impacter);
        }
        impacter=new ElectricalHouseSupplies("Lamps","How many hours you use it?",Units.HOUR,200,BitmapFactory.decodeResource(context.getResources(),R.drawable.lamps));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createElectric(id, (ElectricalHouseSupplies) impacter);
        }
        }

    private void createService(int size){
            int id;
        createImpacter(ImpactType.SERVICES);
            impacter=new Service( "water consumption ", "How many liters have you used?", Units.Liter, 700, BitmapFactory.decodeResource(context.getResources(), R.drawable.wat));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createService(id, (Service) impacter);
        }
        impacter=new Service("gas1","How many times you use it?",Units.TIMES,100,BitmapFactory.decodeResource(context.getResources(),R.drawable.gas));
        if(!mg.findIfImpacterIsExists(impacter,impacterType)) {
            id = MyCo2FootprintManager.getInstance().createCO2Impacter(impacter);
            MyCo2FootprintManager.getInstance().createService(id, (Service) impacter);
        }
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
    private void hideKeyboard() {
        try {
            View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}