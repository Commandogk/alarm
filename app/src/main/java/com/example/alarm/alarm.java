package com.example.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;


public class alarm extends Fragment {
    Spinner ringer;
    TimePicker alarmTime;
    TextClock currentTime;
    Boolean check,g=false;
    CheckBox Vibration;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton add,alarmedit;
    Intent ab;
    private String mParam1;
    private String mParam2;

    public alarm() {
        // Required empty public constructor
    }

    public static alarm newInstance(String param1, String param2) {
        alarm fragment = new alarm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        final

        View p = inflater.inflate(R.layout.fragment_alarm, container, false);
        add = p.findViewById(R.id.addalarm);
        alarmTime = p.findViewById(R.id.timepicker);
        currentTime = p.findViewById(R.id.currenttime);
        Vibration = p.findViewById(R.id.vibrat);
        //ringer = p.findViewById(R.id.ringtoneset);
        alarmedit=p.findViewById(R.id.alarmmanager);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ringer, R.layout.spinnersolor);
        final Ringtone r = RingtoneManager.getRingtone(getContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        adapter.setDropDownViewResource(R.layout.spinnerdroplayout);
      /* ringer.setAdapter(adapter);
       ringer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        ab = new Intent(getActivity(), alarmedit.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g=true;
                check = Vibration.isChecked();
                ab.putExtra("HOUR", alarmTime.getHour());
                ab.putExtra("MIN", alarmTime.getMinute());
                ab.putExtra("CheckBox", check);
                ab.putExtra("add",g);
                startActivity(ab);
            }
        });
        alarmedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g=false;
                ab.putExtra("add/edit",g);
                startActivity(ab);
            }
        });

        return p;
    }


}


