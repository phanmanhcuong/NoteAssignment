package com.example.cuongphan.noteassignment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


/**
 * Created by CuongPhan on 3/28/2017.
 */

public class SecondScreenActivity extends AppCompatActivity {
//    RelativeLayout relaytiveLayout = (RelativeLayout)findViewById(R.id.screen2_id);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.note);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayPopUpWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
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
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.screen2_id);
        relativeLayout.setBackgroundColor(color);
    }
}
