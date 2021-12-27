package com.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TripActivity extends AppCompatActivity {
    Intent i;

    EditText name, email, date_from, date_to, price_from, price_to, no_of_person, no_of_days;
    Spinner place_type;
    CheckBox plane, rail, bus;
    Button btn;
    int id;
    ArrayList<String> pt;

    ArrayAdapter adt;

    /*---------------------------------------*/
    String selected_place_type, travell_via="";
    boolean is_plane_selected, is_bus_selected, is_rail_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        getSupportActionBar().setTitle("Trip");

        pt = new ArrayList<>();
        pt.add("Ancient Site");
        pt.add("Hill Station");
        pt.add("Pilgrimage Site");

        name = findViewById(R.id.trip_name);
        email = findViewById(R.id.trip_email);
        no_of_days = findViewById(R.id.trip_days);
        no_of_person = findViewById(R.id.trip_persons);
        price_from = findViewById(R.id.trip_price_from);
        price_to = findViewById(R.id.trip_price_to);
        date_from = findViewById(R.id.trip_date_from);
        date_to = findViewById(R.id.trip_date_to);
        plane = findViewById(R.id.trip_travell_via_plane);
        rail = findViewById(R.id.trip_travell_via_rail);
        bus = findViewById(R.id.trip_travell_via_bus);
        place_type = findViewById(R.id.trip_place_type);
        btn = findViewById(R.id.btnA);
        adt = new ArrayAdapter(TripActivity.this, android.R.layout.simple_spinner_item, pt);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_item);
        place_type.setAdapter(adt);
        place_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_place_type = place_type.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        i = getIntent();
        if(!i.hasExtra("trip"))
        {
            btn.setText("Insert Trip");
        }
        else
        {
            btn.setText("Update Trip");
            Trips t = (Trips) i.getSerializableExtra("trip");
            name.setText(t.getName());
            email.setText(t.getEmail());
            no_of_days.setText(""+t.getNo_of_days());
            no_of_person.setText(""+t.getNo_of_person());
            price_from.setText(String.valueOf(t.getPrice_from()));
            price_to.setText(String.valueOf(t.getPrice_to()));
            date_from.setText(t.getDate_from());
            date_to.setText(t.getDate_to());
            place_type.setSelection(pt.indexOf(t.place_type));
            id = t.getTrip_id();
        }
    }

    public void insert_update_trip(View view) {
        DatabaseHelper db = new DatabaseHelper(TripActivity.this);
        ContentValues data = new ContentValues();
        data.put("name", name.getText().toString());
        data.put("email", email.getText().toString());
        data.put("no_of_days", Integer.parseInt(no_of_days.getText().toString()));
        data.put("date_to", date_to.getText().toString());
        data.put("date_from", date_from.getText().toString());
        data.put("price_from", Float.parseFloat(price_from.getText().toString()));
        data.put("price_to", Float.parseFloat(price_to.getText().toString()));
        data.put("travel_via", travell_via);
        data.put("place_type", selected_place_type);
        data.put("no_of_person", Integer.parseInt(no_of_person.getText().toString()));
        if(!i.hasExtra("trip"))
        {
            if(db.createRecord(DatabaseHelper.table_trip, data) > 0);
                Toast.makeText(TripActivity.this, "Trip created!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            db.updateRecord(DatabaseHelper.table_trip, data, "id IS '"+id+"'", TripActivity.this);
            finish();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePicker2(View view) {
        this.showDatePicker(view, date_to);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePicker1(View view){
        this.showDatePicker(view, date_from);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePicker(View view, EditText ed)
    {
        Date currentDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        DatePickerDialog dpd = new DatePickerDialog(TripActivity.this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {
                        ed.setText(""+dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DATE));
        dpd.setTitle("Select Date");
        dpd.show();
    }
}