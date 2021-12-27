package com.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BtoH_Conversion extends AppCompatActivity {
    private TextView Answer;
    private EditText Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btoh_conversion);
        this.Answer = findViewById(R.id.conversionAnswer);
        this.Number = findViewById(R.id.number);
        this.Number.setInputType(InputType.TYPE_CLASS_TEXT);
        this.Number.setKeyListener(DigitsKeyListener.getInstance("01"));
        getSupportActionBar().setTitle("Converter");
    }

    protected int binaryToDecimalConversion()
    {
        if(this.Number.getText().toString().compareTo("") != 0)
            return Integer.parseInt(this.Number.getText().toString(), 2);
        else
        {
            Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            v.vibrate(500);
            Toast.makeText(BtoH_Conversion.this,"Please add number first!", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    String decimalToHex()
    {
        int decimalNumber = binaryToDecimalConversion();

        String hexNumber = Integer.toHexString(decimalNumber);

        hexNumber = hexNumber.toUpperCase();

        return hexNumber;
    }
    public void performConversion(View view) {
        this.Answer.setText("Binary to Hexadecimal Conversion:\n"+this.decimalToHex());
    }
}