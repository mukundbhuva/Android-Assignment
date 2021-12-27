package com.assignment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.Toast;

import java.io.Serializable;

public class AppUser implements Serializable{
    /**
     * <p><b>AppUser</b> class represents the user which is logged in this app.</p>
     *
     * @author DSP
     */
    private String user_id, user_full_name, username, email, phone;
    protected Session session;
    private Context context;
    private DatabaseHelper db;

    private String WHERE_CLAUSE;

    public AppUser(Context context, SharedPreferences sp) {

        this.context = context;
        this.session = new Session(sp);
        this.db = new DatabaseHelper(this.context);
        this.WHERE_CLAUSE = "id IS '" + this.session.getSessionEntry("user_id") + "'";
        this.syncWithDatabase("*");
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUser_full_name() {
        return user_full_name;
    }

    public void setUser_full_name(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SuppressLint("Range")
    private void syncWithDatabase(String column_name) {
        column_name = column_name.isEmpty() || column_name.equals(null) ? "*" : column_name;
        Cursor cursor = db.ReadRecord(DatabaseHelper.table_student, column_name, this.WHERE_CLAUSE, "1");

        if(cursor.moveToFirst())
        {
            switch(column_name)
            {
                case "*":               this.setUser_full_name(cursor.getString(cursor.getColumnIndex("name")).toString());
                                        this.setEmail(cursor.getString(cursor.getColumnIndex("email")).toString());
                                        this.setPhone(cursor.getString(cursor.getColumnIndex("phone_number")).toString());
                                        this.setUsername(cursor.getString(cursor.getColumnIndex("username")).toString());
                                        this.user_id = cursor.getString(cursor.getColumnIndex("id")).toString();
                                        break;

                case "user_full_name":  this.setUser_full_name(cursor.getString(cursor.getColumnIndex("name")).toString());break;
                case "email":           this.setEmail(cursor.getString(cursor.getColumnIndex("email")).toString());break;
                case "phone_number":    this.setPhone(cursor.getString(cursor.getColumnIndex("phone_number")).toString());break;
                case "username":        this.setUsername(cursor.getString(cursor.getColumnIndex("username")).toString());break;
            }
        }
        else
            Toast.makeText(this.context, "Unable to fetch records from the database.", Toast.LENGTH_SHORT).show();
    }
    public String getUserPassword(){
        Cursor cursor = db.ReadRecord(DatabaseHelper.table_student, "password", this.WHERE_CLAUSE, "1");
        if(cursor.moveToFirst())
            return cursor.getString(0).toString();
        else {
            Toast.makeText(this.context, "Unable to fetch password records from the database.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void setUserPassword(String pwd){
        ContentValues data = new ContentValues();
        data.put("password", pwd);
        this.db.updateRecord(DatabaseHelper.table_student, data,this.WHERE_CLAUSE, this.context);
    }
    public void logout()
    {
        this.session.removeSession();
    }

}
