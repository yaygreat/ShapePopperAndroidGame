/******************************************************************************
 * TextWatcher to only allow valid time in the time field: hh:mm
 * Uses 24-hr time
 ******************************************************************************/

package com.example.highscoregame;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timeTextWatcher implements TextWatcher {

    private Button saveButton;
    private TextInputLayout nameLayout;
    private TextInputEditText nameText;
    private TextInputLayout scoreLayout;
    private TextInputEditText scoreText;
    private TextInputLayout dateLayout;
    private TextInputEditText dateText;
    private TextInputLayout timeLayout;
    private TextInputEditText timeText;

    public timeTextWatcher(Button saveButton,
                           TextInputLayout nameLayout, TextInputEditText nameText,
                           TextInputLayout scoreLayout, TextInputEditText scoreText,
                           TextInputLayout dateLayout, TextInputEditText dateText,
                           TextInputLayout timeLayout, TextInputEditText timeText) {
        this.saveButton = saveButton;
        this.nameLayout = nameLayout;
        this.nameText = nameText;
        this.scoreLayout = scoreLayout;
        this.scoreText = scoreText;
        this.dateLayout = dateLayout;
        this.dateText = dateText;
        this.timeLayout = timeLayout;
        this.timeText = timeText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    // Only accepts 0-24 for hour and 0-60 for min
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String enteredHour = "00";
        String enteredMin = "00";
        int length = 0;
        boolean timeIsValid = false;

        String working = s.toString();
        length = working.length();

        //hour
        if (length==2 && before ==0) {
            enteredHour = working.substring(0,2);

            if (Integer.parseInt(enteredHour) > 24) {
                timeIsValid = false;
                timeText.setText("");
                timeLayout.setError("Hour must be within range: 0-24");
            }
            else {
                working+=":";
                timeText.setText(working);
                timeText.setSelection(working.length());
            }
        }

        //min -- check here for : error
        else if (length==5 && before ==0) {
            enteredHour = working.substring(0,2);
            enteredMin = working.substring(3,5);

            if (Integer.parseInt(enteredMin) > 59) {
                timeIsValid = false;
                timeText.setText(working.substring(0,3));
                timeText.setSelection(working.substring(0,3).length());
                length = 3;
                timeLayout.setError("Minute must be within range: 0-59");
            }
            else if (length != 3){
                timeText.setText(working);
                timeText.setSelection(working.length());
                length = working.length();
            }
        }

        //check after all is inputted
        else if (length >= 5 && before ==0 && !timeIsValid) {
            enteredHour = working.substring(0,2);
            enteredMin = working.substring(3,5);

            if (Integer.parseInt(enteredHour) > 24) {
                timeIsValid = false;
                timeText.setText("");
                timeLayout.setError("Hour must be within range: 0-24");
            }
            else {
                working+=":";
                timeText.setText(working);
                timeText.setSelection(working.length());
            }
            if (Integer.parseInt(enteredMin) > 59) {
                timeIsValid = false;
                timeText.setText(working.substring(0,3));
                timeText.setSelection(working.substring(0,3).length());
                length = 3;
                timeLayout.setError("Minute must be within range: 0-59");
            }
            else if (length != 3){
                timeText.setText(working);
                timeText.setSelection(working.length());
                length = working.length();
            }
        }

        else {
            timeIsValid = true;
            timeLayout.setError(null);
        }

    }


    // Once all fields are valid, the save button is enabled
    @Override
    public void afterTextChanged(Editable s) {
        // checks that the date and time are not in the future
        if (dateText.getText().toString().length() ==10
                && timeText.getText().toString().length() ==5) {
            String strTime = timeText.getText().toString();
            String strDate = dateText.getText().toString();
            String strDateTime = strDate + " " + strTime;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
            Date datetime = Calendar.getInstance().getTime();
            try {
                datetime = dateFormat.parse(strDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentDate = Calendar.getInstance().getTime();
            if (datetime.compareTo(currentDate) > 0) {
                timeLayout.setError("Time cannot be in the future");
            }
            else {
                timeLayout.setError(null);
            }
        }

        if (nameText.getText().toString().isEmpty() || nameLayout.getError() != null
                || scoreText.getText().toString().isEmpty() || scoreLayout.getError() != null
                || dateText.getText().toString().isEmpty() || dateText.getText().toString().length() !=10 || dateLayout.getError() != null
                || timeText.getText().toString().isEmpty() || timeText.getText().toString().length() !=5 || timeLayout.getError() != null) {
            saveButton.setEnabled(false);
        }
        else {
            saveButton.setEnabled(true);
        }
    }
}
