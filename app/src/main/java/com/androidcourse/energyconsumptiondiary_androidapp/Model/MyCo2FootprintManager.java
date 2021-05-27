package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;

public class MyCo2FootprintManager {

    private static MyCo2FootprintManager instance = null;
    private Context context = null;
    private MyCo2SQLiteDB db = null;

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

    /**********************START-QUERIES***************************/


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
