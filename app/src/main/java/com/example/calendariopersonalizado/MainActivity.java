package com.example.calendariopersonalizado;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private CompactCalendarView compactCalendar;
    private final SimpleDateFormat dateFormatMonth = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle(null);
        compactCalendar = findViewById(R.id.compactcalendar_view);
        compactCalendar.setUseThreeLetterAbbreviation(true);

        //Event ev1 = new Event(Color.BLUE, 1674928620000L, "uno");
        //EEE-MMM-dd-yyyy HH:mm:ss
        Event ev1 = new Event(Color.YELLOW, fechaALong("28-01-2023"), "Pagar a coppel");

        compactCalendar.addEvent(ev1);

        //Event ev2 = new Event(Color.RED, 1674842220000L, "dos");
        Event ev2 = new Event(Color.rgb(26, 188, 156), fechaALong("29-01-2023"), "Presupuesto del usuario");
        compactCalendar.addEvent(ev2);


        compactCalendar.setListener(new CompactCalendarView.CompactCalendarViewListener(){
            @Override
            public void onDayClick(Date dateClicked) {

                data.setText(dateFormatMonth.format(dateClicked));

               if (dateFormatMonth.format(dateClicked).compareTo(milliADate(ev1.getTimeInMillis()))==0) {
                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(ev1.getData()).toString(), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "No contiene eventos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                actionBar.setTitle(dateFormatMonth.format(firstDayOfNewMonth));
            }
        });
    }

    public String milliADate(Long milli){
        // Estableciendo formato
        DateFormat simple = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        // Creando fecha de milliseconds
        // usando constructor de Date
        Date date = new Date(milli);
        return simple.format(date);
    }
    public Long fechaALong(String fecha){
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
        Long timeInMillis = null;
        try {
            Date d = f.parse(fecha);
            assert d != null;
            timeInMillis = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMillis;
    }
}
