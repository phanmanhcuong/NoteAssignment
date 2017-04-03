package com.example.cuongphan.noteassignment;

/**
 * Created by CuongPhan on 4/3/2017.
 */

public class Note {
    public int id;
    public String title;
    public String content;
    public String createTime;
    public String dateHour;

    public Note() {

    }

    public void Note(String title, String content, String createTime){
        this.title = title;
        this.content = content;
        this.createTime = createTime;
    }
    public Note(int id, String title, String content, String createTime, String dateHour){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.dateHour = dateHour;
    }

    public Note(String title, String content, String createTime, String dateHour){
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.dateHour = dateHour;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public void setDateHour(String dateHour){
        this.dateHour = dateHour;
    }
    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }
    public String getCreateTime(){
        return createTime;
    }

    public String getDateHour(){
        return dateHour;
    }
}
