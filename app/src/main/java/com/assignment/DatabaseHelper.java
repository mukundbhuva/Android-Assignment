package com.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {

    public static final CharSequence table_student = "student";
    public static final CharSequence table_trip = "trip_survey";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "chatapp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseHelper.table_student + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR NOT NULL, " +
                "email VARCHAR NOT NULL," +
                "phone_number VARCHAR NOT NULL," +
                "username VARCHAR UNIQUE NOT NULL, " +
                "password VARCHAR NOT NULL)");

        db.execSQL("CREATE TABLE " + DatabaseHelper.table_trip + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR NOT NULL, " +
                "email VARCHAR NOT NULL," +
                "no_of_days INTEGER NOT NULL," +
                "place_type VARCHAR UNIQUE NOT NULL, " +
                "price_from REAL NOT NULL,"+
                "price_to REAL NOT NULL," +
                "travel_via VARCHAR NOT NULL,"+
                "date_from DATE NOT NULL,"+
                "date_to DATE NOT NULL,"+
                "no_of_person INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long createRecord(CharSequence table, ContentValues data) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(table.toString(), null, data);
    }

    public Cursor ReadRecord(CharSequence table, String req_fields, String where_clause, String limits) {
        where_clause = where_clause == null ? "" : where_clause;
        limits = limits == null? "" : limits;


        String query = "SELECT " + req_fields + " FROM " + table.toString();

        if(!where_clause.isEmpty() || !where_clause.equals(""))
            query = query + " WHERE " + where_clause;
        if(!limits.isEmpty() || !limits.equals(""))
            query = query + " LIMIT " + limits;
        query = query+";";

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void updateRecord(CharSequence table, ContentValues data, String where_clause, Context c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            int rows_affected = db.update(table.toString(), data, where_clause, null);
            if(rows_affected > 0)
                Toast.makeText(c, "Record has been updated.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(c, "Record update has been failed.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteRecord(CharSequence table, String where_clause,  String[] where_args, Context c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            if(db.delete(table.toString(), where_clause, where_args) > 0)
                Toast.makeText(c, "Record(s) has been deleted successfully!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(c, "No records matched for deletion.", Toast.LENGTH_SHORT).show();
        }
        catch(RuntimeException e)
        {
            Toast.makeText(c, "Sorry! could not delete records at this time.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        catch(Exception e)
        {
            Toast.makeText(c, "Sorry! could not delete records at this time.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
