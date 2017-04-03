package com.example.cuongphan.noteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuongPhan on 4/3/2017.
 */

public class SqliteHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NOTES_APP";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "NOTE";
    private static final String NOTE_ID  = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private static final String NOTE_DATE_HOUR = "date_hour";
    private static final String NOTE_CREATED_TIME = "created_time";
    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + "(" + NOTE_ID + " INTEGER PRIMARY KEY," + NOTE_TITLE +
                " TEXT," + NOTE_CONTENT + " TEXT, " + NOTE_CREATED_TIME + " TEXT," + NOTE_DATE_HOUR + " TEXT" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_CONTENT, note.getContent());
        values.put(NOTE_CREATED_TIME, note.getCreateTime());
        values.put(NOTE_DATE_HOUR, note.getDateHour());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{NOTE_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_CREATED_TIME, NOTE_DATE_HOUR},
                NOTE_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4));
            return note;
        }
        return null;
    }

    public List<Note> getAllNotes(){
        List<Note> noteList = new ArrayList<Note>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setCreateTime(cursor.getString(3));
                note.setDateHour(cursor.getString(4));
                noteList.add(note);
            }while(cursor.moveToNext());
        }
        return noteList;
    }
}
