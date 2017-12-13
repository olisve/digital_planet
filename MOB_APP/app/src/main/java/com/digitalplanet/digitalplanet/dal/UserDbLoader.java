package com.digitalplanet.digitalplanet.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by marija.savtchouk on 13.12.2017.
 */

public class UserDbLoader {
    DbUserHelper dbHelper;
    public final static String dbName = "digitalplanetuser";

    public UserDbLoader(Context context) {
        dbHelper = new DbUserHelper(context, dbName, 1);
    }

    public User getUser() {
        String selectQuery = "SELECT * FROM " + dbName;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        getUsersFromCursor(cursor, users);
        cursor.close();
        dbHelper.close();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    private void getUsersFromCursor(Cursor c, ArrayList<User> outItems) {
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(DbUserHelper.idField);
            int nameColIndex = c.getColumnIndex(DbUserHelper.nameField);
            int lastNameColIndex= c.getColumnIndex(DbUserHelper.lastNameField);
            int emailColIndex= c.getColumnIndex(DbUserHelper.emailField);
            int phoneColIndex= c.getColumnIndex(DbUserHelper.phoneField);
            int addressColIndex = c.getColumnIndex(DbUserHelper.addressField);
            do {
                User user = new User();
                user.set_id(c.getString(idColIndex));
                user.setFirstName(c.getString(nameColIndex));
                user.setLastName(c.getColumnName(lastNameColIndex));
                user.setEmail(c.getColumnName(emailColIndex));
                user.setPhone(c.getColumnName(phoneColIndex));
                user.setAddress(c.getColumnName(addressColIndex));
                outItems.add(user);
            } while (c.moveToNext());
        }
    }

    public void saveUser(User user){
        removeUser();
        ContentValues cv = new ContentValues();
        cv.put(DbUserHelper.idField, user.get_id());
        cv.put(DbUserHelper.nameField, user.getFirstName());
        cv.put(DbUserHelper.lastNameField, user.getLastName());
        cv.put(DbUserHelper.emailField, user.getEmail());
        cv.put(DbUserHelper.phoneField, user.getPhone());
        cv.put(DbUserHelper.addressField, user.getAddress());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(dbName, null, cv);
        dbHelper.close();
    }

    public void removeUser() {
        User user = getUser();
        if(user!=null) {
            String selectQuery = "DELETE FROM " + dbName + " WHERE " + DbUserHelper.idField + " = ?";
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(selectQuery, new String[]{user.get_id()});
            db.close();
        }
    }
}
