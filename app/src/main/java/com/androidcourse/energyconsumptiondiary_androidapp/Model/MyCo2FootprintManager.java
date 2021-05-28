package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;
import android.util.Log;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

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

    //-----------------------co2 impacter
    public Co2Impacter getSelectedCO2Impacter(ImpactType c) {
        switch (c) {
            case ELECTRICAL: return selectedElectricalHouseSupplies;
            case SERVICES: return selectedService;
            case FOOD:return selectedFood;
            case TRANSPORTATIOIN: return selectedTransportation;
        }
        return null;
    }
    public void setSelectedCO2Impacter(Co2Impacter c) {
       if(c instanceof Transportation)
       {
           selectedTransportation=(Transportation)c;
       }
        if(c instanceof Food)
        {
            selectedFood=(Food)c;
        }
        if(c instanceof ElectricalHouseSupplies)
        {
            selectedElectricalHouseSupplies=(ElectricalHouseSupplies) c;
        }
        if(c instanceof Service)
        {
            selectedService=(Service) c;
        }
    }
public void removeImpacter(ImpactType impacterType,int id)
    {
        switch (impacterType)
        {
            case ELECTRICAL: deleteElectric(id);break;
            case FOOD:deleteFood(id); break;
            case SERVICES:deleteService(id);break;
            case TRANSPORTATIOIN:deleteTransportation(id);
        }
        deleteCo2Impacter(id);

    }

    public int createCO2Impacter(Co2Impacter item) {
        int i=-1;
        if (db != null) {
            i=db.createCO2Impacter(item);
        }
        return i;
    }

    public Co2Impacter readCO2Impacter(int id) {
        Co2Impacter result = null;
        if (db != null) {
            result = db.readCO2Impacter(id);
        }
        return result;
    }

    public List<Co2Impacter> getAllCo2Impacter() {
        List<Co2Impacter> result = new ArrayList<Co2Impacter>();
        if (db != null) {
            result = db.getAllCO2Impacter();
        }
        return result;
    }
    public int updateCo2Impacter(Co2Impacter item) {
        int i=-1;
        if (db != null && item != null) {
            i=db.updateCO2Impacter(item);
        }
        return i;
    }
    public int deleteCo2Impacter(int item) {
        int i=-1;
        if (db != null) {
            i=db.deleteCO2Impacter(item);
        }
        return i;
    }
    //----------- transportation
    public Transportation getSelectedTransporatation() {
        return selectedTransportation;
    }

    public ArrayList<Co2Impacter> getImpactersByType(ImpactType impacterType)
    {
        if(db !=null)
        return db.getImpactersByType(impacterType);
        return new ArrayList<>();
    }
    public int createTransportation(int id,Transportation item) {
        if (db != null) {
            db.createTransportation(id,item);
        }
        return  -1;
    }

    public Transportation readTransporation(int id) {
        Transportation result = null;
        if (db != null) {
            result = db.readTransportation(id);
        }
        return result;
    }

    public ArrayList<Co2Impacter> getAllTransportations() {
        ArrayList<Co2Impacter> result = new ArrayList<>();
        if (db != null) {
            result = db.getAllTransportations();
        }
        return result;
    }
    public void updateTransportation(int id,Transportation item) {
        if (db != null && item != null) {
            db.updateTransportation(item);
        }
    }
    public void deleteTransportation(int item) {
        if (db != null) {
            db.deleteTransportation(item);
        }
    }
    public void deleteElectric(int item) {
        if (db != null) {
            db.deleteElectric(item);
        }
    }
    public void deleteService(int item) {
        if (db != null) {
            db.deleteService(item);
        }
    }
    public void deleteFood(int item) {
        if (db != null) {
            db.deleteFood(item);
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
    public void createResult(Result result) {
        if (db != null) {
            db.createResult(result);
        }
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
