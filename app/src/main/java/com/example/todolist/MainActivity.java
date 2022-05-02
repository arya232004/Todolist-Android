package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   RecyclerView r1;
   TextView addtask,index;
   ImageView imageView,testingl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        r1=findViewById(R.id.taskRecycler);
        addtask=findViewById(R.id.addTask);
        imageView=findViewById(R.id.noDataImage);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fade);
        imageView.startAnimation(animation);
         index=findViewById(R.id.index);

       try {
           addtask.setOnClickListener(view -> {

               bottomtaskfragment bottomSheetDialogFragment = new bottomtaskfragment();
               bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

           });
       }catch (Exception e){
           Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
       }

        Intent i=getIntent();
        String task=i.getStringExtra("task");
        String title=i.getStringExtra("title");
        String description=i.getStringExtra("description");
        String date=i.getStringExtra("date");
        String event=i.getStringExtra("event");
        String time=i.getStringExtra("time");
    }

    @Override
    protected void onStart()
    {    ImageView[] options= new ImageView[1000];
        SharedPreferences sharedPreferences= getApplication().getSharedPreferences("data",MODE_PRIVATE);
        ArrayList<model> modelArrayList=new ArrayList<>();
        adapter adapterxd;

        try {
            String data=sharedPreferences.getString("data","");
            if (!data.isEmpty()) {
                //Toast.makeText(this, "not empty", Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = new JSONArray(sharedPreferences.getString("data", null));
               // Toast.makeText( this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                try {
                    Log.d("data", jsonArray.length() + "");
                    Log.d("datatrial", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("repeat", "Repeating" + i + " " + jsonArray.length());
                        String title = jsonArray.getJSONObject(i).getString("title");
                        String description = jsonArray.getJSONObject(i).getString("description");
                        String day = jsonArray.getJSONObject(i).getString("day");
                        String month = jsonArray.getJSONObject(i).getString("month");
                        String time = jsonArray.getJSONObject(i).getString("time");
                        String index= jsonArray.getJSONObject(i).getString("index");

                        modelArrayList.add(new model(title, description, day, month, time,index));
                        adapterxd = new adapter(this, modelArrayList);
                        r1.setAdapter(adapterxd);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                        r1.setLayoutManager(linearLayoutManager);
                        imageView.setImageDrawable(null);
                    }
                } catch (JSONException e) {
                    Log.d("data", e.toString());
                }
            } else {
Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        super.onStart();
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("data", this.MODE_PRIVATE);
        String data=sharedPreferences.getString("data","");
        JSONArray jsonArray = null;
        try {
             jsonArray=new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onOptionsMenuClosed(menu);
    }
}