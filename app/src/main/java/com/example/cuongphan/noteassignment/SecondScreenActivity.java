package com.example.cuongphan.noteassignment;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by CuongPhan on 3/28/2017.
 */

public class SecondScreenActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        //add icon to actionbar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.note);

        //set date to right top corner
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date today = new Date();
        TextView tv_date = (TextView)findViewById(R.id.tv_date);
        tv_date.setText(dateFormat.format(today).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(this, FirstScreenActivity.class);
                startActivity(intent);
                return true;

            case R.id.item_background:
                displayPopUpWindow();
                return true;

            case R.id.item_check:
                saveDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayPopUpWindow() {
        popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.popup_window, null);
        view.setBackgroundColor(getResources().getColor(R.color.colorCyan));

        popupWindow.setContentView(view);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public void changeColor(View view){
        ColorDrawable colorDrawable = (ColorDrawable)view.getBackground();
        int color = colorDrawable.getColor();
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.screen2_id);
        relativeLayout.setBackgroundColor(color);
        popupWindow.dismiss();
    }

    private void saveDatabase() {
        SqliteHandler db = new SqliteHandler(this);

        EditText et_title = (EditText)findViewById(R.id.et_title);
        String title = et_title.getText().toString();

        EditText et_content = (EditText)findViewById(R.id.et_content);
        String content = et_content.getText().toString();

        TextView tv_createdTime = (TextView)findViewById(R.id.tv_date);
        String createdTime = tv_createdTime.getText().toString();

        Button btn_datePicker = (Button)findViewById(R.id.btnDate);
        String date = btn_datePicker.getText().toString();

        Button btn_hourPicker = (Button)findViewById(R.id.btnTime);
        String hour = btn_hourPicker.getText().toString();

        String dateHour = date + " "+ hour;

        Note note = new Note(title, content, createdTime, dateHour);
        db.addNote(note);

    }
    public void showDateTimePicker(View view){
        LinearLayout alarmLayout = (LinearLayout)findViewById(R.id.lnlayout_alarm);
        TextView tvAlarm = (TextView)findViewById(R.id.tv_alarm);
        tvAlarm.setVisibility(View.GONE);

        //add date and time picker buttons
        LinearLayout datetimeLayout = (LinearLayout)findViewById(R.id.lnlayout_datetime);
        if(null == datetimeLayout){
            datetimeLayout = new LinearLayout(this);
            datetimeLayout.setId(R.id.lnlayout_datetime);

            Button btnDate = new Button(this);
            btnDate.setId(R.id.btnDate);
            btnDate.setText(R.string.date);
            btnDate.setOnClickListener(new showDatePicker());
            datetimeLayout.addView(btnDate);
            //linearLayout.addView(btnDate);

            Button btnTime = new Button(this);
            btnTime.setId(R.id.btnTime);
            btnTime.setText(R.string.time);
            btnTime.setOnClickListener(new showTimePicker());
            datetimeLayout.addView(btnTime);
            //linearLayout.addView(btnTime);

            ImageButton btnExit = new ImageButton(this);
            btnExit.setImageResource(R.drawable.quit);
            btnExit.setOnClickListener(new exitPicker());
            datetimeLayout.addView(btnExit);

            alarmLayout.addView(datetimeLayout);
        }
        else{
            datetimeLayout.setVisibility(View.VISIBLE);
        }


    }

    private class showDatePicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getFragmentManager(), "date picker");
        }
    }

    private class showTimePicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getFragmentManager(), "time picker");
        }
    }

    private class exitPicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LinearLayout datetimeLayout = (LinearLayout)findViewById(R.id.lnlayout_datetime);
            datetimeLayout.setVisibility(View.GONE);

            TextView textView = (TextView)findViewById(R.id.tv_alarm);
            textView.setVisibility(View.VISIBLE);
        }
    }
}
