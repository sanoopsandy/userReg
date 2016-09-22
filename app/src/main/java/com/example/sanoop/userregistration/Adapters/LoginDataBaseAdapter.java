package com.example.sanoop.userregistration.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanoop.userregistration.Data.DataBaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    public static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text, DEVICEID text, DEVICETYPE text, TIME text); ";

    public  SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String deviceId, String deviceType, String time)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("DEVICEID",deviceId);
        newValues.put("DEVICETYPE",deviceType);
        newValues.put("TIME",time);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public ArrayList<String> getSingleEntry(String userName)
    {
        ArrayList<String> device = new ArrayList<String>();
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return device;
        }
        cursor.moveToFirst();
        String deviceId= cursor.getString(cursor.getColumnIndex("DEVICEID"));
        String deviceType= cursor.getString(cursor.getColumnIndex("DEVICETYPE"));
        String time= cursor.getString(cursor.getColumnIndex("TIME"));
        device.add(deviceId);
        device.add(deviceType);
        device.add(time);
        cursor.close();
        return device;
    }
    public void  updateEntry(String userName, String deviceId, String deviceType, String time)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("DEVICEID", deviceId);
        updatedValues.put("DEVICETYPE", deviceType);
        updatedValues.put("TIME", time);

        String where="USERNAME = ?";
        db.update("LOGIN", updatedValues, where, new String[]{userName});
    }

    public Cursor fetchAll() {
        db = dbHelper.getReadableDatabase();
        Cursor mCursor = db.query("LOGIN", new String[] {
                "ID",
                "USERNAME",
                "DEVICEID",
                "DEVICETYPE",
                "TIME"
        }, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}