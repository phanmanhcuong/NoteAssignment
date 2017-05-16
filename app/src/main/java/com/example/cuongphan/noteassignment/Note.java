package com.example.cuongphan.noteassignment;

import android.graphics.Bitmap;

/**
 * Created by CuongPhan on 4/3/2017.
 */

public class Note {
    int id;
    String title;
    String content;
    String createTime;
    String dateHour;
    byte[] byteArrayPicture;

    public Note() {}

    public Note(String title, String content, String createTime, String dateHour) {
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.dateHour = dateHour;
    }

    public Note(String title, String content, String createdTime, String dateHour, byte[] byteArray) {
        this.title = title;
        this.content = content;
        this.createTime = createdTime;
        this.dateHour = dateHour;
        this.byteArrayPicture = byteArray;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setDateHour(String dateHour) {
        this.dateHour = dateHour;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDateHour() {
        return dateHour;
    }

    public byte[] getByteArrayPicture() {
        return byteArrayPicture;
    }
}

