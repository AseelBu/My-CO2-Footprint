package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MyCo2FootprintManager {

    private static MyCo2FootprintManager instance = null;
    private Context context = null;
    private MyCo2SQLiteDB db = null;
    private Transportation transportation = null;
    private Food food=null;
    private ElectricalHouseSupplies electricalHouseSupplies=null;
    private Service service=null;

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
    public Transportation getSelectedTransporatation() {
        return transportation;
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

}
