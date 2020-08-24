package com.example.alarm;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.util.Objects;

import static com.example.alarm.R.id.one;


public class stopwatch extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public long pausetime;
    RecyclerView recyclerView;
    FloatingActionButton start, pause, reset,split;
    Integer count =0;
    int[] d=new int[20];
    int elasped;
    long first,second=0 ,temp;
    String[] lapc=new String[20];

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static stopwatch newInstance(String param1, String param2) {
        stopwatch fragment = new stopwatch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public stopwatch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View a = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        final Chronometer stopwatch = a.findViewById(one);
        recyclerView = a.findViewById(R.id.recycle);
        start = a.findViewById(R.id.fab1);
        pause = a.findViewById(R.id.fab2);
        reset = a.findViewById(R.id.fab4);
        split=a.findViewById(R.id.fab3);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vie) {
                stopwatch.setBase(SystemClock.elapsedRealtime() - pausetime);
                stopwatch.start();
                start.setEnabled(false);
                split.setEnabled(true);
                pause.setEnabled(true);

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausetime = SystemClock.elapsedRealtime() - stopwatch.getBase();
                temp=SystemClock.elapsedRealtime() - stopwatch.getBase();
                start.setEnabled(true);
                split.setEnabled(false);
                pause.setEnabled(false);
                Toast.makeText(getContext(), "clicked pause",Toast.LENGTH_SHORT).show();
                stopwatch.stop();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopwatch.setBase(SystemClock.elapsedRealtime());
                pausetime = 0;
                temp=0;
                Toast.makeText(getContext(), "clicked reset",Toast.LENGTH_SHORT).show();
                for(int i=0;i<=count;i++)
                { d[i]=0;
                lapc[i]=null;
                }
                count=0;
                MyAdapter myAdapter ;
                myAdapter = new MyAdapter(stopwatch.getContext(),null,null,count);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(stopwatch.getContext()));
            }
        });
         split.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.O)
             @Override
             public void onClick(View view) {
                 if(count<20){
                 count++;
                 if(count>1)
                     second=first;
                 if(start.isEnabled()&&(temp==(SystemClock.elapsedRealtime() - stopwatch.getBase()))){
                     second=first;
                 }
                 else if(start.isEnabled()&&(temp!=(SystemClock.elapsedRealtime() - stopwatch.getBase()))){
                     second=first;
                     first=temp;
                     }
                 else {
                     first = SystemClock.elapsedRealtime() - stopwatch.getBase();
                 }
                 elasped=(int)((first - second)/1000);
                 d[count-1]=elasped;
                 for(int i=0;i<count;i++){
                     lapc[i]="LAP"+(i+1);
                 }

                     Toast.makeText(getContext(), "clicked split",Toast.LENGTH_SHORT).show();
                 MyAdapter myAdapter ;
                 myAdapter = new MyAdapter(stopwatch.getContext(),d,lapc,count);
                 recyclerView.setAdapter(myAdapter);
                 recyclerView.setLayoutManager(new LinearLayoutManager(stopwatch.getContext()));
             }
             else
                     Toast.makeText(getContext(), "split limit reached",Toast.LENGTH_SHORT).show();
             }
         });

        return a;
    }

}