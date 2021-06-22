package com.omar.bemyhart.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDataBaseWorld extends SQLiteAssetHelper {

    public static final String DB_NAME = "world.db";
    public static final int DB_VERSION = 1;

    public MyDataBaseWorld(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

}
