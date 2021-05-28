package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyCo2FootprintManager {

    private static MyCo2FootprintManager instance = null;
    private Context context = null;
    private MyCo2SQLiteDB db = null;
    private Transportation selectedTransportation = null;
    private Food selectedFood=null;
    private ElectricalHouseSupplies selectedElectricalHouseSupplies=null;
    private Service selectedService=null;

    public static MyCo2FootprintManager getInstance() {
        if (instance == null) {
            instance = new MyCo2FootprintManager();
        }
        return instance;
    }


    public static void releaseInstance() {
        if (instance != null) {
            instance.clean();
            instance = null;
        }
    }

    private void clean() {

    }
    public Context getContext() {
        return context;

    }

    /**********************START-QUERIES***************************/

    //---co2 impacter
    public Co2Impacter getImpacterById(int impacterId) {
        if (db != null) {
            return db.getImpacterById(impacterId);
        }
        return null;
    }

    //--- transportation
    public Transportation getSelectedTransporatation() {
        return selectedTransportation;
    }


    public void createTransportation(Transportation item) {
        if (db != null) {
            db.createTransportation(getSelectedTransporatation());
        }
    }
    public Transportation readTransporation(int id) {
        Transportation result = null;
        if (db != null) {
            result = db.readTransportation(id);
        }
        return result;
    }

    public List<Transportation> getAllTransportations() {
        List<Transportation> result = new ArrayList<Transportation>();
        if (db != null) {
            result = db.getAllTransportations();
        }
        return result;
    }
    public void updateTransportation(Transportation item) {
        if (db != null && item != null) {
            db.updateTransportation(item);
        }
    }
    public void deleteTransportation(Transportation item) {
        if (db != null) {
            db.deleteTransportation(item);
        }
    }

    //------ type Entry

    public void createTypeEntry(int entryId,TypeEntry typeEntry) {
        if (db != null) {
            db.createTypeEntry(entryId,typeEntry);
        }
    }
    //------- Entry
    public int createEntry(Entry entry) {
        if (db != null) {
            return db.createEntry(entry);
        }
        return -1;
    }

    //-------Result
    public int createResult(Result result) {
        if (db != null) {
            return db.createResult(result);
        }
        return -1;
    }

    public Result getResultById(int resultId) {
        Result result = null;
        if (db != null) {
            result = db.getResultById(resultId);
        }
        return result;
    }
    public List<Result> getAllResults() {
        List<Result> result = new ArrayList<Result>();
        if (db != null) {
            result = db.getAllResults();
        }
        return result;
    }

    /**********************END-QUERIES***************************/

    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new MyCo2SQLiteDB(context);
            db.open();
        }
    }
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

}
