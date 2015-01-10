package com.Avishek2014.vatcalculator;

/**
 * Created by Avishek on 07-Jan-15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_VAT_CALCULATOR = "vatCalculator"; // table name

    // columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATETIME = "dateTime";
    public static final String COLUMN_GRAND_TOTAL = "grandTotal";
    public static final String COLUMN_TOTAL_VAT = "totalVat";

    // database name and version
    private static final String DATABASE_NAME = "vatCalculator.db";
    private static final int DATABASE_VERSION = 1;

    // database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_VAT_CALCULATOR + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_DATETIME + " text not null, "
            + COLUMN_GRAND_TOTAL + " real not null, " + COLUMN_TOTAL_VAT
            + " real not null);";

    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VAT_CALCULATOR);
        onCreate(db);
    }
}
