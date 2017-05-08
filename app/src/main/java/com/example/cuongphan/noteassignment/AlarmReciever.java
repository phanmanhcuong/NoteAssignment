package com.example.cuongphan.noteassignment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

/**
 * Created by CuongPhan on 4/10/2017.
 */

public class AlarmReciever extends BroadcastReceiver{
    private static int noteId;
    private static String title;
    private static String content;
    private static String createdTime;
    private static String date;
    private static String hour;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder buider = new NotificationCompat.Builder(context);
        buider.setSmallIcon(R.drawable.note_notification);
        buider.setContentTitle(title);
        buider.setContentText(content);

        Intent intentNotification = new Intent(context, SecondScreenActivity.class);
        Bundle noteInfo = new Bundle();
        //noteInfo.putString("flag", "alarm_reciever_flag");
        noteInfo.putInt("note_id", noteId);
        noteInfo.putString("title", title);
        noteInfo.putString("content", content);
        noteInfo.putString("createdTime", createdTime);
        noteInfo.putString("date", date);
        noteInfo.putString("hour", hour);
        intentNotification.putExtras(noteInfo);

        PendingIntent showPopupNote = PendingIntent.getActivity(context, 1, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        buider.setContentIntent(showPopupNote);

        buider.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, buider.build());

        ShowNotificaiton showNotificaiton = new ShowNotificaiton();
        showNotificaiton.setTitleConTent(title, content);
    }

    public void setTitleContent(int noteId, String title, String content, String createdTime, String date, String hour) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.date = date;
        this.hour = hour;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

}
