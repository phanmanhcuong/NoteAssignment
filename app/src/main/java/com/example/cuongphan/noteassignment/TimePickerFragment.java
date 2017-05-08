package com.example.cuongphan.noteassignment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by CuongPhan on 3/29/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Button btnTime = (Button) getActivity().findViewById(R.id.btnTime);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String time = String.valueOf(hourOfDay)+":"+String.valueOf(minute);
        try {
            Date timePicker = timeFormat.parse(time);
            btnTime.setText(timeFormat.format(timePicker).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
