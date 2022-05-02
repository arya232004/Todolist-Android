package com.example.todolist;

import android.widget.Toast;

public class model {
    private String title;
    private String description;
    private String date;
    private String time;
    private String event;
    private String status;
    private String Month;
    private String Day;
    private String index;


    // Constructor
    public model(String title, String description,String Month, String Day,String time,String index) {

        try {
            this.title = title;
            this.description = description;
            this.Month = Month;
            this.Day = Day;
            this.time = time;
            this.index = index;
        }
        catch (Exception e) {
            Toast.makeText(null, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
        // Getter and Setter
        public String getTitle () {
            return title;
        }

        public void setTitle (String title){
            this.title = title;
        }

        public String getDescription () {
            return description;
        }

        public void setDescription (String description){
            this.description = description;
        }

    public String getMonth () {
        return Month;
    }

    public void setMonth (String Month){
        this.Month = Month;
    }
    public String getDay () {
        return Day;
    }

    public void setDay (String Day){
        this.Day = Day;
    }

    public String getTime () {
        return time;
    }

    public void setTime (String time){
        this.time = time;
    }

    public String  getIndex () {
        return index;
    }

    public void setIndex (String index){
        this.index = index;
    }

    }



