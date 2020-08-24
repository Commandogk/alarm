package com.example.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {
    int count,del=0;
    String[] a =new String[20];
    SharedPreferences sharedPref ;
    Context c;
    Boolean[] d= new Boolean[20];
    public MyAdapter2(Context context,String[] al, int cout,Boolean[] di) {
        c=context;
        count=cout;
        a=al;
        d=di;
    }
    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.alarms,parent,false);

        return new MyAdapter2.MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, final int position) {
        int x=position+1;
       holder.Atime.setText(a[position]);
        holder.pos.setText(""+x);
        holder.v.setChecked(d[position]);
    }

    @Override
    public int getItemCount() {
        return count;
    }


    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView Atime,pos;
        CheckBox v;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            Atime = ((View) itemView).findViewById(R.id.Alarmtime);
            pos = ((View) itemView).findViewById(R.id.pos);
            v = ((View) itemView).findViewById(R.id.vibratee);

        }
    }

}
