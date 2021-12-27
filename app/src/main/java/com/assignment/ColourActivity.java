package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ColourActivity extends AppCompatActivity {
    String[] colours = {"RED", "GREEN", "BLUE", "MAGENTA", "BLACK", "WHITE"};
    Spinner fgList, bgList;
    ArrayAdapter adt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Change BG & FG");
        setContentView(R.layout.activity_colour);

        fgList = findViewById(R.id.fgColors);
        bgList = findViewById(R.id.bgColors);
        tv = findViewById(R.id.tv);

        adt = new ArrayAdapter(ColourActivity.this, android.R.layout.simple_spinner_item, colours);
        adt.setDropDownViewResource(android.R.layout.simple_spinner_item);

        fgList.setAdapter(adt);
        fgList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (colours[position]) {
                    case "RED":
                        tv.setBackgroundColor(Color.RED);
                        break;
                    case "BLUE":
                        tv.setBackgroundColor(Color.BLUE);
                        break;
                    case "GREEN":
                        tv.setBackgroundColor(Color.GREEN);
                        break;
                    case "MAGENTA":
                        tv.setBackgroundColor(Color.MAGENTA);
                        break;
                    case "BLACK":
                        tv.setBackgroundColor(Color.BLACK); break;
                    case "WHITE":
                        tv.setBackgroundColor(Color.WHITE); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        bgList.setAdapter(adt);
        bgList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (colours[position]) {
                    case "RED":
                        tv.setTextColor(Color.RED);
                        break;
                    case "BLUE":
                        tv.setTextColor(Color.BLUE);
                        break;
                    case "GREEN":
                        tv.setTextColor(Color.GREEN);
                        break;
                    case "MAGENTA":
                        tv.setTextColor(Color.MAGENTA);
                        break;
                    case "BLACK":
                        tv.setTextColor(Color.BLACK); break;
                    case "WHITE":
                        tv.setTextColor(Color.WHITE); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }
}