package edu.arizona.uas.glucose.scheduler;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.arizona.uas.glucose.GlucoseHistory;
import edu.arizona.uas.glucose.MyDate;

public class DailyGlucoseTracker extends IntentService {
    private static final long INTERVAL = TimeUnit.SECONDS.toMillis(60);

    private CharSequence name = "Glucose Daily Data";
    private String description = "This is a nofication channel for daily glucose data input notification";
    private String channelID = "dailyGlucoseC";


    public DailyGlucoseTracker() {
        super("Glucose Tracker");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if(GlucoseHistory.findGlucoseHistoryByDate(new MyDate(new Date())) != null)
            return;

        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), channelID)
                                                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                                                        .setContentTitle("Daily Glucose Notification")
                                                        .setContentText("Beep! Beep! Time to collect glucose data")
                                                        .setContentIntent(null)
                                                        .setAutoCancel(true)
                                                        .build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }




    public static void setServiceOn(boolean on, Context context) {
        Intent myIntent = new Intent(context, DailyGlucoseTracker.class);
        PendingIntent pi = PendingIntent.getService(context, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if(on) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), INTERVAL, pi);
            System.out.println("Alarm is on");
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }


    private void createNotificationChannel( ) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //code could break for earlier versions, so need to check

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
