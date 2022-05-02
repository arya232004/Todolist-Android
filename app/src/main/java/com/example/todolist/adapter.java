package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class adapter  extends RecyclerView.Adapter<adapter.Viewholder> {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<model> modelArrayList;
    private CardView cardView;

    SharedPreferences sharedPreferences = null;
    // Constructor
    public adapter(Context context, ArrayList<model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.Viewholder holder, int position) {
        model model = modelArrayList.get(position);
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.month.setText(model.getDay());
        holder.date.setText(model.getMonth());
        holder.time.setText(model.getTime());
        holder.index.setText(model.getIndex());
    }
    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder {

        JSONArray jsonArray;
        private TextView day,date,month,title,description,status,time,index;
        private ImageView imageView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            //day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            cardView = itemView.findViewById(R.id.card_task);
            month = itemView.findViewById(R.id.month);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            time = itemView.findViewById(R.id.time);
            index = itemView.findViewById(R.id.index);
            imageView = itemView.findViewById(R.id.options);

            imageView.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Complete")){

                        }
                        else if(menuItem.getTitle().equals("Delete")){

                            try {
                                sharedPreferences = context.getSharedPreferences("data", context.MODE_PRIVATE);
                                String data=sharedPreferences.getString("data","");
                                jsonArray=new JSONArray(data);
                                Log.d("data_trial",jsonArray.toString());

                                Log.d("data_trial",index.getText().toString());

                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                                    if((index.getText().toString()).equals("0")){
                                       index.setText("1");
                                    }

                                    if(jsonObject.getString("index").equals(index.getText().toString())){
                                        jsonArray.remove(i);
                                        Log.d("data_trial2", itemView.getParent().toString());
                                        ViewGroup parent = (ViewGroup) itemView.getParent();
                                        parent.removeView(itemView);

                                        Log.d("data_trial3", parent.getChildCount()+"");
                                        Intent intent = new Intent(context, MainActivity.class);
                                         context.startActivity(intent);

                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(i, jsonArray.length());
                                        notifyItemChanged(i);

                                        notifyDataSetChanged();
                                        break;
                                    }
                                }
                                setIndex();
                                 SharedPreferences.Editor editor = context.getSharedPreferences("data", context.MODE_PRIVATE).edit();
                                 editor.putString("data", jsonArray.toString());
                                 notifyItemChanged(Integer.parseInt(index.getText().toString()));
                                 editor.apply();
                            } catch (Exception e) {
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
                popupMenu.show();
            });
        }
        void setIndex(){
            for(int i=0;i<jsonArray.length();i++){
                index.setText(String.valueOf(i));
            }
        }
    }
}