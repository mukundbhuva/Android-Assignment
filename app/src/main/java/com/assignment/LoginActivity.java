package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private AppUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Assignment App - Login");
        this.username = findViewById(R.id.login_username);
        this.password = findViewById(R.id.login_password);
    }

    public void try_to_login(View view)
    {
        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
        Cursor c = db.ReadRecord(DatabaseHelper.table_student, "password", "username IS '"+this.username.getText().toString()+"'", "1");
        if(c.moveToFirst())
        {
                if(c.getString(0).toString().equals(this.password.getText().toString()))
                {
                    SharedPreferences sp = getSharedPreferences("chat_app_session", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    String user_id = null;
                    Cursor c2 = db.ReadRecord(DatabaseHelper.table_student, "id", "username IS '"+this.username.getText().toString()+"'", "1");
                    if(c2.moveToFirst())
                        user_id = c2.getString(0).toString();
                    else
                        Toast.makeText(LoginActivity.this, "Unable to verify login data. Please contact developer.", Toast.LENGTH_SHORT).show();
                    if(!user_id.equals(null)) {
                        ed.putString("user_id", user_id);
                        ed.putString("session_start_at", new Date().toString());
                        ed.commit();
                    }
                    this.user = new AppUser(LoginActivity.this, sp);
                    Intent i = new Intent(LoginActivity.this, Home.class);
//                    i.putExtra("user", this.user);
                    startActivity(i);
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Password doesn't match.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(LoginActivity.this, "Username doesn't match.", Toast.LENGTH_SHORT).show();
    }

    public void show_register_activity(View view)
    {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}