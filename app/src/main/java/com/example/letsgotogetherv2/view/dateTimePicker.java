package com.example.letsgotogetherv2.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Trung Tinh on 4/19/2019.
 */
public class dateTimePicker {
    public static void chooseDate(Context context, final TextView editText){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editText.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,date);
        datePickerDialog.show();
    }

    public  static void chooseTime(Context context, final TextView editText){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                calendar.set(0,0,0,hourOfDay,minute);
                editText.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },hour,minute,true);
        timePickerDialog.show();
    }
}
