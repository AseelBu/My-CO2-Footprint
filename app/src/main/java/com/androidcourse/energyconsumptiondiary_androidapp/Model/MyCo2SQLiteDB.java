package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyCo2SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="MyCo2FootprintDB";


    //CO2 impacter table
    private static final String TABLE_CO2IMPACTER_NAME = "co2_impacter";
    private static final String CO2IMPACTER_COLUMN_ID="impacterID";
    private static final String CO2IMPACTER_COLUMN_NAME= "name";
    private static final String CO2IMPACTER_COLUMN_QUESTION= "question";
    private static final String CO2IMPACTER_COLUMN_UNIT= "unit";
    private static final String CO2IMPACTER_COLUMN_CO2AMOUNT= "co2Amount";
    private static final String CO2IMPACTER_COLUMN_IMG= "img";
    private static final String[] TABLE_CO2IMPACTER_COLUMNS ={CO2IMPACTER_COLUMN_ID,CO2IMPACTER_COLUMN_NAME,
            CO2IMPACTER_COLUMN_QUESTION,CO2IMPACTER_COLUMN_UNIT,CO2IMPACTER_COLUMN_CO2AMOUNT,CO2IMPACTER_COLUMN_IMG};
    //Transportation table
    private static final String TABLE_TRANSPORTATION_NAME = "transportation";
    private static final String TRANSPORTATION_COLUMN_IMPACTERID="co2ImpacterId";//foreign key
    //TODO rest of the fields
    private static final String[] TABLE_TRANSPORTATION_COLUMNS ={};

    //Food table
    private static final String TABLE_FOOD_NAME = "food";
    private static final String FOOD_COLUMN_IMPACTERID="co2ImpacterId";//foreign key
    //TODO rest of the fields
    private static final String[] TABLE_FOOD_COLUMNS ={};

    //Service table
    private static final String TABLE_SERVICE_NAME = "service";
    private static final String SERVICE_COLUMN_IMPACTERID="co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_SERVICE_COLUMNS ={};

    //ElectricalHouseSupplies table
    private static final String TABLE_ELECTRICS_NAME = "electrics";
    private static final String ELECTRICS_COLUMN_IMPACTERID="co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_ELECTRICS_COLUMNS ={};

    //Tips table
    private static final String TABLE_TIP_NAME = "tip";
    //TODO rest of the fields
    private static final String[] TABLE_TIP_COLUMNS ={};

    //Entry Table
    private static final String TABLE_ENTRY_NAME = "entry";
    //TODO rest of the fields
    private static final String[] TABLE_ENTRY_COLUMNS ={};


    private SQLiteDatabase db = null;

    public MyCo2SQLiteDB(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    try{
        //create CO2 impacter table
        String CREATE_CO2IMPACTER_TABLE="create table if not exists "+TABLE_CO2IMPACTER_NAME+"("
                +CO2IMPACTER_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CO2IMPACTER_COLUMN_NAME+" TEXT,"
                +CO2IMPACTER_COLUMN_QUESTION+" TEXT,"
                +CO2IMPACTER_COLUMN_UNIT+" TEXT,"
                +CO2IMPACTER_COLUMN_CO2AMOUNT+" INTEGER,"
                +CO2IMPACTER_COLUMN_IMG+" BLOB"
                +")";
        db.execSQL(CREATE_CO2IMPACTER_TABLE);
        //create Transportation table
        //TODO
        //create Food table
        String CREATE_FOOD_TABLE="create table if not exists "+TABLE_FOOD_NAME+"("
                +FOOD_COLUMN_IMPACTERID+" INTEGER"
                +")";
        db.execSQL(CREATE_FOOD_TABLE);
        //TODO

        //create Service table
        //TODO

        //create ElectricalHouseSupplies table
        //TODO

        //create Tips table
        //TODO

        //create Entries Table
        //TODO


    }catch (Throwable t){
        t.printStackTrace();
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //queries for changing database between versions
        }catch (Throwable t){
            t.printStackTrace();
        }

    }


    //----------------------START OF-Methods queries and actions----------------------------
    /*********methods go in here***********/

    //----------------------END OF-Methods queries and actions----------------------------

    public void open(){
        try{
            db=getWritableDatabase();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public void close(){
        try {
            db.close();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }
}
