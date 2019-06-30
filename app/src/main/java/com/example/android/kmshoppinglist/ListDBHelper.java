package com.example.android.kmshoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.kmshoppinglist.ListContract.*;

/**
 * Created by Lydia on 15-Mar-18.
 */

public class ListDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ItemsList.db";
    public static final int DATABASE_VERSION = 1;

    ListDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " +
                ItemsList.TABLE_NAME + " (" +
                ItemsList._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ItemsList.COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                ItemsList.COLUMN_ITEM_AMOUNT + " TEXT, " +
                ItemsList.COLUMN_ITEM_UNIT_PRICE + " TEXT, " +
                ItemsList.COLUMN_ITEM_TOTAL_PRICE + " TEXT" +
                ");";


        db.execSQL(SQL_CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //code which drops the db and recreates it
        db.execSQL("DROP TABLE IF EXISTS " + ItemsList.TABLE_NAME);

        onCreate(db);

    }
}
