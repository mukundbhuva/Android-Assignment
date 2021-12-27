package com.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AgeCalculatorActivity extends AppCompatActivity {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    java.util.Date date_and_time_of_birth = null;
    Date currentDate = null;
    String Diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);
        getSupportActionBar().setTitle("Age Calculator");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDatePicker(View view)
    {
        currentDate = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            DatePickerDialog dpd = new DatePickerDialog(AgeCalculatorActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                        {
                            ((EditText)findViewById(R.id.dateofbirth)).setText(dayOfMonth + "/"+ (month+1) + "/" + year);
                            try
                            {
                                date_and_time_of_birth = sdf.parse(dayOfMonth + "/"+ (month+1) + "/" + year+ " 00:00:00");
                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(currentDate.before(date_and_time_of_birth))
                            {
                                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                                v.vibrate(500);
                                Toast.makeText(AgeCalculatorActivity.this, "Congratulations! You are going to born in future!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            long difference_In_Time = currentDate.getTime() - date_and_time_of_birth.getTime();

                            long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time)%60;

                            long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time)% 60;

                            long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

                            long difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;

                            long difference_In_Years = TimeUnit.MILLISECONDS.toDays(difference_In_Time) / 365l;

                            Diff = difference_In_Years
                                    + " years, "
                                    + difference_In_Days
                                    + " days, "
                                    + difference_In_Hours
                                    + " hours, "
                                    + difference_In_Minutes
                                    + " minutes, "
                                    + difference_In_Seconds
                                    + " seconds";
                            ((TextView)findViewById(R.id.answer)).setText("Your Age :\n" + Diff);
                        }
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DATE));
            dpd.setTitle("Select Date Of Birth");
            dpd.show();
        }
    }
}