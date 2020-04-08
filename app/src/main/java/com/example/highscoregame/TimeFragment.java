/******************************************************************************
 * Fragment for the time picker
 * Fills in the time field with chosen time: hh:mm
 * Uses 24-hr time
 ******************************************************************************/

package com.example.highscoregame;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

//https://developer.android.com/guide/topics/ui/controls/pickers#java
public class TimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

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

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        String hr = String.valueOf(hourOfDay);
        if (hr.length() == 1) {
            hr = "0" + hr;
        }
        Log.d("min", String.valueOf(minute));
        String min = String.valueOf(minute);
        if (min.length() == 1) {
            min = "0" + min;
        }

        ((TextInputEditText) getActivity().findViewById(R.id.timeEditText)).setText(hr + ":" + min);
    }
}
