package com.example.letsgotogetherv2.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Trung Tinh on 4/19/2019.
 */

public class dateTimePicker {

    static public int dayDistance, timeDistance;

    public static void chooseDate(final Context context, final TextView editText){
        final Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                long calendar1 = calendar.getTimeInMillis();
                calendar.set(year,month,dayOfMonth);
                dayDistance = (int) (calendar.getTimeInMillis() - calendar1);
                if (dayDistance < 0) {
                    Toast.makeText(context, "Vui lòng chọn ngày trong tương lai!", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    editText.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },year,month,date);
        datePickerDialog.show();
    }

    public  static void chooseTime(final Context context, final TextView editText){
        final Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int Minute = calendar.get(Calendar.MINUTE);
        final TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeDistance = (hourOfDay * 60 + minute) - (hour * 60 + Minute);
                calendar.set(0,0,0,hourOfDay,minute);

                if (dayDistance == 0 && timeDistance < 0) {
                    Toast.makeText(context, "Vui lòng chọn giờ trong tương lai!", Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    editText.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },hour,Minute,true);
        timePickerDialog.show();
    }
}
