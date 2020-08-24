package com.example.alarm;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ServiceLoader;

public class timer extends Fragment {
    FloatingActionButton play, stop, enter;
    EditText minutes, seconds;
    CountDownTimer countDownTimer;
    PendingIntent pendingIntent1;
    AlarmManager alarmManager1;
    TextView counter;
    String x;
    NotificationCompat.Builder timernotify;
    Long timeleftinmilli = 0L, min = 0L, sec = 0L;
    boolean timerunning;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public timer() {
        // Required empty public constructor
    }


    public static timer newInstance(String param1, String param2) {
        timer fragment = new timer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        notitimer();
        View v = inflater.inflate(R.layout.fragment_timer, container, false);
        play = v.findViewById(R.id.fab5);
        stop = v.findViewById(R.id.fab7);
        enter = v.findViewById(R.id.fab8);
        minutes = v.findViewById(R.id.minuteinput);
        seconds = v.findViewById(R.id.secondinput);
        counter = v.findViewById(R.id.counter);
        play.setEnabled(false);
        stop.setEnabled(false);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                min = Long.parseLong(minutes.getText().toString());
                sec = Long.parseLong(seconds.getText().toString());
                if (minutes.getText().toString().isEmpty()||seconds.getText().toString().isEmpty())  {
                    Toast.makeText(getContext(), "Enter valid time", Toast.LENGTH_SHORT).show();
                } else if((min > 59) || (sec > 59)){
                    Toast.makeText(getContext(), "Enter valid time", Toast.LENGTH_SHORT).show();
                }
                else {
                    timeleftinmilli = ((min * 60 * 1000) + (sec * 1000));
                    play.setEnabled(true);
                    stop.setEnabled(true);
                    if ((min < 10) && (sec < 10)) {
                        x = "0" + min + ":" + "0" + sec;
                    } else if (sec < 10) {
                        x = min + ":" + "0" + sec;
                    } else if ((min < 10)) {
                        x = "0" + min + ":" + sec;
                    } else {
                        x = min + ":" + sec;
                    }
                    counter.setText(x);
                }

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enter.setEnabled(false);
                minutes.setEnabled(false);
                seconds.setEnabled(false);
                Intent intent = new Intent(getContext(), TimerAlert.class);
                pendingIntent1 = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                alarmManager1 = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeleftinmilli, pendingIntent1);
                countDownTimer = new CountDownTimer(timeleftinmilli, 1000) {
                    @Override
                    public void onTick(long l) {
                        timeleftinmilli = l;
                        updatetext();
                    }

                    @Override
                    public void onFinish() {
                        onStop();

                    }


                }.start();
                timerunning = true;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Ringtone r = RingtoneManager.getRingtone(getContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                r.stop();
                alarmManager1.cancel(pendingIntent1);
                minutes.setEnabled(true);
                seconds.setEnabled(true);
                enter.setEnabled(true);
                play.setEnabled(false);
                countDownTimer.cancel();
                timerunning = false;
                timeleftinmilli = 0L;
                min = 0L;
                sec = 0L;
            }
        });
        return v;
    }

    private void notitimer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm";
            String description = "Timer Over";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Timer", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updatetext() {
        min = timeleftinmilli / (60000);
        sec = ((timeleftinmilli % 60000) / 1000);
        if ((min < 10) && (sec < 10)) {
            x = "0" + min + ":" + "0" + sec;

        } else if (sec < 10) {
            x = min + ":" + "0" + sec;
        } else if ((min < 10)) {
            x = "0" + min + ":" + sec;
        } else {
            x = min + ":" + sec;
        }
        counter.setText(x);
    }
}