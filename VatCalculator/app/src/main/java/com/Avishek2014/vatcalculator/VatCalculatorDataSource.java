package com.Avishek2014.vatcalculator;

/**
 * Created by Avishek on 07-Jan-15.
 */
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class VatCalculatorDataSource {
    // database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_DATETIME,
                                   SQLiteHelper.COLUMN_GRAND_TOTAL, SQLiteHelper.COLUMN_TOTAL_VAT};

    public VatCalculatorDataSource(Context context){
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public VatCalculator createVatCalculatorEntry(String dateTime, float grandTotal, float totalVat){
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_DATETIME, dateTime);
        values.put(SQLiteHelper.COLUMN_GRAND_TOTAL, grandTotal);
        values.put(SQLiteHelper.COLUMN_TOTAL_VAT, totalVat);
        long insertId = database.insert(SQLiteHelper.TABLE_VAT_CALCULATOR, null,
                values);  // insertion into database completed here

        // reading from the database
        Cursor cursor = database.query(SQLiteHelper.TABLE_VAT_CALCULATOR, allColumns,
                SQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        VatCalculator newEntry = cursorToEntry(cursor);
        cursor.close();
        return newEntry;
    }

    public void deleteEntry(VatCalculator vc){
        long id = vc.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_VAT_CALCULATOR, SQLiteHelper.COLUMN_ID
        + " = " + id, null);
    }

    // this method adds all the entries from the database to the List
    public List<VatCalculator> getAllEntries(){
        List<VatCalculator> vcentries = new ArrayList<VatCalculator>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_VAT_CALCULATOR, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            VatCalculator vc = cursorToEntry(cursor);
            vcentries.add(vc);
            cursor.moveToNext();
        }

        cursor.close();
        return vcentries;
    }

    private VatCalculator cursorToEntry(Cursor cursor){
        VatCalculator vc = new VatCalculator();
        vc.setId(cursor.getLong(0));
        vc.setDateTime(cursor.getString(1));
        vc.setGrandTotal(cursor.getFloat(2));
        vc.setTotalVat(cursor.getFloat(3));
        return vc;
    }

}
