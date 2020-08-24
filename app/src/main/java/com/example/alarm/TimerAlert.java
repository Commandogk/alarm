package com.example.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TimerAlert extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final Ringtone r = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        r.play();
        Intent myapp1= new Intent(context,firstpage.class);
        myapp1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0, myapp1, 0);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context,"Timer");
        builder.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.icon)
                .setContentText("Time Up")
                .setContentIntent(pendingIntent1)
                .setVibrate(new long[] { 1000, 1000})
                .setContentTitle("Timer");
        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(100,builder.build());
    }
}
