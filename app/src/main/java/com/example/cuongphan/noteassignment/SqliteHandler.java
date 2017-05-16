package com.example.cuongphan.noteassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuongPhan on 4/3/2017.
 */

public class SqliteHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NOTES_APP";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "NOTE";
    private static final String NOTE_ID  = "id";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private static final String NOTE_DATE_HOUR = "date_hour";
    private static final String NOTE_CREATED_TIME = "created_time";
    private static final String NOTE_PICTURE = "picture";
    public SqliteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ TABLE_NAME + "(" + NOTE_ID + " INTEGER PRIMARY KEY," + NOTE_TITLE +
                " TEXT," + NOTE_CONTENT + " TEXT, " + NOTE_CREATED_TIME + " TEXT," + NOTE_DATE_HOUR + " TEXT," + NOTE_PICTURE +
                " BLOB);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
        if(newVersion > oldVersion){
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NOTE_PICTURE + " BLOB;");
        }
    }

    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        byte[] byteArrayPicture = null;
        values.put(NOTE_TITLE, note.getTitle());
        values.put(NOTE_CONTENT, note.getContent());
        values.put(NOTE_CREATED_TIME, note.getCreateTime());
        values.put(NOTE_DATE_HOUR, note.getDateHour());
        if((byteArrayPicture = note.getByteArrayPicture()) != null){
            values.put(NOTE_PICTURE, byteArrayPicture);
        }
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateData(ContentValues contentValues, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
        db.close();
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

    public byte[] getPicture(int id){
        byte[] byteArrayPicture = null;
        String selectQuery = "SELECT picture FROM " + TABLE_NAME + " WHERE " + NOTE_ID + " = " + id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            byteArrayPicture = cursor.getBlob(0);
        }
        return byteArrayPicture;
    }

    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NOTE_ID + " = ?", new String[]{String.valueOf(id)});
        //db.execSQL("DELETE FROM TABLE "+ TABLE_NAME + "WHERE " + NOTE_ID + "= '" + id +"'");
        db.close();
    }

    public void deletePictureFromNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET picture = null WHERE " + NOTE_ID + " = " + id);
        db.close();
    }

    public String getNoteTitle(int note_id){
        String selectQuery = "SELECT title FROM " + TABLE_NAME + " WHERE id = " + note_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }
        return null;
    }

    public String getNoteContent(int note_id) {
        String selectQuery = "SELECT content FROM " + TABLE_NAME + " WHERE id = " + note_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor  = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }
        return null;
    }
}
