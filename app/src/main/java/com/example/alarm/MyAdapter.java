package com.example.alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Duration;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    int count=1;
    String[] l;
    int[] Lpt;
    Context ct;
    public MyAdapter(Context context, int[] Laptime,String[] lapcount, int cout) {
        Lpt=Laptime;
        ct=  context;
        count=cout;
        l=lapcount;

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.laps,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.Laptime.setVisibility(View.VISIBLE);
        holder.Lapcount.setVisibility(View.VISIBLE);
        holder.Laptime.setText(" "+Lpt[position]+"s");
        holder.Lapcount.setText(l[position]);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Lapcount,Laptime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Lapcount=((View)itemView).findViewById(R.id.LapCount);
            Laptime=((View)itemView).findViewById(R.id.LapTime);
        }
    }
}
