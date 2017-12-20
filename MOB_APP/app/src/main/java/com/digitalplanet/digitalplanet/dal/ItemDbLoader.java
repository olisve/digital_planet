package com.digitalplanet.digitalplanet.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by marija.savtchouk on 11.12.2017.
 */

public class ItemDbLoader {
    DBItemHelper dbHelper;
    public final static String dbName = "digitalplanetitem";

    public ItemDbLoader(Context context) {
        dbHelper = new DBItemHelper(context, dbName, 1);
    }

    public List<BasketItem> getBasketItems() {
        String selectQuery = "SELECT * FROM " + dbName;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<BasketItem> items = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{});
        getItemsFromCursor(cursor, items);
        cursor.close();
        dbHelper.close();
        return items;
    }

    private void getItemsFromCursor(Cursor c, ArrayList<BasketItem> outItems) {
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(DBItemHelper.itemField);
            int countColIndex = c.getColumnIndex(DBItemHelper.countField);
            do {
                BasketItem item = new BasketItem();
                item.set_id(c.getString(idColIndex));
                item.setCount(c.getInt(countColIndex));
                outItems.add(item);
            } while (c.moveToNext());
        }
    }

    public BasketItem getItemById(String itemId) {
        String selectQuery = "SELECT * FROM " + dbName + " WHERE " + DBItemHelper.itemField + " = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{itemId});
        ArrayList<BasketItem> items = new ArrayList<>();
        getItemsFromCursor(cursor, items);
        cursor.close();
        if (items.isEmpty()) {
            return null;
        } else return items.get(0);
    }

    public void setBasketItem(String basket_id, int value) {
        String selectQuery = "SELECT * FROM " + dbName + " WHERE " + DBItemHelper.itemField + " = ?";
        ContentValues cv = new ContentValues();
        cv.put(DBItemHelper.countField, value);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{basket_id});
        String id;
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(DBItemHelper.itemField));
            db.update(dbName, cv, DBItemHelper.itemField + "= ?", new String[]{id});
        } else {
            cv.put(DBItemHelper.itemField, basket_id);
            db.insert(dbName, null, cv);
        }
        cursor.close();
        dbHelper.close();
    }

    public void removeBasketItem(String basket_id) {
        String selectQuery = "DELETE FROM " + dbName + " WHERE " + DBItemHelper.itemField + " = ?";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(selectQuery, new String[]{basket_id});
        db.close();
    }

    public void cleanBasket() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbName, null, null);
        db.close();
    }
}

