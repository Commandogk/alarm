package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class alarmedit extends AppCompatActivity {
    String[] AlarmTime = new String[20];
    Calendar calendar = Calendar.getInstance();
    int p ;
    String[] t3 = new String[20];
    int[] h = new int[20], m = new int[20];
    long[] l = new long[20];
    long[] trigger = new long[20];
    RecyclerView recyclerView2;
    Boolean[] Vib = new Boolean[20];
    boolean j = false;
    SharedPreferences sharedPref;
    FloatingActionButton back, del;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    TextClock f;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmedit);
        f = findViewById(R.id.er);
        del = findViewById(R.id.del);
        recyclerView2 = findViewById(R.id.rc);
        creatchannel();
        back = findViewById(R.id.back);
        sharedPref = getSharedPreferences("mydata", MODE_PRIVATE);
        x = sharedPref.getInt("count", 0);
        load();
        Intent ab = getIntent();
        j = ab.getBooleanExtra("add", false);
        if (j) {
            h[x] = ab.getIntExtra("HOUR", 0);
            m[x] = ab.getIntExtra("MIN", 0);
            Vib[x] = ab.getBooleanExtra("CheckBox", false);
            if (m[x] < 10) {
                t3[x] = h[x] + ":" + "0" + m[x] + " ";
            } else {
                t3[x] = h[x] + ":" + m[x] + " ";
            }
            AlarmTime[x] = t3[x];
            settime(x);
            ++x;
        }
        if (x > 0) {
            order(x);
            setTrigger(x);
            alertnot();
            loadrecycler();
        }
        else{
            loadrecycler();
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int del;
                final EditText editText = findViewById(R.id.delpos);
                del = Integer.parseInt(editText.getText().toString());
                //Toast.makeText(getApplicationContext(),""+del+":"+x,Toast.LENGTH_SHORT).show();
                if (x > 0 && del <= x && del > 0) {
                    if (x == 1) {
                        t3[0] = null;
                        h[0] = 0;
                        m[0] = 0;
                        l[0] = -1L;
                        AlarmTime[0] = null;
                        x--;
                    } else {
                        for (int i = del - 1; i < x; i++) {
                            t3[i] = t3[i + 1];
                            h[i] = h[i + 1];
                            m[i] = m[i + 1];
                            l[i] = l[i + 1];
                            AlarmTime[i] = AlarmTime[i + 1];
                        }
                        --x;
                        save();
                        order(x);
                        setTrigger(x);
                    }
                    Toast.makeText(getApplicationContext(), "" + x, Toast.LENGTH_SHORT).show();
                    loadrecycler();
                    alertnot();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
                Intent t = new Intent(alarmedit.this, firstpage.class);
                startActivity(t);
            }
        });

    }

    private void order(int t) {
        if (t > 1) {
            for (int i = 0; i < t; i++) {
                for (int j = i + 1; j < t; j++) {
                    if (l[i] > l[j]) {
                        long temp = l[i];
                        l[i] = l[j];
                        l[j] = temp;
                    }
                }
            }
        }

        for (int j = 0; j < t; j++) {
            //  Toast.makeText(getApplicationContext(),""+l[j],Toast.LENGTH_SHORT).show();
        }

    }

    private void settime(int q) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, h[q]);
        calendar.set(Calendar.MINUTE, m[q]);
        calendar.set(Calendar.SECOND, 0);
        l[q] = calendar.getTimeInMillis();
    }

    private void setTrigger(int count) {
        long t = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            trigger[i] = l[i] - t;
            // Toast.makeText(getApplicationContext(), "" + i + "\n" +trigger[i]+"\n"+x, Toast.LENGTH_SHORT).show();
        }

    }

    private void creatchannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm reminder";
            String description = h[x] + ":" + m[x];
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ALARM", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void alertnot() {
        if (x >= 1) {
            Toast.makeText(getApplicationContext(), "passed1", Toast.LENGTH_SHORT).show();
            if (x == 1&&trigger[0] > 0L) {
                Toast.makeText(getApplicationContext(), "" + 0 + "\n" + trigger[0], Toast.LENGTH_SHORT).show();
                p = 0;
            } else {
                for (int i = 0; i < x; i++) {
                    if (trigger[i] > 0L && trigger[i - 1] < 0L) {
                        Toast.makeText(getApplicationContext(), "" + i + "\n" + trigger[i], Toast.LENGTH_SHORT).show();
                        p = i;
                        break;
                    }
                }
                if(p>=0){
                    Intent intent = new Intent(alarmedit.this, ReminderBroadcast.class);
                    pendingIntent = PendingIntent.getBroadcast(alarmedit.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                else {
                    pendingIntent.cancel();
                }
            }
        }
        else{
            pendingIntent.cancel();
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +trigger[p], pendingIntent);
    }


    private void loadrecycler() {
        MyAdapter2 myAdapter2;
        myAdapter2 = new MyAdapter2(getApplicationContext(), AlarmTime, x, Vib);
        recyclerView2.setAdapter(myAdapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void load() {
        sharedPref = getSharedPreferences("mydata", MODE_PRIVATE);
        for (int i = 0; i < x; i++) {
            String re = "one" + i + "";
            String wq = "two" + i + "";
            t3[i] = sharedPref.getString("t3" + i, null);
            AlarmTime[i] = sharedPref.getString(re, null);
            h[i] = sharedPref.getInt("h" + i, 0);
            m[i] = sharedPref.getInt("m" + i, 0);
            l[i] = sharedPref.getLong("l" + i, 0L);
            Vib[i] = sharedPref.getBoolean(wq, false);
        }
    }

    private void save() {
        sharedPref = getSharedPreferences("mydata", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (x < 20) {
            editor.putInt("count", x);
            editor.commit();
            for (int i = 0; i < x; i++) {
                String r = "one" + i + "";
                String ty = "two" + i + "";
                editor.putString("t3" + i, t3[i]);
                editor.putString(r, AlarmTime[i]);
                editor.putInt("h" + i, h[i]);
                editor.putLong("l" + i, l[i]);
                editor.putInt("m" + i, m[i]);
                editor.putBoolean(ty, Vib[i]);
                editor.commit();
            }
        } else {
            x = 0;
            editor.putInt("count", x);
            editor.commit();
            for (int i = 0; i < 20; i++) {
                String r = "one" + i + "";
                String ty = "two" + i + "";
                editor.putString("t3" + i, t3[i]);
                editor.putString(r, AlarmTime[i]);
                editor.putInt("h" + i, h[i]);
                editor.putLong("l" + i, l[i]);
                editor.putInt("m" + i, m[i]);
                editor.putBoolean(ty, Vib[i]);
                editor.commit();
            }
        }
    }
}

