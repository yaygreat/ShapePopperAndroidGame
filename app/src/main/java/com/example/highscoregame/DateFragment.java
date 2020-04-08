/******************************************************************************
 * Fragment for the date picker
 * Fills in the date field with chosen date: MM-DD-YYY
 ******************************************************************************/

package com.example.highscoregame;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

//https://developer.android.com/guide/topics/ui/controls/pickers#java
public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        int mth = month + 1;
        String m = String.valueOf(mth);
        if (m.length() == 1) {
            m = "0" + m;
        }
        String d = String.valueOf(day);
        if (d.length() == 1) {
            d = "0" + d;
        }

        ((TextInputEditText) getActivity().findViewById(R.id.dateEditText)).setText((m + "-" + d + "-" + year));
    }

}
