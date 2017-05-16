package com.example.cuongphan.noteassignment;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CuongPhan on 3/28/2017.
 */

public class NoteDetail extends AppCompatActivity {
    private static final int DATEPICKER_FLAG = 1;
    private static final int TIMEPICKER_FLAG = 2;
    private static final int EXITDATETIMEPICKER_FLAG = 3;
    private static final int DELETENOTE_FLAG = 4;
    private static final int SAVEFILE_FLAG = 5;
    private static final int DELETE_IMAGE_FLAG = 6;
    private PopupWindow popupWindow;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_LOAD_IMAGE = 2;
    private Note note = new Note();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetail);

        //add icon to actionbar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.note);

        //set date to right top corner
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date today = new Date();
        TextView tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(dateFormat.format(today).toString());

        //get bundle and show on screen
        Intent intent = getIntent();
        Bundle info = intent.getExtras();
        if (info != null) {
            //get note id to check if note is already exist to update that note
            note.setId(info.getInt(getResources().getString(R.string.note_id)));
            if(note.getId() != 0) {
                SqliteHandler db = new SqliteHandler(this);
                byte[] byteArrayPicture = db.getPicture(info.getInt(getResources().getString(R.string.note_id)));
                if (byteArrayPicture != null) {
                    ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
                    iv_picture.setImageBitmap(BitmapFactory.decodeByteArray(byteArrayPicture, 0, byteArrayPicture.length));
                    iv_picture.setOnClickListener(new OnClickEventHandler(DELETE_IMAGE_FLAG, note.getId()));
                }
            }
            else{
                byte[] byteArrayPicture = info.getByteArray(getResources().getString(R.string.picture));
                if (byteArrayPicture != null) {
                    ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
                    iv_picture.setImageBitmap(BitmapFactory.decodeByteArray(byteArrayPicture, 0, byteArrayPicture.length));
                    iv_picture.setOnClickListener(new OnClickEventHandler(DELETE_IMAGE_FLAG, note.getId()));
                }
            }
            TextView tv_createdTime = (TextView) findViewById(R.id.tv_date);
            tv_createdTime.setText(info.getString(getResources().getString(R.string.createdTime)));

            EditText etTitle = (EditText) findViewById(R.id.et_title);
            etTitle.setText(info.getString(getResources().getString(R.string.title)));

            EditText etContent = (EditText) findViewById(R.id.et_content);
            etContent.setText(info.getString(getResources().getString(R.string.content)));

            TextView tvAlarm = (TextView) findViewById(R.id.tv_alarm);
            tvAlarm.setVisibility(View.GONE);

            LinearLayout alarmLayout = (LinearLayout) findViewById(R.id.lnlayout_alarm);
            LinearLayout datetimeLayout = new LinearLayout(this);
            //datetimeLayout.setId(R.id.lnlayout_datetime);

            Button btnDate = new Button(this);
            btnDate.setId(R.id.btnDate);
            btnDate.setText(info.getString(getResources().getString(R.string.date_2)));
            btnDate.setOnClickListener(new OnClickEventHandler(DATEPICKER_FLAG));
            datetimeLayout.addView(btnDate);

            Button btnTime = new Button(this);
            btnTime.setId(R.id.btnTime);
            btnTime.setText(info.getString(getResources().getString(R.string.hour)));
            btnTime.setOnClickListener(new OnClickEventHandler(TIMEPICKER_FLAG));
            datetimeLayout.addView(btnTime);

            alarmLayout.addView(datetimeLayout);

            //create bottom toolbar to delete and load file
            Toolbar bottomToolbar = new Toolbar(this);
            Toolbar.LayoutParams toolbarParams = new Toolbar.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            bottomToolbar.setLayoutParams(toolbarParams);
            bottomToolbar.setVisibility(View.VISIBLE);
            LinearLayout lnlayout_toolbar = new LinearLayout(this);

            ImageButton imgbtn_savefile = new ImageButton(this);
            imgbtn_savefile.setId(R.id.imgbtn_loadfile);
            imgbtn_savefile.setImageResource(R.drawable.savefile);
            imgbtn_savefile.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            imgbtn_savefile.setBackgroundColor(Color.TRANSPARENT);
            imgbtn_savefile.setOnClickListener(new OnClickEventHandler(SAVEFILE_FLAG, info.getInt(getResources().getString(R.string.note_id))));

            ImageButton imgbtn_delete = new ImageButton(this);
            imgbtn_delete.setId(R.id.imgbtn_delete);
            imgbtn_delete.setImageResource(R.drawable.delete);
            imgbtn_delete.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
            imgbtn_delete.setBackgroundColor(Color.TRANSPARENT);
            imgbtn_delete.setOnClickListener(new OnClickEventHandler(DELETENOTE_FLAG, info.getInt(getResources().getString(R.string.note_id))));

            lnlayout_toolbar.addView(imgbtn_savefile);
            lnlayout_toolbar.addView(imgbtn_delete);
            lnlayout_toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            RelativeLayout relativeLayout_screen2 = (RelativeLayout) findViewById(R.id.rl_screen2);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            relativeLayout_screen2.addView(lnlayout_toolbar, params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notedetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, NoteList.class);
                startActivity(intent);
                return true;

            case R.id.item_background:
                popupWindowColor();
                return true;

            case R.id.item_check:
                saveDatabase();
                return true;

            case R.id.item_camera:
                popupWindowPhoto();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void popupWindowPhoto() {
        popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.popup_loadpicture, null);
        popupWindow.setContentView(view);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //dim background
        View container;
        container = (View)popupWindow.getContentView().getParent();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND; // add a flag here instead of clear others
        p.dimAmount = 0.7f;
        wm.updateViewLayout(container, p);
    }

    public void takePhotoHandle(View v) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void choosePhotoHandle(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_LOAD_IMAGE);
    }

    //called when taking photo from camera or choose picture from gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap picture = null;
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            picture = (Bitmap) data.getExtras().get("data");
        } else if (requestCode == GALLERY_LOAD_IMAGE && resultCode == RESULT_OK) {
            //get the image from data
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();

            picture = BitmapFactory.decodeFile(imgDecodableString);
        }

