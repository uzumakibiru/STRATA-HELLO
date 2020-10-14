package com.example.firebasedatabsedemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.CompactDecimalFormat;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.EventLog;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class cleaningcalender extends AppCompatActivity {
    CompactCalendarView compactCalendarView;
   // private SimpleDateFormat dateFormatMonth= new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    private  SimpleDateFormat dateFormatMonth=new SimpleDateFormat("MMM- yyyy", Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaningcalender);
       /* final ActionBar actionBar=getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        compactCalendarView=(CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        Event evi=new Event(Color.RED,1597196421000L, "Cleaning Schedule");
        compactCalendarView.addEvent(evi);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context=getApplicationContext();
                if(dateClicked.toString().compareTo("Thu Aug 13 09:00:00 AST 2020")==0){
                    Toast.makeText(context,"Cleaning Schedule",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,"No Cleaning Schedule",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
             //  actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });*/

        Date today=new Date();
        Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        CalendarPickerView datePicker=findViewById(R.id.calendar);
        datePicker.init(today,nextYear.getTime()).withSelectedDate(today);
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
             /*   if(date.toString().compareTo("Thursday August 13 09:00:00 AST 2020")==0){
                    Toast.makeText(cleaningcalender.this,"Cleaning Schedule",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(cleaningcalender.this,"No Cleaning Schedule",Toast.LENGTH_SHORT).show();
                }*/
               String selectedDate= DateFormat.getDateInstance(DateFormat.FULL).format(date);
               // Toast.makeText(cleaningcalender.this,"No Cleaning Schedule",Toast.LENGTH_SHORT).show();
                Toast.makeText(cleaningcalender.this,selectedDate +"" + "No cleaning Scheduled",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
