package com.example.cuongphan.noteassignment;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

public class NoteList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notelist);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.note);
        shownotes();
    }

    private void shownotes(){
        SqliteHandler db = new SqliteHandler(this);
        List<Note> noteList = db.getAllNotes();

        final GridView gv_notelist = (GridView)findViewById(R.id.gv_notelist);
        gv_notelist.setAdapter(new AdapterNoteList(this, noteList));

        gv_notelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object object = gv_notelist.getItemAtPosition(position);
                Note note = (Note)object;

                Intent intent = new Intent(NoteList.this, NoteDetail.class);
                Bundle info = new Bundle();
                String[] datehour;
                datehour = note.getDateHour().split(" ");
                info.putString(getResources().getString(R.string.title), note.getTitle());
                info.putString(getResources().getString(R.string.content), note.getContent());
                info.putString(getResources().getString(R.string.createdTime), note.getCreateTime());
                info.putString(getResources().getString(R.string.date_2), datehour[0]);
                info.putString(getResources().getString(R.string.hour), datehour[1]);
                info.putInt(getResources().getString(R.string.note_id), note.getId());
                intent.putExtras(info);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.notelist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_add){
            Intent intent = new Intent(this, NoteDetail.class);
            startActivity(intent);
        }
        return true;
    }
}
