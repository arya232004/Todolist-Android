package com.example.todolist;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.text.DateFormatSymbols;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class bottomtaskfragment extends BottomSheetDialogFragment {
    ArrayList<Bundle> tasks=new ArrayList<Bundle>();
    public ArrayList<model> modelArrayList=new ArrayList<>();
    EditText title,description,event,taskDate,taskTime;
    Button b;
    RecyclerView recyclerView;
    ImageView imageView;
    int mYear;
    int mMonth;
    int mDay;
    int mHour,mMinute;

    Bundle bundle;
    HashMap<String, String> map;
    SharedPreferences sharedPreferences = null;

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create_task, container, false);
        title = v.findViewById(R.id.addTaskTitle);
        description = v.findViewById(R.id.addTaskDescription);
//        event = v.findViewById(R.id.taskEvent);
        b = v.findViewById(R.id.addTask);
        b.setBackground(getResources().getDrawable(R.drawable.btn_background));
        taskDate = v.findViewById(R.id.taskDate);
        taskTime = v.findViewById(R.id.taskTime);
        recyclerView = getActivity().findViewById(R.id.taskRecycler);
        imageView=getActivity().findViewById(R.id.noDataImage);

        sharedPreferences = getActivity().getSharedPreferences("data", getContext().MODE_PRIVATE);
        String data = sharedPreferences.getString("data", "");
        if(!data.isEmpty())
        {
            JSONArray jsonArrayforprev = null;
            try {
                jsonArrayforprev = new JSONArray(sharedPreferences.getString("data", null));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                for (int i = 0; i < jsonArrayforprev.length(); i++) {
                    String title = jsonArrayforprev.getJSONObject(i).getString("title");
                    String description = jsonArrayforprev.getJSONObject(i).getString("description");
                    String day = jsonArrayforprev.getJSONObject(i).getString("day");
                    String month = jsonArrayforprev.getJSONObject(i).getString("month");
                    String time = jsonArrayforprev.getJSONObject(i).getString("time");
                    String index = jsonArrayforprev.getJSONObject(i).getString("index");
                    modelArrayList.add(new model(title, description, day, month, time,index));
                    adapter adapterxd = new adapter(getContext(), modelArrayList);
                    recyclerView.setAdapter(adapterxd);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    imageView.setImageDrawable(null);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try{
            b.setOnClickListener(view -> {
                String titleoftask = title.getText().toString();
                String descriptionoftask = description.getText().toString();
//            String eventtask = event.getText().toString();
                String timetask = taskTime.getText().toString();
                String datetask = taskDate.getText().toString();
                Log.d("timexd", timetask);

                String month= new DateFormatSymbols().getMonths()[Integer.parseInt(datetask.split("-")[1])-1].substring(0,3);
                String day=datetask.split("-")[0];
                String min=timetask.split(":")[1];
                String hour=timetask.split(":")[0];

                Log.d("datexd", timetask);


                String time=hour+":"+min;


                if (!TextUtils.isEmpty(titleoftask) && !TextUtils.isEmpty(descriptionoftask)  && !TextUtils.isEmpty(timetask) && !TextUtils.isEmpty(datetask)) {
                    String size=String.valueOf(modelArrayList.size());
                    modelArrayList.add(new model(titleoftask, descriptionoftask,day,month,time,size));

                    try {
                        adapter adapterxd = new adapter(getContext() ,modelArrayList);
                        recyclerView.setAdapter(adapterxd);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        imageView.setImageDrawable(null);

                        for(int i=0;i<modelArrayList.size() ;i++){

                            bundle=new Bundle();
                            bundle.putString("title",titleoftask);
                            bundle.putString("description",descriptionoftask);
                            bundle.putString("day",day);
                            bundle.putString("month",month);
                            bundle.putString("time",time);
                            bundle.putString("index",modelArrayList.size()+"");
                        }
                        tasks.add(bundle);

                        JSONArray alltasks = new JSONArray();
                        for (int i = 0; i < tasks.size(); i++) {
                            JSONObject task = new JSONObject();
                            task.put("title", tasks.get(i).getString("title"));
                            task.put("description", tasks.get(i).getString("description"));
                            task.put("day", tasks.get(i).getString("day"));
                            task.put("month", tasks.get(i).getString("month"));
                            task.put("time", tasks.get(i).getString("time"));
                            task.put("index", tasks.get(i).getString("index"));
                            alltasks.put(task);
                        }
                        Log.d("alltasks", alltasks.toString());

                        String verification= sharedPreferences.getString("data", null);

                        if(verification!=null) {
                            JSONArray jsonArray = new JSONArray(sharedPreferences.getString("data", null));
                            JSONArray result = new JSONArray();
                            result=combine(jsonArray,alltasks);
                            Log.d("result", result.toString());
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", getContext().MODE_PRIVATE).edit();
                            editor.putString("data", result.toString());
                            Toast.makeText(getContext(), "Task added", Toast.LENGTH_SHORT).show();
                            editor.apply();
                            Intent i=new Intent(getContext(),MainActivity.class);
                            startActivity(i);
                        }
                        else {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", getContext().MODE_PRIVATE).edit();
                            editor.putString("data", alltasks.toString());
                            Log.d("dataalltask", alltasks.toString());
                            editor.apply();
                        }

                        sharedPreferences = getActivity().getSharedPreferences("data", getContext().MODE_PRIVATE);
                        String complete_data= sharedPreferences.getString("data", null);
                        JSONArray jsonArray = new JSONArray(sharedPreferences.getString("data", null));
                        String title = jsonArray.getJSONObject(0).getString("title");

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage()+ "last one", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        taskDate.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        (view12, hourOfDay, minute) -> {
                            taskTime.setText(hourOfDay + ":" + minute);
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });

        return v;
    }
    @NonNull
    private JSONArray combine (@NonNull JSONArray a, JSONArray b) throws JSONException {
        JSONArray result = new JSONArray();
        for (int i = 0; i < a.length(); i++) {
            result.put(a.getJSONObject(i));
        }
        for (int i = 0; i < b.length(); i++) {
            result.put(b.getJSONObject(i));
        }
        return result;
    }
}