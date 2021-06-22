package com.omar.bemyhart.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omar.bemyhart.Models.City_Model;
import com.omar.bemyhart.Models.Country_Model;
import com.omar.bemyhart.Models.State_Model;

import java.util.ArrayList;

public class DataBaseAccess {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DataBaseAccess instance;

    public static final String COUNTRY_TB_NAME = "countries";
    public static final String COUNTRY_CLN_ID = "id";
    public static final String COUNTRY_CLN_NAME = "name";

    public static final String STATE_TB_NAME = "states";
    public static final String STATE_CLN_ID = "id";
    public static final String STATE_CLN_NAME = "name";
    public static final String STATE_CLN_COUNTRY_ID = "country_id";

    public static final String CITY_TB_NAME = "cities";
    public static final String CITY_CLN_ID = "id";
    public static final String CITY_CLN_CITY_NAME = "name_city";
    public static final String CITY_CLN_STATE_ID = "state_id";

    private DataBaseAccess (Context context){
        this.openHelper = new MyDataBaseWorld(context);
    }

    public static DataBaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close(){
        if(this.database != null) {
            this.database.close();
        }
    }

    public ArrayList<Country_Model> getCountries(){

        ArrayList<Country_Model> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+COUNTRY_TB_NAME,null);

        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(2);

                Country_Model model = new Country_Model(name,id);
                list.add(model);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }

    public ArrayList<State_Model> getState(String country_id){

        ArrayList<State_Model> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+STATE_TB_NAME+" WHERE "+STATE_CLN_COUNTRY_ID+"="+country_id,null);

        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);

                State_Model model = new State_Model(name,id);
                list.add(model);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }

    public ArrayList<City_Model> getCity(String state_id){

        ArrayList<City_Model> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+CITY_TB_NAME+" WHERE "+CITY_CLN_STATE_ID+"="+state_id,null);

        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);

                City_Model model = new City_Model(name,id);
                list.add(model);
            }while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }


}
