package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity
{
    private EditText full_name, username, email, password, confirm_password, contact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Assignment App - Register");
        this.full_name = findViewById(R.id.register_fullname);
        this.email = findViewById(R.id.register_email);
        this.contact_number = findViewById(R.id.register_contact_number);
        this.username = findViewById(R.id.register_username);
        this.password = findViewById(R.id.register_password);
        this.confirm_password = findViewById(R.id.register_confirm_password);
    }

    @SuppressLint("Range")
    public void try_to_register(View view)
    {
        boolean noError = true;
        if(!InputValidation.validate_input(this.full_name.getText().toString(), InputValidation.name_validation))
        {
            noError = false;
            Toast.makeText(this, "Invalid Full Name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!InputValidation.validate_input(this.email.getText().toString(), InputValidation.email_address_validation))
        {
            noError = false;
            Toast.makeText(this, "Invalid Email Address.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!InputValidation.validate_input(this.contact_number.getText().toString(), InputValidation.phone_number_validation))
        {
            noError = false;
            Toast.makeText(this, "Invalid Contact Number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!InputValidation.validate_input(this.username.getText().toString(), InputValidation.username_validation))
        {
            noError = false;
            Toast.makeText(this, "Invalid Username.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(this.password.getText().toString().isEmpty() || this.confirm_password.getText().toString().isEmpty())
        {
            noError = false;
            Toast.makeText(this, "Passwords fields can not be empty.", Toast.LENGTH_SHORT).show();
            return;

        }

        if(!this.password.getText().toString().equals(this.confirm_password.getText().toString()))
        {
            noError = false;
            Toast.makeText(this, "Passwords does not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(noError) {
            DatabaseHelper db = new DatabaseHelper(this);
            ContentValues data = new ContentValues();
            data.put("name", this.full_name.getText().toString());
            data.put("username", this.username.getText().toString());
            data.put("email", this.email.getText().toString());
            data.put("password", this.password.getText().toString());
            data.put("phone_number", this.contact_number.getText().toString());

            if (db.createRecord(DatabaseHelper.table_student,data) > 0) {
                Cursor c = db.ReadRecord(DatabaseHelper.table_student,"*", "username IS '"+this.username.getText().toString()+"'", "1");
                AppUser user = null;
                if (c.moveToFirst()) {

                    Toast.makeText(this, "Registration has been successfully completed.", Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = getSharedPreferences("chat_app_session", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("user_id", c.getString(c.getColumnIndex("id")).toString());
                    ed.putString("session_start_at", new Date().toString());
                    ed.commit();
                    startActivity(new Intent(this, Home.class));
                    finish();
                } else {
                    Toast.makeText(this, "Unable to retrieve the registration details at the moment. Please try again after some time.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    public void reset_register_form(View view)
    {
        this.full_name.setText("");
        this.email.setText("");
        this.username.setText("");
        this.contact_number.setText("");
        this.password.setText("");
        this.confirm_password.setText("");
    }
}