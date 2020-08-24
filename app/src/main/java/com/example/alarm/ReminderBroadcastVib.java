package com.example.alarm;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class ReminderBroadcastVib extends BroadcastReceiver {
    String a;
    int h,m;
    @Override
    public void onReceive(Context context, Intent intent) {
       /* h= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        m= Calendar.getInstance().get(Calendar.MINUTE);
        a= h+":"+m;
        Intent myapp= new Intent(context,alarmedit.class);
        myapp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                myapp, 0);
        final Ringtone r = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        r.play();
        NotificationCompat.Builder b = new NotificationCompat.Builder(context,"ALARM");
        b.setSmallIcon(R.drawable.icon)
                .setContentTitle("TIMER")
                .setContentIntent(pendingIntent)
                .setContentText(a)
                .setVibrate(new long[] { 1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        Notification b1 = b.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,b1);
        */
    }
}