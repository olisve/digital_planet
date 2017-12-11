package com.digitalplanet.digitalplanet.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marija.savtchouk on 11.12.2017.
 */

class DBItemHelper extends SQLiteOpenHelper {
    public static final String itemField = "item_id";
    public static final String countField = "count";
    private String dbName;

    DBItemHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        this.dbName = dbName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createString = "create table " + dbName + " (" +
                itemField + " text primary key, " +
                countField + " integer); ";
        sqLiteDatabase.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {

    }
}