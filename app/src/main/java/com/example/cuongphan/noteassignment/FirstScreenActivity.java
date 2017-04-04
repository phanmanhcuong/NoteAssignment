package com.example.cuongphan.noteassignment;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        LinearLayout lnLayoutScreen = (LinearLayout)findViewById(R.id.ln_layout);
        int linebreak = 0;
        if(noteList != null){
            TableLayout tb_layout = new TableLayout(this);
            tb_layout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            for (Note note : noteList){
                LinearLayout linearLayoutNote = createLinearLayout(note);
                if(linebreak % 2 == 0){
                    TableRow tb_row = new TableRow(this);
                    tb_row.addView(linearLayoutNote);
                    tb_layout.addView(tb_row);
                }
                else{
                    TableRow tb_row = (TableRow)tb_layout.getChildAt(tb_layout.getChildCount()-1);
                    tb_row.addView(linearLayoutNote);
                }
                linebreak++;
            }
        lnLayoutScreen.addView(tb_layout);
        }
        else{
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            lnLayoutScreen.addView(textView);
        }
    }

    private LinearLayout createLinearLayout(Note note){
        LinearLayout lnLayout = new LinearLayout(this);
        lnLayout.setOrientation(LinearLayout.VERTICAL);
        String title, content, createdTime;
        title = note.getTitle();
        content = note.getContent();
        createdTime = note.getCreateTime();

        TextView tv_title = new TextView(this);
        tv_title.setText(title);

        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        view.setBackgroundColor(getResources().getColor(R.color.colorSilver));

        TextView tv_content = new TextView(this);
        tv_content.setText(content);

        TextView tv_createdTime = new TextView(this);
        tv_createdTime.setGravity(Gravity.RIGHT);
        tv_createdTime.setText(createdTime);
        tv_createdTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.creadted_date_text_size));

        lnLayout.addView(tv_title);
        lnLayout.addView(view);
        lnLayout.addView(tv_content);
        lnLayout.addView(tv_createdTime);
        lnLayout.setPadding(20, 0, 0, 20);
        lnLayout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        return lnLayout;
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
