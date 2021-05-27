package com.androidcourse.energyconsumptiondiary_androidapp.Model;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.androidcourse.energyconsumptiondiary_androidapp.core.Units;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyCo2SQLiteDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyCo2FootprintDB";
    //CO2 impacter table
    private static final String TABLE_CO2IMPACTER_NAME = "co2_impacter";
    private static final String CO2IMPACTER_COLUMN_ID = "impacterID";
    private static final String CO2IMPACTER_COLUMN_NAME = "name";
    private static final String CO2IMPACTER_COLUMN_Fuel = "fuel";
    private static final String CO2IMPACTER_COLUMN_QUESTION = "question";
    private static final String CO2IMPACTER_COLUMN_UNIT = "unit";
    private static final String CO2IMPACTER_COLUMN_CO2AMOUNT = "co2Amount";
    private static final String CO2IMPACTER_COLUMN_IMG = "img";
    private static final String[] TABLE_CO2IMPACTER_COLUMNS = {CO2IMPACTER_COLUMN_ID, CO2IMPACTER_COLUMN_NAME,
            CO2IMPACTER_COLUMN_QUESTION, CO2IMPACTER_COLUMN_UNIT, CO2IMPACTER_COLUMN_CO2AMOUNT, CO2IMPACTER_COLUMN_IMG};
    //Transportation table
    private static final String TABLE_TRANSPORTATION_NAME = "transportation";
    private static final String TRANSPORTATION_COLUMN_IMPACTERID = "co2ImpacterId";//foreign key
    private static final String[] TABLE_TRANSPORTATION_COLUMNS =
            {CO2IMPACTER_COLUMN_ID, CO2IMPACTER_COLUMN_NAME,
                    CO2IMPACTER_COLUMN_QUESTION, CO2IMPACTER_COLUMN_UNIT, CO2IMPACTER_COLUMN_CO2AMOUNT, CO2IMPACTER_COLUMN_IMG,CO2IMPACTER_COLUMN_Fuel};
    //Food table
    private static final String TABLE_FOOD_NAME = "food";
    private static final String FOOD_COLUMN_IMPACTERID = "co2ImpacterId";//foreign key
    //TODO rest of the fields
    private static final String[] TABLE_FOOD_COLUMNS = {CO2IMPACTER_COLUMN_ID, CO2IMPACTER_COLUMN_NAME,
            CO2IMPACTER_COLUMN_QUESTION, CO2IMPACTER_COLUMN_UNIT, CO2IMPACTER_COLUMN_CO2AMOUNT, CO2IMPACTER_COLUMN_IMG};

    //Service table
    private static final String TABLE_SERVICE_NAME = "service";
    private static final String SERVICE_COLUMN_IMPACTERID = "co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_SERVICE_COLUMNS = {CO2IMPACTER_COLUMN_ID, CO2IMPACTER_COLUMN_NAME,
            CO2IMPACTER_COLUMN_QUESTION, CO2IMPACTER_COLUMN_UNIT, CO2IMPACTER_COLUMN_CO2AMOUNT, CO2IMPACTER_COLUMN_IMG};

    //ElectricalHouseSupplies table
    private static final String TABLE_ELECTRICS_NAME = "electrics";
    private static final String ELECTRICS_COLUMN_IMPACTERID = "co2ImpacterId"; //foreign key
    //TODO rest of the fields
    private static final String[] TABLE_ELECTRICS_COLUMNS ={CO2IMPACTER_COLUMN_ID, CO2IMPACTER_COLUMN_NAME,
            CO2IMPACTER_COLUMN_QUESTION, CO2IMPACTER_COLUMN_UNIT, CO2IMPACTER_COLUMN_CO2AMOUNT, CO2IMPACTER_COLUMN_IMG};

    //Tips table
    private static final String TABLE_TIP_NAME = "tip";
    //TODO rest of the fields
    private static final String[] TABLE_TIP_COLUMNS = {};

    //Entry Table
    private static final String TABLE_ENTRY_NAME = "entry";
    //TODO rest of the fields
    private static final String[] TABLE_ENTRY_COLUMNS = {};


    private SQLiteDatabase db = null;

    public MyCo2SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            //create CO2 impacter table
            String CREATE_CO2IMPACTER_TABLE = "create table if not exists " + TABLE_CO2IMPACTER_NAME + "("
                    + CO2IMPACTER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CO2IMPACTER_COLUMN_NAME + " TEXT,"
                    + CO2IMPACTER_COLUMN_QUESTION + " TEXT,"
                    + CO2IMPACTER_COLUMN_UNIT + " TEXT,"
                    + CO2IMPACTER_COLUMN_CO2AMOUNT + " INTEGER,"
                    + CO2IMPACTER_COLUMN_IMG + " BLOB"
                    + ")";
            db.execSQL(CREATE_CO2IMPACTER_TABLE);
            //create Transportation table
            //TODO
            String CREATE_Transportation_TABLE = "create table if not exists " + TABLE_FOOD_NAME + "("
                    + TRANSPORTATION_COLUMN_IMPACTERID + " INTEGER"
                    + ")";
            //create Food table
            String CREATE_FOOD_TABLE = "create table if not exists " + TABLE_FOOD_NAME + "("
                    + FOOD_COLUMN_IMPACTERID + " INTEGER"
                    + ")";
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


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //queries for changing database between versions
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }


    //----------------------START OF-Methods queries and actions----------------------------

    /*********methods go in here***********/

    //----------------------END OF-Methods queries and actions----------------------------
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void createTransportation(Transportation t) {
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(CO2IMPACTER_COLUMN_ID, t.getImpacterID());
            values.put(CO2IMPACTER_COLUMN_NAME, t.getName());
            values.put(CO2IMPACTER_COLUMN_CO2AMOUNT, t.getCo2Amount());
            values.put(CO2IMPACTER_COLUMN_Fuel, t.getFuelType());
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
            values.put(CO2IMPACTER_COLUMN_Fuel, item.getFuelType());
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
    private boolean isTableExist(String name, SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }


}