//        ExecutorService updateImageView = Executors.newFixedThreadPool(5);
//        updateImageView.execute(new UpdateImageView(picture));
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
        iv_picture.setImageBitmap(picture);
    }

    private void popupWindowColor() {
        popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.popup_window, null);
        view.setBackgroundColor(getResources().getColor(R.color.colorCyan));

        popupWindow.setContentView(view);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //dim background
        View container;
        container = (View)popupWindow.getContentView().getParent();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND; // add a flag here instead of clear others
        p.dimAmount = 0.7f;
        wm.updateViewLayout(container, p);
    }

    public void changeColor(View view) {
        ColorDrawable colorDrawable = (ColorDrawable) view.getBackground();
        int color = colorDrawable.getColor();
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.screen2_id);
        relativeLayout.setBackgroundColor(color);
        popupWindow.dismiss();
    }

    private void saveDatabase() {
        SqliteHandler db = new SqliteHandler(this);
        //Note note;

        EditText et_title = (EditText) findViewById(R.id.et_title);
        String title = et_title.getText().toString();

        EditText et_content = (EditText) findViewById(R.id.et_content);
        String content = et_content.getText().toString();

        TextView tv_createdTime = (TextView) findViewById(R.id.tv_date);
        String createdTime = tv_createdTime.getText().toString();

        Button btn_datePicker = (Button) findViewById(R.id.btnDate);
        String date = btn_datePicker.getText().toString();

        Button btn_hourPicker = (Button) findViewById(R.id.btnTime);
        String hour = btn_hourPicker.getText().toString();

        String dateHour = date + " " + hour;

        // if note is not exist
        byte[] byteArray = null;
        ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
        if (note.getId() == 0) {
            if (iv_picture.getDrawable() == null) {
                note = new Note(title, content, createdTime, dateHour);
            } else {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap picture = ((BitmapDrawable) iv_picture.getDrawable()).getBitmap();
                picture.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byteArray = stream.toByteArray();
                note = new Note(title, content, createdTime, dateHour, byteArray);
            }
            db.addNote(note);
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(getResources().getString(R.string.title), title);
            contentValues.put(getResources().getString(R.string.content), content);
            contentValues.put(getResources().getString(R.string.createdTime), createdTime);
            contentValues.put("date_hour", dateHour);
            if (iv_picture.getDrawable() != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap picture = ((BitmapDrawable) iv_picture.getDrawable()).getBitmap();
                picture.compress(Bitmap.CompressFormat.PNG, 0, stream);
                byteArray = stream.toByteArray();

                //if note had a picture
                if (db.getPicture(note.getId()) != null) {
                    //imageview doesn't change note picture
                    if (!db.getPicture(note.getId()).equals(byteArray)) {
                        contentValues.put(getResources().getString(R.string.picture), byteArray);
                    }
                } else {
                    contentValues.put(getResources().getString(R.string.picture), byteArray);
                }
            }
            db.updateData(contentValues, note.getId());
        }

        alarmNotification(note.getId(), title, content, createdTime, date, hour, byteArray);

        Intent intent = new Intent(this, NoteList.class);
        startActivity(intent);
    }

    //set alarm notification
    private void alarmNotification(int noteId, String title, String content, String createdTime, String date, String hour, byte[] byteArray) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String[] year_month_date;
        String[] hour_minute;
        year_month_date = date.split("-");
        hour_minute = hour.split(":");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(year_month_date[2]));
        cal.set(Calendar.MONTH, Integer.valueOf(year_month_date[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(year_month_date[0]));
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour_minute[0]));
        cal.set(Calendar.MINUTE, Integer.valueOf(hour_minute[1]));
        cal.set(Calendar.SECOND, 0);

        AlarmReciever alarmReciever = new AlarmReciever();
        alarmReciever.setTitleContent(noteId, title, content, createdTime, date, hour, byteArray); //send title and content to AlarmReciever class
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
    }

    //called when alarm layout is clicked
    public void showDateTimePicker(View view) {
        LinearLayout alarmLayout = (LinearLayout) findViewById(R.id.lnlayout_alarm);
        TextView tvAlarm = (TextView) findViewById(R.id.tv_alarm);
        tvAlarm.setVisibility(View.GONE);

        LinearLayout datetimeLayout = (LinearLayout) findViewById(R.id.lnlayout_datetime);
        if (null == datetimeLayout) {
            datetimeLayout = new LinearLayout(this);
            datetimeLayout.setId(R.id.lnlayout_datetime);

            Button btnDate = new Button(this);
            btnDate.setId(R.id.btnDate);
            btnDate.setText(R.string.date);
            btnDate.setOnClickListener(new OnClickEventHandler(DATEPICKER_FLAG));
            datetimeLayout.addView(btnDate);

            Button btnTime = new Button(this);
            btnTime.setId(R.id.btnTime);
            btnTime.setText(R.string.time);
            btnTime.setOnClickListener(new OnClickEventHandler(TIMEPICKER_FLAG));
            datetimeLayout.addView(btnTime);

            ImageButton btnExit = new ImageButton(this);
            btnExit.setImageResource(R.drawable.quit);
            btnExit.setOnClickListener(new OnClickEventHandler(EXITDATETIMEPICKER_FLAG));
            datetimeLayout.addView(btnExit);

            alarmLayout.addView(datetimeLayout);
        } else {
            datetimeLayout.setVisibility(View.VISIBLE);
        }
    }

    private class OnClickEventHandler implements View.OnClickListener {
        int actionFlag;
        int id;

        public OnClickEventHandler(int eventFlag) {
            actionFlag = eventFlag;
        }

        public OnClickEventHandler(int eventFlag, int note_id) {
            actionFlag = eventFlag;
            id = note_id;
        }

        @Override
        public void onClick(View v) {
            switch (actionFlag) {
                case DATEPICKER_FLAG:
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getFragmentManager(), "date picker");
                    break;
                case TIMEPICKER_FLAG:
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getFragmentManager(), "time picker");
                    break;
                case EXITDATETIMEPICKER_FLAG:
                    LinearLayout datetimeLayout = (LinearLayout) findViewById(R.id.lnlayout_datetime);
                    datetimeLayout.setVisibility(View.GONE);

                    TextView textView = (TextView) findViewById(R.id.tv_alarm);
                    textView.setVisibility(View.VISIBLE);
                    break;
                case DELETENOTE_FLAG:
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoteDetail.this);
                    builder.setCancelable(true);
                    builder.setMessage(R.string.quit);
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SqliteHandler db = new SqliteHandler(NoteDetail.this);
                            db.deleteNote(id);

                            Intent intent = new Intent(NoteDetail.this, NoteList.class);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
                case SAVEFILE_FLAG:
                    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                        File Directory = new File(Environment.getExternalStorageDirectory(), "Notes");
                        if (!Directory.exists()) {
                            Directory.mkdirs();
                        }
                        File note = new File(Directory, "Note.txt");
                        String fileContent;
                        SqliteHandler db = new SqliteHandler(NoteDetail.this);
                        String title = db.getNoteTitle(id);
                        String content = db.getNoteContent(id);
                        fileContent = "Title: " + title + "\n" + "Content: " + content;
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter(note);
                            writer.append(fileContent);
                            writer.flush();
                            writer.close();
                            Toast.makeText(NoteDetail.this, R.string.Save_file_successfully, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(NoteDetail.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                        }
                    }
                    else{
                        Toast.makeText(NoteDetail.this, R.string.SD_card_is_not_mounted_now, Toast.LENGTH_LONG).show();
                    }
                    break;
                case DELETE_IMAGE_FLAG:
                    AlertDialog.Builder builderImage = new AlertDialog.Builder(NoteDetail.this);
                    builderImage.setCancelable(true);
                    builderImage.setMessage(R.string.quit);
                    builderImage.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SqliteHandler db = new SqliteHandler(NoteDetail.this);
                            db.deletePictureFromNote(id);

                            ImageView iv_picture = (ImageView) findViewById(R.id.iv_picture);
                            iv_picture.setImageBitmap(null);
                        }
                    });

                    builderImage.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do nothing
                        }
                    });
                    AlertDialog dialog_image = builderImage.create();
                    dialog_image.show();
                    break;
            }
        }
    }
}
