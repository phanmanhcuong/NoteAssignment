package com.example.cuongphan.noteassignment;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class FirstScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.note);
        shownotes();
    }

    private void shownotes() {
        SqliteHandler db = new SqliteHandler(this);
        List<Note> noteList = db.getAllNotes();
        String title, content, createdTime;
        int linebreak = 0;
        if(noteList != null){
            TableLayout tb_layout = new TableLayout(this);
            for (Note note : noteList){
                if(linebreak % 2 == 0){
                    TableRow tb_row = new TableRow(this);

                    title = note.getTitle();
                    content = note.getContent();
                    createdTime = note.getCreateTime();

                    LinearLayout lnLayout = new LinearLayout(this);
                    TextView tv_title = new TextView(this);
                    tv_title.setText(title);

                    View view = new View(this);
                    view.getLayoutParams().height = 2;
                    view.setBackgroundColor(getResources().getColor(R.color.colorBlack));

                    TextView tv_content = new TextView(this);
                    tv_content.setText(content);

                    TextView tv_createdTime = new TextView(this);
                    tv_createdTime.setGravity(Gravity.RIGHT);
                    tv_createdTime.setText(createdTime);

                    lnLayout.addView(tv_title);
                    lnLayout.addView(view);
                    lnLayout.addView(tv_content);
                    lnLayout.addView(tv_createdTime);

                    tb_row.addView(lnLayout);
                    tb_layout.addView(tb_row);
                }

            }

        }
        else{

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.first_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.item_add){
            Intent intent = new Intent(this, SecondScreenActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
