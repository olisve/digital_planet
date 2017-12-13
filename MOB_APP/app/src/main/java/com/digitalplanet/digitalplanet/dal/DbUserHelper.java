package com.digitalplanet.digitalplanet.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marija.savtchouk on 13.12.2017.
 */

public class DbUserHelper  extends SQLiteOpenHelper {
    public static final String idField = "user_id";
    public static final String nameField = "user_name";
    public static final String lastNameField = "user_lastname";
    public static final String emailField = "email";
    public static final String phoneField = "phone";
    public static final String addressField = "address";
    private String dbName;

    DbUserHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        this.dbName = dbName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createString = "create table " + dbName + " (" +
                idField + " text primary key, " +
                nameField + " text, " +
                lastNameField + " text, " +
                emailField + " text, " +
                phoneField + " text, " +
                addressField + " text); ";
        sqLiteDatabase.execSQL(createString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_version, int new_version) {

    }
}
