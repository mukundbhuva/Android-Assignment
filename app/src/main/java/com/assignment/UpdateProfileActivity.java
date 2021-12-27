package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText name, email, password, confirm_password, contact_number, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle("Update Profile");

        name = findViewById(R.id.update_fullname);
        email = findViewById(R.id.update_email);
        contact_number = findViewById(R.id.update_contact_number);
        password = findViewById(R.id.update_new_password);
        confirm_password = findViewById(R.id.update_new_confirm_password);
        username = findViewById(R.id.update_username);

        DatabaseHelper db = new DatabaseHelper(UpdateProfileActivity.this);
        SharedPreferences sp = getSharedPreferences("chat_app_session", MODE_PRIVATE);
        Cursor c = db.ReadRecord(DatabaseHelper.table_student, "*","id IS '"+sp.getString("user_id", "")+"'", "1");
        if(c.moveToFirst())
        {
            name.setText(c.getString(1).toString());
            email.setText(c.getString(2).toString());
            contact_number.setText(c.getString(3).toString());
            username.setText(c.getString(4).toString());
        }
        else
            Toast.makeText(UpdateProfileActivity.this, "No records found!", Toast.LENGTH_SHORT).show();
    }

    public void try_to_update(View view)
    {
        AlertDialog.Builder dialog_builder= new AlertDialog.Builder(UpdateProfileActivity.this);
        View verify_password_dialog = getLayoutInflater().inflate(R.layout.password_dialog, null);

        dialog_builder.setView(verify_password_dialog);

        dialog_builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateProfileActivity.this, "Cancel Password Verification. Update Abort.", Toast.LENGTH_SHORT).show();
            }
        });
        dialog_builder.setPositiveButton("Update Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseHelper db = new DatabaseHelper(UpdateProfileActivity.this);
                SharedPreferences sp = getSharedPreferences("chat_app_session", MODE_PRIVATE);
                Cursor c = db.ReadRecord(DatabaseHelper.table_student, "password", "id IS '"+sp.getString("user_id", "")+"'", "1");
                String current_pwd = "";
                if(c.moveToFirst())
                    current_pwd = c.getString(0);
                else
                    Toast.makeText(UpdateProfileActivity.this, "No records found!", Toast.LENGTH_SHORT).show();

                if(!current_pwd.isEmpty())
                {
                    if(((EditText)verify_password_dialog.findViewById(R.id.current_password)).getText().toString().equals(current_pwd))
                    {
                        ContentValues updated_data = new ContentValues();
                        if(!name.getText().toString().isEmpty())
                            updated_data.put("name", name.getText().toString());
                        if(!email.getText().toString().isEmpty())
                            updated_data.put("email", email.getText().toString());
                        if(!contact_number.getText().toString().isEmpty())
                            updated_data.put("phone_number", contact_number.getText().toString());
                        if(!username.getText().toString().isEmpty())
                            updated_data.put("username", username.getText().toString());
                        if(!password.getText().toString().isEmpty())
                        {
                            if(password.getText().toString().equals(confirm_password.getText().toString()))
                                updated_data.put("password", name.getText().toString());
                            else
                            {
                                Toast.makeText(UpdateProfileActivity.this, "Password and confirm password does not match.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        db.updateRecord(DatabaseHelper.table_student, updated_data, "id IS '"+sp.getString("user_id", "")+"'", UpdateProfileActivity.this);
                    }
                }
            }
        });
        dialog_builder.setTitle("Verify your identity");
        dialog_builder.show();
    }
}