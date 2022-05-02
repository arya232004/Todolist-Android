package com.example.todolist;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Calendar;

public class addtask extends AppCompatActivity{
   EditText title,description,event,taskDate,taskTime;
   Button b;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_task);
        title=findViewById(R.id.addTaskTitle);
        description=findViewById(R.id.addTaskDescription);
        event=findViewById(R.id.taskEvent);
        b=findViewById(R.id.addTask);
        taskDate=findViewById(R.id.taskDate);
        taskTime=findViewById(R.id.taskTime);

        b.setOnClickListener(view -> {
            String titleoftask=title.getText().toString();
            String descriptionoftask=description.getText().toString();
            String eventtask=event.getText().toString();
            String timetask= taskTime.getText().toString();
            String datetask=taskDate.getText().toString();
            if( !TextUtils.isEmpty(titleoftask)&& !TextUtils.isEmpty(descriptionoftask) && !TextUtils.isEmpty(eventtask)&& !TextUtils.isEmpty(timetask) && !TextUtils.isEmpty(datetask) ) {
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("title", titleoftask);
                i.putExtra("description", descriptionoftask);
                i.putExtra("event", eventtask);
                i.putExtra("time", timetask);
                i.putExtra("date", datetask);

                startActivity(i);
            }
            else{
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show();
            }
        });
        taskDate.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            taskDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        taskTime.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        (view12, hourOfDay, minute) -> {
                            taskTime.setText(hourOfDay + ":" + minute);
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

    }
}
