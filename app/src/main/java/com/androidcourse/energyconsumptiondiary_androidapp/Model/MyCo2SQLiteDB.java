package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    private static final int DATABASE_VERSION =8;
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

        //create ElectricalHouseSupplies table
        //TODO

        //create Tips table
        //TODO


        //create Entry Type Table
        String CREATE_ENTRY_TYPE_TABLE="create table if not exists "+TABLE_TYPE_ENTRY_NAME+"("
                + TYPE_ENTRY_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TYPE_ENTRY_COLUMN_VALUE +" INTEGER,"
                + TYPE_ENTRY_COLUMN_IMPACTER_TYPE +" TEXT,"
                + TYPE_ENTRY_COLUMN_ENTRYID +" INTEGER"
                +")";
        db.execSQL(CREATE_ENTRY_TYPE_TABLE);

        //create Entries Table
        String CREATE_ENTRY_TABLE="create table if not exists "+TABLE_ENTRY_NAME+"("
                +ENTRY_COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ENTRY_COLUMN_USERID+" INTEGER,"
                +ENTRY_COLUMN_DATE+" TEXT"
                +")";
        db.execSQL(CREATE_ENTRY_TABLE);

        //create result table
        String CREATE_RESULT_TABLE="create table if not exists "+ TABLE_RESULT_NAME +"("
                + RESULT_COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +RESULT_COLUMN_USERID+" INTEGER,"
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
    //---Co2impacter


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
                    case TRANSPORTATIOIN:
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

                //images
                byte[] imgByte = cursor.getBlob(6);
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
            impacterType= ImpactType.TRANSPORTATIOIN;

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

    //---transportation queries
    public void createTransportation(Transportation t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(TRANSPORTATION_COLUMN_IMPACTERID,t.getImpacterID());
            values.put(TRANSPORTATION_COLUMN_FUEL, t.getFuelType());


            values.put(CO2IMPACTER_COLUMN_NAME, t.getName());
            values.put(CO2IMPACTER_COLUMN_CO2AMOUNT, t.getCo2Amount());

            values.put(CO2IMPACTER_COLUMN_QUESTION, t.getQuestion());
            values.put(CO2IMPACTER_COLUMN_UNIT, t.getUnit().toString());


            //images
            Bitmap image1 = t.getImg();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(CO2IMPACTER_COLUMN_IMG, data);
                }
            }

            // insert item
            db.insert(TABLE_TRANSPORTATION_NAME, null, values);

        } catch (Throwable e) {
            e.printStackTrace();
        }


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
                item.setImpacterID(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setCo2Amount(cursor.getInt(2));
                item.setQuestion(cursor.getString(3));
                item.setUnit(Units.valueOf(cursor.getString(4)));
                item.setFuelType(cursor.getString(5));

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

    public List<Transportation> getAllTransportations() {
        List<Transportation> result = new ArrayList<Transportation>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_TRANSPORTATION_NAME, TABLE_TRANSPORTATION_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Transportation item = cursorToTransportation(cursor);
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

    private Transportation cursorToTransportation(Cursor cursor) {
        Transportation result = new Transportation();
        try {
            result .setImpacterID(cursor.getInt(0));
            result .setName(cursor.getString(1));
            result .setCo2Amount(cursor.getInt(2));
            result .setQuestion(cursor.getString(4));
            result .setUnit(Units.valueOf(cursor.getString(5)));
            result .setFuelType(cursor.getString(6));

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

    public int updateTransportation(Transportation item) {
        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues values= new ContentValues();
//            values.put(CO2IMPACTER_COLUMN_ID, item.getImpacterID());
            values.put(CO2IMPACTER_COLUMN_NAME, item.getName());
            values.put(CO2IMPACTER_COLUMN_CO2AMOUNT, item.getCo2Amount());
            values.put(TRANSPORTATION_COLUMN_FUEL, item.getFuelType());
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
            cnt = db.update(TABLE_TRANSPORTATION_NAME, values, CO2IMPACTER_COLUMN_CO2AMOUNT + " = ?",
                    new String[] { String.valueOf(item.getImpacterID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }
    public void deleteTransportation(Transportation item) {

        try {

            // delete item
            db.delete(TABLE_TRANSPORTATION_NAME, TRANSPORTATION_COLUMN_IMPACTERID+ " = ?",
                    new String[] { String.valueOf(item.getImpacterID()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    //---Entry type queries
    public void createTypeEntry(int entryId,TypeEntry typeEntry){

        try {
            ContentValues values = new ContentValues();
            values.put(TYPE_ENTRY_COLUMN_VALUE,typeEntry.getValue());
            values.put(TYPE_ENTRY_COLUMN_IMPACTER_TYPE,typeEntry.getType().name());
            values.put(TYPE_ENTRY_COLUMN_ENTRYID,entryId);

            // insert item
            db.insert(TABLE_TYPE_ENTRY_NAME, null, values);

        }catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //---Entry queries
    public int createEntry(Entry entry){
        long entryId=-1;
        try {
            ContentValues values = new ContentValues();
            values.put(ENTRY_COLUMN_USERID,entry.getUserId());
            values.put(ENTRY_COLUMN_DATE,entry.getDate().toString());
            // insert item
            entryId=db.insert(TABLE_ENTRY_NAME, null, values);

        }catch (Throwable t) {
            t.printStackTrace();
        }
        return Long.valueOf(entryId).intValue();
    }

    //------Result Queries
    public int createResult(Result result) {
        int id=-1;
        try {
            ContentValues values = new ContentValues();
            values.put(RESULT_COLUMN_USERID,result.getUserId());
            values.put(RESULT_COLUMN_DATE,result.getDate().toString());
            values.put(RESULT_COLUMN_TRANSPORTATION,result.getTransportationResult());
            values.put(RESULT_COLUMN_FOOD,result.getFoodResult());
            values.put(RESULT_COLUMN_ELECTRICS,result.getElectricsResult());
            values.put(RESULT_COLUMN_SERVICE,result.getServicesResult());

            // insert result
            id=Long.valueOf(db.insert(TABLE_RESULT_NAME, null, values)).intValue();
            return id;
        }catch (Throwable t) {
            t.printStackTrace();
        }
        return id;
    }

    public Result getResultById(int resultId){
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
     * get last k results for specific user
     * @param userId
     * @param k  the wanted amount of results
     * @return List<Result>
     */
    public List<Result> getAllResults(int userId,int k) {
        List<Result> results = new ArrayList<Result>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_RESULT_NAME, TABLE_RESULT_COLUMNS, RESULT_COLUMN_USERID+"= ? ",new String[] { String.valueOf(userId) } ,
                    null, null, RESULT_COLUMN_DATE +" DESC ",String.valueOf(k));

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
            result.setId(cursor.getInt(0));
            //item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ITEM_COLUMN_ID))));
            result.setUserId(cursor.getInt(1));
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
