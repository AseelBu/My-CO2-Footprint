package com.androidcourse.energyconsumptiondiary_androidapp.Model;

import android.content.Context;

import com.androidcourse.energyconsumptiondiary_androidapp.core.ImpactType;

import java.util.ArrayList;
import java.util.Collections;
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

    public  boolean findIfImpacterIsExists(Co2Impacter c,ImpactType t)
    {
        if(db!=null) {
            return  db.findIfImpacterIsExists(c,t);
        }
        return false;
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
            case ELECTRICAL:
                return selectedElectricalHouseSupplies;
            case SERVICES:
                return selectedService;
            case FOOD:
                return selectedFood;
            case TRANSPORTATION:
                return selectedTransportation;
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
        if(c == null){
            selectedTransportation=null;
            selectedFood=null;
            selectedElectricalHouseSupplies=null;
            selectedService=null;
        }
    }
public void removeImpacter(ImpactType impacterType,String id)
    {
       if(db!=null){
           db.removeImpacter(impacterType,id);
        }

    }
    //---co2 impacter
    public Co2Impacter getImpacterById(String impacterId) {
        if (db != null) {
            return db.getImpacterById(impacterId);
        }
        return null;
    }

    public void createCO2Impacter(Co2Impacter item) {
        if (db != null) {
           db.createCO2Impacter(item);
        }
    }

    public Co2Impacter readCO2Impacter(String id) {
        Co2Impacter result = null;
        if (db != null) {
         db.readCO2Impacter(id);
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
    public void removeAllCo2Impacter() {
        if(db!=null){
            db.deleteAllCo2Impacter();
        }
    }

    public int updateCo2Impacter(Co2Impacter item) {
        int i=-1;
        if (db != null && item != null) {
            i=db.updateCO2Impacter(item);
        }
        return i;
    }
    public int deleteCo2Impacter(String item) {
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
        if(db !=null) {
            return db.getImpactersByType(impacterType);
        }
        return new ArrayList<>();
    }
    public int createTransportation(String id,Transportation item) {
        if (db != null) {
            db.createTransportation(id,item);
        }
        return  -1;
    }
    public int createFood(String id,Food item) {
        if (db != null) {
            db.createFood(id,item);
        }
        return  -1;
    }
    public int createElectric(String id,ElectricalHouseSupplies item) {
        if (db != null) {
            db.createElectric(id,item);
        }
        return  -1;
    }
    public int createService(String id,Service item) {
        if (db != null) {
            db.createService(id,item);
        }
        return  -1;
    }

    public Transportation readTransporation(String id) {
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
    public void deleteTransportation(String item) {
        if (db != null) {
            db.deleteTransportation(item);
        }
    }
    public void deleteElectric(String item) {
        if (db != null) {
            db.deleteElectric(item);
        }
    }
    public void deleteService(String item) {
        if (db != null) {
            db.deleteService(item);
        }
    }
    public void deleteFood(String item) {
        if (db != null) {
            db.deleteFood(item);
        }
    }
    //------ type Entry

    public void createTypeEntry(String entryId,TypeEntry typeEntry) {
        if (db != null) {
            db.createTypeEntry(entryId,typeEntry);
        }
    }

    public void deleteAllTypeEntryByEntryID(String entryId) {
        if (db != null) {
            db.deleteAllTypeEntryByEntryID(entryId);
        }
    }
    //------- Entry
    public void createEntry(Entry entry) {
        if (db != null) {
            db.createEntry(entry);
        }

    }

    //-------Result
    public void createResult(Result result) {
        if (db != null) {
            db.createResult(result);
        }

    }

    public Result getResultById(String resultId) {
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

    public List<Result> getAllResults(String userId,int limit) {
        List<Result> results = new ArrayList<Result>();
        List<Result> kResults = new ArrayList<Result>();
        if (db != null) {
            results = db.getAllResults(userId);
            Collections.sort(results, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            int index= Math.min(limit,results.size());
            for(int i=0;i<index;i++){
                kResults.add(results.get(i));
            }
        }
        return kResults;
    }

    //---------user points
    public void replaceUserPoints(User user) {
        if (db != null) {
            db.replaceUserPoints(user);
        }

    }

    public User getUserInfoPoints(String userId) {
        User user=null;
        if (db != null) {
            user=db.getUserInfoPoints(userId);
        }
        return user;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        if (db != null) {
            users = db.getAllUsers();
        }
        return users;
    }

    public List<User> getTopkUsers(int k) {
        List<User> users = new ArrayList<User>();
        if (db != null) {
            users = db.getTopkUsers(k);
        }
        return users;
    }

    public void removeAllUsers() {
        if(db!=null){
            db.removeAllUsers();
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
