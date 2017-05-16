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
    private static byte[] byteArray;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder buider = new NotificationCompat.Builder(context);
        buider.setSmallIcon(R.drawable.note_notification);
        buider.setContentTitle(title);
        buider.setContentText(content);

        Intent intentNotification = new Intent(context, NoteDetail.class);
        Bundle noteInfo = new Bundle();
        noteInfo.putInt(context.getResources().getString(R.string.note_id), noteId);
        noteInfo.putString(context.getResources().getString(R.string.title), title);
        noteInfo.putString(context.getResources().getString(R.string.content), content);
        noteInfo.putString(context.getResources().getString(R.string.createdTime), createdTime);
        noteInfo.putString(context.getResources().getString(R.string.date_2), date);
        noteInfo.putString(context.getResources().getString(R.string.hour), hour);
        noteInfo.putByteArray(context.getResources().getString(R.string.picture), byteArray);
        intentNotification.putExtras(noteInfo);

        PendingIntent showPopupNote = PendingIntent.getActivity(context, 1, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);
        buider.setContentIntent(showPopupNote);

        buider.setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, buider.build());

        ShowNotificaiton showNotificaiton = new ShowNotificaiton();
        showNotificaiton.setTitleConTent(title, content);
    }

    public void setTitleContent(int noteId, String title, String content, String createdTime, String date, String hour, byte[] byteArray) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.date = date;
        this.hour = hour;
        this.byteArray = byteArray;
    }

}
