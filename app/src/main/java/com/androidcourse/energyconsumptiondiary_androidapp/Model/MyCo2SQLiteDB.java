package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyCo2SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =6;
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
    private static final String TRANSPORTATION_COLUMN_FUEL = "fuel";
    private static final String[] TABLE_TRANSPORTATION_COLUMNS ={TRANSPORTATION_COLUMN_IMPACTERID, TRANSPORTATION_COLUMN_FUEL};

    //Food table
    private static final String TABLE_FOOD_NAME = "food";
    private static final String FOOD_COLUMN_IMPACTERID="co2ImpacterId";//foreign key
    //TODO rest of the fields
    private static final String[] TABLE_FOOD_COLUMNS ={FOOD_COLUMN_IMPACTERID};

    //Service table
    private static final String TABLE_SERVICE_NAME = "service";
    private static final String SERVICE_COLUMN_IMPACTERID="co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_SERVICE_COLUMNS ={SERVICE_COLUMN_IMPACTERID};

    //ElectricalHouseSupplies table
    private static final String TABLE_ELECTRICS_NAME = "electrics";
    private static final String ELECTRICS_COLUMN_IMPACTERID="co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_ELECTRICS_COLUMNS ={ELECTRICS_COLUMN_IMPACTERID};

    //Tips table
    private static final String TABLE_TIP_NAME = "tip";
    //TODO rest of the fields
    private static final String[] TABLE_TIP_COLUMNS ={};


    //Entry Type Table
    private static final String TABLE_TYPE_ENTRY_NAME = "entry_type";
    private static final String TYPE_ENTRY_COLUMN_ID ="id";
    private static final String TYPE_ENTRY_COLUMN_VALUE ="value";
    private static final String TYPE_ENTRY_COLUMN_IMPACTER_TYPE ="impacter_type";
    private static final String TYPE_ENTRY_COLUMN_ENTRYID ="entryId";
    private static final String[] TABLE_TYPE_ENTRY_COLUMNS ={TYPE_ENTRY_COLUMN_ID, TYPE_ENTRY_COLUMN_VALUE, TYPE_ENTRY_COLUMN_IMPACTER_TYPE, TYPE_ENTRY_COLUMN_ENTRYID};

    //Entry Table
    private static final String TABLE_ENTRY_NAME ="entry";

    private static final String ENTRY_COLUMN_ID ="id";
    private static final String ENTRY_COLUMN_USERID ="userId";
    private static final String ENTRY_COLUMN_DATE ="entry_date";
    private static final String[] TABLE_ENTRY_COLUMNS ={ENTRY_COLUMN_ID,ENTRY_COLUMN_USERID,ENTRY_COLUMN_DATE};

    //Result Table
    private static final String TABLE_RESULT_NAME = "results";
    private static final String RESULT_COLUMN_ID ="id";
    private static final String RESULT_COLUMN_USERID ="userId";
    private static final String RESULT_COLUMN_DATE ="result_date";
    private static final String RESULT_COLUMN_TRANSPORTATION ="transportation_result";
    private static final String RESULT_COLUMN_FOOD ="food_result";
    private static final String RESULT_COLUMN_ELECTRICS ="electrics_result";
    private static final String RESULT_COLUMN_SERVICE ="service_result";
    private static final String[] TABLE_RESULT_COLUMNS ={RESULT_COLUMN_ID,RESULT_COLUMN_USERID,RESULT_COLUMN_DATE,RESULT_COLUMN_TRANSPORTATION,RESULT_COLUMN_FOOD,RESULT_COLUMN_ELECTRICS,RESULT_COLUMN_SERVICE};

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
        String CREATE_TRANSPORTATION_TABLE = "create table if not exists " + TABLE_TRANSPORTATION_NAME + "("
                + TRANSPORTATION_COLUMN_IMPACTERID + " INTEGER,"
                + TRANSPORTATION_COLUMN_FUEL + " TEXT"
                + ")";
        db.execSQL(CREATE_TRANSPORTATION_TABLE);
        //create Food table
        String CREATE_FOOD_TABLE="create table if not exists "+TABLE_FOOD_NAME+"("
                +FOOD_COLUMN_IMPACTERID+" INTEGER"
                +")";
        db.execSQL(CREATE_FOOD_TABLE);
        //TODO

        //create Service table
        //TODO
        String CREATE_SERVICE_TABLE="create table if not exists "+TABLE_SERVICE_NAME+"("
                +SERVICE_COLUMN_IMPACTERID+" INTEGER"
                +")";
        db.execSQL(CREATE_SERVICE_TABLE);
        //create ElectricalHouseSupplies table
        //TODO
        String CREATE_Electric_TABLE="create table if not exists "+TABLE_ELECTRICS_NAME+"("
                +ELECTRICS_COLUMN_IMPACTERID+" INTEGER"
                +")";
        db.execSQL(CREATE_Electric_TABLE);
        //create Tips table
        //TODO


        //create Entry Type Table
        String CREATE_ENTRY_TYPE_TABLE="create table if not exists "+TABLE_TYPE_ENTRY_NAME+"("
                + TYPE_ENTRY_COLUMN_ID +" INTEGER , "
                + TYPE_ENTRY_COLUMN_VALUE +" INTEGER,"
                + TYPE_ENTRY_COLUMN_IMPACTER_TYPE +" TEXT,"
                + TYPE_ENTRY_COLUMN_ENTRYID +" TEXT,"
                + "PRIMARY KEY ("+TYPE_ENTRY_COLUMN_ID+","+TYPE_ENTRY_COLUMN_ENTRYID+")"
                +")";
        db.execSQL(CREATE_ENTRY_TYPE_TABLE);

        //create Entries Table
        String CREATE_ENTRY_TABLE="create table if not exists "+TABLE_ENTRY_NAME+"("
                +ENTRY_COLUMN_ID+" TEXT PRIMARY KEY, "
                +ENTRY_COLUMN_USERID+" TEXT,"
                +ENTRY_COLUMN_DATE+" TEXT"
                +")";
        db.execSQL(CREATE_ENTRY_TABLE);

        //create result table
        String CREATE_RESULT_TABLE="create table if not exists "+ TABLE_RESULT_NAME +"("
                + RESULT_COLUMN_ID +" TEXT PRIMARY KEY , "
                +RESULT_COLUMN_USERID+" TEXT,"
                +RESULT_COLUMN_DATE+" TEXT,"
                +RESULT_COLUMN_TRANSPORTATION+" INTEGER,"
                +RESULT_COLUMN_FOOD+" INTEGER,"
                +RESULT_COLUMN_ELECTRICS+" INTEGER,"
                +RESULT_COLUMN_SERVICE+" INTEGER"
                +")";
        db.execSQL(CREATE_RESULT_TABLE);


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
    //---Co2impacter-----------


    public Co2Impacter getImpacterById(int impacterId) {
        Co2Impacter co2Impacter = null;
        Cursor cursor = null;
        ImpactType type =getImpacterTypeById(impacterId);
        try {
            /// get reference of the itemDB database
            cursor = db
                    .query(TABLE_CO2IMPACTER_NAME,
                            TABLE_CO2IMPACTER_COLUMNS, CO2IMPACTER_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(impacterId) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){

                cursor.moveToFirst();
                switch (type){
                    case TRANSPORTATION:
                        co2Impacter = new Transportation();
                        break;
                    case FOOD:
                        co2Impacter=new Food();
                        break;
                    case ELECTRICAL:
                        co2Impacter=new ElectricalHouseSupplies();
                        break;
                    case SERVICES:
                        co2Impacter=new Service();
                        break;
                }

                co2Impacter.setImpacterID(cursor.getInt(0));
                //item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_COLUMN_ID))));
                co2Impacter.setName(cursor.getString(1));
                co2Impacter.setQuestion(cursor.getString(2));
                co2Impacter.setUnit(Units.valueOf(cursor.getString(3)));
                co2Impacter.setCo2Amount(cursor.getInt(4));

                if(type.equals(ImpactType.TRANSPORTATION)){
                    ((Transportation)co2Impacter).setFuelType(getTransportationFuel(co2Impacter.getImpacterID()));
                }

                //images
                byte[] imgByte = cursor.getBlob(5);
                if (imgByte != null && imgByte.length > 0) {
                    Bitmap image = BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                    if (image != null) {
                        co2Impacter.setImg(image);
                    }
                }

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return co2Impacter;
    }

    public ImpactType getImpacterTypeById(int impacterId) {
        // get  query
        ImpactType impacterType=null;
        Cursor cursor = null;
        try {
            impacterType= ImpactType.TRANSPORTATION;

        //check in transportation table
        cursor = db
                .query(TABLE_TRANSPORTATION_NAME,
                        TABLE_TRANSPORTATION_COLUMNS, TRANSPORTATION_COLUMN_IMPACTERID + " = ?",
                        new String[] { String.valueOf(impacterId) }, null, null,
                        null, null);
        //check in food table
        if(cursor!=null && cursor.getCount()==0) {
            impacterType = ImpactType.FOOD;

            cursor = db
                    .query(TABLE_FOOD_NAME,
                            TABLE_FOOD_COLUMNS, FOOD_COLUMN_IMPACTERID + " = ?",
                            new String[] { String.valueOf(impacterId) }, null, null,
                            null, null);
            //check in electrics table
            if (cursor != null && cursor.getCount() == 0) {
                impacterType = ImpactType.ELECTRICAL;
                cursor = db
                        .query(TABLE_ELECTRICS_NAME,
                                TABLE_ELECTRICS_COLUMNS, ELECTRICS_COLUMN_IMPACTERID + " = ?",
                                new String[] { String.valueOf(impacterId) }, null, null,
                                null, null);
                //check in service table
                if (cursor != null && cursor.getCount() == 0) {
                    impacterType = ImpactType.SERVICES;
                    cursor = db
                            .query(TABLE_SERVICE_NAME,
                                    TABLE_SERVICE_COLUMNS, SERVICE_COLUMN_IMPACTERID + " = ?",
                                    new String[] { String.valueOf(impacterId) }, null, null,
                                    null, null);
                    if(cursor!=null && cursor.getCount()>0){
                        return impacterType;
                    }

                }else if(cursor!=null && cursor.getCount()>0){
                    return impacterType;
                }
            }else if(cursor!=null && cursor.getCount()>0){
                return impacterType;
            }
        }
        else if(cursor!=null && cursor.getCount()>0){
            return impacterType;
        }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    return impacterType;
    }

    //--trans
    public String getTransportationFuel(int id) {
        String fuelType =" ";
        Cursor cursor = null;

        try {
            /// get reference of the itemDB database
            cursor = db
                    .query(TABLE_TRANSPORTATION_NAME,
                            TABLE_TRANSPORTATION_COLUMNS, TRANSPORTATION_COLUMN_IMPACTERID + " = ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){
                fuelType=cursor.getString(1);
            }} catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return fuelType;
    }


    //-----------------------------------transportation queries
    public void createTransportation(int id,Transportation t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(TRANSPORTATION_COLUMN_FUEL, t.getFuelType());
            values.put(TRANSPORTATION_COLUMN_IMPACTERID, id);

            // insert item
            db.insert(TABLE_TRANSPORTATION_NAME, null, values);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void createFood(int id, Food t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(FOOD_COLUMN_IMPACTERID, id);

            // insert item
            db.insert(TABLE_FOOD_NAME, null, values);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void createElectric(int id,ElectricalHouseSupplies t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(ELECTRICS_COLUMN_IMPACTERID, id);
            // insert item
            db.insert(TABLE_ELECTRICS_NAME, null, values);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void createService(int id,Service t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(SERVICE_COLUMN_IMPACTERID, id);
            // insert item
            db.insert(TABLE_SERVICE_NAME, null, values);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }




    public ArrayList<Co2Impacter> getImpactersByType(ImpactType impacterType)
    {
        ArrayList<Co2Impacter> list=new ArrayList<>();
        switch(impacterType) {
        case TRANSPORTATION:
            list=getAllTransportations();
            break;
        case ELECTRICAL:
            list=getAllElectric();
            break;
        case FOOD:
            list=getAllFood();
            break;
        case SERVICES:
            list=getAllService();
            break;
    }
    return list;
    }

    public Transportation readTransportation(int id) {
        Transportation item = null;
        Cursor cursor = null;
        try {
            // get  query
            cursor = db
                    .query(TABLE_TRANSPORTATION_NAME,
                            TABLE_CO2IMPACTER_COLUMNS, TRANSPORTATION_COLUMN_IMPACTERID + " = ?",
                            new String[]{String.valueOf(id)}, null, null,
                            null, null);
            // if results !=null, parse the first one
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                item = new Transportation();
                item.setFuelType(cursor.getString(0));
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return item;
    }

    public ArrayList<Co2Impacter> getAllTransportations() {
        ArrayList<Co2Impacter> result = new ArrayList<Co2Impacter>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_TRANSPORTATION_NAME, TABLE_TRANSPORTATION_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id=cursor.getInt(0);
                Co2Impacter item=(Transportation)getImpacterById(id);
                ((Transportation)item).setFuelType(cursor.getString(1));
                result.add(item);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    public ArrayList<Co2Impacter> getAllFood() {
        ArrayList<Co2Impacter> result = new ArrayList<Co2Impacter>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_FOOD_NAME, TABLE_FOOD_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id=cursor.getInt(0);
                result.add(getImpacterById(id));
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public ArrayList<Co2Impacter> getAllElectric() {
        ArrayList<Co2Impacter> result = new ArrayList<Co2Impacter>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_ELECTRICS_NAME, TABLE_ELECTRICS_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id=cursor.getInt(0);
                result.add(getImpacterById(id));
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public ArrayList<Co2Impacter> getAllService() {
        ArrayList<Co2Impacter> result = new ArrayList<Co2Impacter>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_SERVICE_NAME, TABLE_SERVICE_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id=cursor.getInt(0);
                result.add(getImpacterById(id));
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

   public void removeImpacter(ImpactType impacterType, int id)
   {
       try {
           switch (impacterType)
           {
               case TRANSPORTATION:
                   deleteTransportation(id);
                   break;
               case FOOD:
                   deleteFood(id);
                   break;
               case SERVICES:
                   deleteService(id);
                   break;
               case ELECTRICAL:
                   deleteElectric(id);
                   break;
           }
           deleteCO2Impacter(id);
       } catch (Throwable t) {
           t.printStackTrace();
       }
   }

    private Food cursorToFood(Cursor cursor) {
        cursorToCO2Impacter(cursor);
        Food result = new Food();
        try {
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }
    private ElectricalHouseSupplies cursorToElectric(Cursor cursor) {
        cursorToCO2Impacter(cursor);
        ElectricalHouseSupplies result = new ElectricalHouseSupplies();
        try {
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }
    private Service cursorToService(Cursor cursor) {
        cursorToCO2Impacter(cursor);
        Service result = new Service();
        try {
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    private Transportation cursorToTransportation(Cursor cursor) {
        cursorToCO2Impacter(cursor);
        Transportation result = new Transportation();
        try {
            result .setFuelType(cursor.getString(0));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }

    public int updateTransportation(Transportation item) {
        updateCO2Impacter(item);
        int cnt = -1;
        try {
            // make values to be inserted
            ContentValues values= new ContentValues();
            values.put(TRANSPORTATION_COLUMN_FUEL, item.getFuelType());
            // update
            cnt = db.update(TABLE_TRANSPORTATION_NAME, values, TRANSPORTATION_COLUMN_IMPACTERID + " = ?",
                    new String[] { String.valueOf(item.getImpacterID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return cnt;
    }
    public void deleteTransportation(int id) {
        try {
            // delete item
            db.delete(TABLE_TRANSPORTATION_NAME, TRANSPORTATION_COLUMN_IMPACTERID+ " = ?",
                    new String[] { String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void deleteService(int id) {
        try {
            // delete item
            db.delete(TABLE_SERVICE_NAME, SERVICE_COLUMN_IMPACTERID+ " = ?",
                    new String[] { String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public void deleteFood(int id) {
        try {
            // delete item
            db.delete(TABLE_FOOD_NAME, FOOD_COLUMN_IMPACTERID+ " = ?",
                    new String[] { String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    public void deleteElectric(int id) {
        try {
            // delete item
            db.delete(TABLE_ELECTRICS_NAME, ELECTRICS_COLUMN_IMPACTERID+ " = ?",
                    new String[] { String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public boolean findIfImpacterIsExists(Co2Impacter impacter,ImpactType imp)
    {
        List<Co2Impacter> array;
                array=getAllCO2Impacter();
                for(Co2Impacter c:array)
                {
                    if(c.getName().equals(impacter.getName())) return true;
                }

        return false;
    }

    //-----------------------------------CO2Impacter queries
    public int createCO2Impacter(Co2Impacter t) {
        long entryId=-1;
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(CO2IMPACTER_COLUMN_NAME, t.getName());
            values.put(CO2IMPACTER_COLUMN_CO2AMOUNT, t.getCo2Amount());
            values.put(CO2IMPACTER_COLUMN_QUESTION, t.getQuestion());
            values.put(CO2IMPACTER_COLUMN_UNIT, t.getUnit().toString());
            //images
            Bitmap image = t.getImg();
            if (image != null) {
                byte[] data = getBitmapAsByteArray(image);
                if (data != null && data.length > 0) {
                    values.put(CO2IMPACTER_COLUMN_IMG, data);
                }
            }
            // insert item
            entryId=db.insert(TABLE_CO2IMPACTER_NAME, null, values);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return Long.valueOf(entryId).intValue();
    }

    public Co2Impacter  readCO2Impacter(int id) {
        Co2Impacter  item = null;
        Cursor cursor = null;
        try {

            // get  query
            cursor = db
                    .query(TABLE_CO2IMPACTER_NAME,
                            TABLE_CO2IMPACTER_COLUMNS, CO2IMPACTER_COLUMN_ID + " = ?",
                            new String[]{String.valueOf(id)}, null, null,
                            null, null);

            // if results !=null, parse the first one
            if (cursor != null && cursor.getCount() > 0) {

                cursor.moveToFirst();

                item = new Co2Impacter ();
                item.setImpacterID(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setCo2Amount(cursor.getInt(2));
                item.setQuestion(cursor.getString(3));
                item.setUnit(Units.valueOf(cursor.getString(4)));


                //images
                byte[] img1Byte = cursor.getBlob(6);
                if (img1Byte != null && img1Byte.length > 0) {
                    Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
                    if (image1 != null) {
                        item.setImg(image1);
                    }
                }

            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return item;
    }

    public List<Co2Impacter > getAllCO2Impacter() {
        List<Co2Impacter > result = new ArrayList<Co2Impacter >();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CO2IMPACTER_NAME, TABLE_CO2IMPACTER_COLUMNS, null, null,
                    null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Co2Impacter  item = cursorToCO2Impacter(cursor);
                result.add(item);
                cursor.moveToNext();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
    private Co2Impacter  cursorToCO2Impacter(Cursor cursor) {
        Co2Impacter result = new Co2Impacter ();
        try {
            result .setImpacterID(cursor.getInt(0));
            result .setName(cursor.getString(1));
            result .setCo2Amount(cursor.getInt(2));
            result .setQuestion(cursor.getString(4));
            result .setUnit(Units.valueOf(cursor.getString(5)));
            //images
            byte[] img1Byte = cursor.getBlob(3);
            if (img1Byte != null && img1Byte.length > 0) {
                Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
                if (image1 != null) {
                    result.setImg(image1);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    public int updateCO2Impacter(Co2Impacter  item) {
        int cnt = 0;
        try {
            // make values to be inserted
            ContentValues values= new ContentValues();
            values.put(CO2IMPACTER_COLUMN_NAME, item.getName());
            values.put(CO2IMPACTER_COLUMN_CO2AMOUNT, item.getCo2Amount());
            values.put(CO2IMPACTER_COLUMN_QUESTION, item.getQuestion());
            values.put(CO2IMPACTER_COLUMN_UNIT, item.getUnit().toString());

            //images
            Bitmap image1 = item.getImg();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(CO2IMPACTER_COLUMN_IMG, data);
                }
            }
            else{
                values.putNull(CO2IMPACTER_COLUMN_IMG);
            }

            // update
            cnt = db.update(TABLE_CO2IMPACTER_NAME, values, CO2IMPACTER_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(item.getImpacterID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }
    public int deleteCO2Impacter(int id) {
        int cnt = 0;
        try {

            // delete item
            cnt = db.delete(TABLE_CO2IMPACTER_NAME, CO2IMPACTER_COLUMN_ID+ " = ?",
                    new String[] { String.valueOf(id) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return cnt;
    }

    //--------------------------------------------------Entry type queries
    public void createTypeEntry(String entryId,TypeEntry typeEntry){

        try {
            ContentValues values = new ContentValues();
            values.put(TYPE_ENTRY_COLUMN_ID,typeEntry.getId());
            values.put(TYPE_ENTRY_COLUMN_VALUE,typeEntry.getValue());
            values.put(TYPE_ENTRY_COLUMN_IMPACTER_TYPE,typeEntry.getType().name());
            values.put(TYPE_ENTRY_COLUMN_ENTRYID,entryId);

            // insert item
            db.replace(TABLE_TYPE_ENTRY_NAME, null, values);


        }
//        catch (SQLiteConstraintException e){
//            updateTypeEntry(entryId,typeEntry);
//        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void updateTypeEntry(String entryId,TypeEntry typeEntry) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(TYPE_ENTRY_COLUMN_VALUE,typeEntry.getValue());
            values.put(TYPE_ENTRY_COLUMN_IMPACTER_TYPE,typeEntry.getType().name());

            // update
            db.update(TABLE_TYPE_ENTRY_NAME, values, TYPE_ENTRY_COLUMN_ID + " = ? AND "
                            +TYPE_ENTRY_COLUMN_ENTRYID+ " = ?",
                    new String[] { String.valueOf(typeEntry.getId()), String.valueOf(entryId)});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void deleteAllTypeEntryByEntryID(String entryId) {
        try {
            db.delete(TABLE_TYPE_ENTRY_NAME, TYPE_ENTRY_COLUMN_ENTRYID + " = ?",
                    new String[]{String.valueOf(entryId)});
        }catch (Throwable t) {
            t.printStackTrace();
        }
    }
    //---Entry queries
    public void createEntry(Entry entry){

        try {
            ContentValues values = new ContentValues();
            values.put(ENTRY_COLUMN_ID,entry.getId());
            values.put(ENTRY_COLUMN_USERID,entry.getUserId());
            values.put(ENTRY_COLUMN_DATE,entry.getDate().toString());
            // insert item
            db.replace(TABLE_ENTRY_NAME, null, values);

        }
//        catch (SQLiteConstraintException e){
//            updateEntry(entry);
//        }
        catch (Throwable t) {
            t.printStackTrace();
        }
//        return Long.valueOf(entryId).intValue();
    }

    public void updateEntry(Entry entry) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(ENTRY_COLUMN_USERID, entry.getUserId());
            values.put(ENTRY_COLUMN_DATE, entry.getDate().toString());


            // update
            db.update(TABLE_ENTRY_NAME, values, ENTRY_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(entry.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    //------Result Queries
    public void createResult(Result result) {
//        int id=-1;
        try {
            ContentValues values = new ContentValues();
            values.put(RESULT_COLUMN_ID,result.getId());
            values.put(RESULT_COLUMN_USERID,result.getUserId());
            values.put(RESULT_COLUMN_DATE,result.getDate().toString());
            values.put(RESULT_COLUMN_TRANSPORTATION,result.getTransportationResult());
            values.put(RESULT_COLUMN_FOOD,result.getFoodResult());
            values.put(RESULT_COLUMN_ELECTRICS,result.getElectricsResult());
            values.put(RESULT_COLUMN_SERVICE,result.getServicesResult());

            // insert result
            db.replace(TABLE_RESULT_NAME, null, values);
//            id=Long.valueOf(db.insert(TABLE_RESULT_NAME, null, values)).intValue();

        }
//        catch (SQLiteConstraintException e){
//            updateResult(result);
//        }
        catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void updateResult(Result result) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(RESULT_COLUMN_USERID,result.getUserId());
            values.put(RESULT_COLUMN_DATE,result.getDate().toString());
            values.put(RESULT_COLUMN_TRANSPORTATION,result.getTransportationResult());
            values.put(RESULT_COLUMN_FOOD,result.getFoodResult());
            values.put(RESULT_COLUMN_ELECTRICS,result.getElectricsResult());
            values.put(RESULT_COLUMN_SERVICE,result.getServicesResult());


            // update
            db.update(TABLE_RESULT_NAME, values, RESULT_COLUMN_ID + " = ?",
                    new String[] { String.valueOf(result.getId()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Result getResultById(String resultId){
        Result result = null;
        Cursor cursor = null;
        try{

            cursor = db
                    .query(TABLE_RESULT_NAME,
                            TABLE_RESULT_COLUMNS, RESULT_COLUMN_ID + " = ?",
                            new String[] { String.valueOf(resultId) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){

                cursor.moveToFirst();

               result=cursorToResult(cursor);



            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }
        return result;
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<Result>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_RESULT_NAME, TABLE_RESULT_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Result result = cursorToResult(cursor);
                results.add(result);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return results;
    }

    /**
     * get all results for specific user
     * @param userId
     * @return List<Result>
     */
    public List<Result> getAllResults(String userId ){
        List<Result> results = new ArrayList<Result>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_RESULT_NAME, TABLE_RESULT_COLUMNS, RESULT_COLUMN_USERID+"= ? ",new String[] { String.valueOf(userId) } ,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Result result = cursorToResult(cursor);
                results.add(result);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return results;
    }

    private Result cursorToResult(Cursor cursor) {
        Result result = new Result();
        try {
            result.setId(cursor.getString(0));
            //item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_COLUMN_ID))));
            result.setUserId(cursor.getString(1));
            result.setDate(new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(cursor.getString(2)));
            result.setTransportationResult(Integer.valueOf(cursor.getInt(3)).doubleValue());
            result.setFoodResult(Integer.valueOf(cursor.getInt(4)).doubleValue());
            result.setElectricsResult(Integer.valueOf(cursor.getInt(5)).doubleValue());
            result.setServicesResult(Integer.valueOf(cursor.getInt(6)).doubleValue());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }
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
    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


}
