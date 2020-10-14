package com.example.firebasedatabsedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class requestcalendar extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener{
    private TextView datetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestcalendar);
        datetext = findViewById(R.id.dateText);
        findViewById(R.id.cleaningRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            showDatePickerDialog();
            }
        });
    }
       private void showDatePickerDialog(){
            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    this,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date="The Cleaning Schedule for:" +month+"/"+dayOfMonth+"/"+year;
        datetext.setText(date);
    }
}
