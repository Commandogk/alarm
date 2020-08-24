package com.example.alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;



public class ReminderBroadcast extends BroadcastReceiver {
    String a;
    int h,m;
    @Override
    public void onReceive(Context context, Intent intent) {
        h= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        m= Calendar.getInstance().get(Calendar.MINUTE);
        a= h+":"+m;
        Intent myapp= new Intent(context,alarmedit.class);
        myapp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                myapp, 0);
        final Ringtone r = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        r.play();
        NotificationCompat.Builder b = new NotificationCompat.Builder(context,"ALARM")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Alarm")
                .setContentIntent(pendingIntent)
                .setContentText("Alarm:"+a)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        Notification b1 = b.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(300,b1);
        Handler m = new Handler();
        m.postDelayed(new Runnable() {
            @Override
            public void run() {
                r.stop();
            }
        },15*1000);
    }
}
