/******************************************************************************
 * TextWatcher to only allow valid dates in the date field: MM-DD-YYY
 * Uses Gregorian Calendar
 ******************************************************************************/

package com.example.highscoregame;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class dateTextWatcher implements TextWatcher {

    private Button saveButton;
    private TextInputLayout nameLayout;
    private TextInputEditText nameText;
    private TextInputLayout scoreLayout;
    private TextInputEditText scoreText;
    private TextInputLayout dateLayout;
    private TextInputEditText dateText;
    private TextInputLayout timeLayout;
    private TextInputEditText timeText;

    public dateTextWatcher(Button saveButton,
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    // Only accepts a valid month 1-12, valid day per given month and leap year, and valid year
    // Any date in the future is not accepted
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String enteredMonth = "00";
        String enteredDay = "00";
        String stringYear = "0000";
        int length = 0;
        boolean dateIsValid = false;

        String working = s.toString();
        length = working.length();

        //Month
        if (length==2 && before ==0) {
            enteredMonth = working.substring(0,2);
            if (Integer.parseInt(enteredMonth) < 1 || Integer.parseInt(enteredMonth)>12) {
                dateIsValid = false;
                dateText.setText("");
                dateLayout.setError("Month must be within range: 1-12");
            }
            else {
                working+="-";
                dateText.setText(working);
                dateText.setSelection(working.length());
            }
        }

        //Day
        else if (length==5 && before ==0) {
            enteredMonth = working.substring(0,2);
            enteredDay = working.substring(3,5);
            if (Integer.parseInt(enteredDay) < 1 || Integer.parseInt(enteredDay) > 31) {
                dateIsValid = false;
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 31");
            }
            if (enteredMonth.equals("02") && (Integer.parseInt(enteredDay) < 1 || Integer.parseInt(enteredDay) > 29)) {
                dateIsValid = false;
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 29 for February");
                Log.d("sub", working.substring(0,3));
            }
            if (enteredDay.equals("31") &&
                    (enteredMonth.equals("04") || enteredMonth .equals("06") || enteredMonth.equals("09") || enteredMonth.equals("11"))) {
                dateIsValid = false; // only 1,3,5,7,8,10,12 has 31 days
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 30 for this month");
            }
            else if (length != 3){
                working+="-";
                dateText.setText(working);
                dateText.setSelection(working.length());
            }
        }

        //Year
        else if (working.length()==10 && before ==0) {
            enteredMonth = working.substring(0,2);
            enteredDay = working.substring(3,5);
            stringYear = working.substring(6,10);
            int intYear = Integer.parseInt(stringYear);
            //    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            String date = working;
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date enteredDate = null;
            try {
                enteredDate = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentDate = Calendar.getInstance().getTime();

            if (enteredDate.compareTo(currentDate) > 0) {
                dateIsValid = false; // date is in the future
                dateText.setText(working.substring(0,10));
                dateText.setSelection(working.substring(0,10).length());
                dateLayout.setError("Date cannot be in the future.");
            }
            else if (intYear % 4 != 0 && enteredMonth.equals("02") && enteredDay.equals("29")) {
                dateIsValid = false; //this is not a leap year, day < 29
                dateText.setText(working.substring(0,10));
                dateText.setSelection(working.substring(0,10).length());
                dateLayout.setError("This is not a leap year, day must be within range: 1-28");
            }
            else {
                dateIsValid = true;
                dateText.setText(working);
                dateText.setSelection(working.length());
                length = working.length();
            }
        }
        else if (working.length()==11 && before ==0) {
            enteredMonth = working.substring(0,2);
            enteredDay = working.substring(3,5);
            stringYear = working.substring(6,10);
            int intYear = Integer.parseInt(stringYear);
            //    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            String date = working;
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date enteredDate = null;
            try {
                enteredDate = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentDate = Calendar.getInstance().getTime();

            if (enteredDate.compareTo(currentDate) > 0) {
                dateIsValid = false; // date is in the future
                dateText.setText(working.substring(0,10));
                dateText.setSelection(working.substring(0,10).length());
                dateLayout.setError("Date cannot be in the future.");
            }
            else if (intYear % 4 != 0 && enteredMonth.equals("02") && enteredDay.equals("29")) {
                dateIsValid = false; //this is not a leap year, day < 29
                dateText.setText(working.substring(0,10));
                dateText.setSelection(working.substring(0,10).length());
                dateLayout.setError("This is not a leap year, day must be within range: 1-28");
            }
        }

        //check for changes after all numbers have been inputed
        else if (working.length()>=10 && before ==0 && !dateIsValid) {
            enteredMonth = working.substring(0,2);
            enteredDay = working.substring(3,5);
            stringYear = working.substring(7,10);
            int intYear = Integer.parseInt(stringYear);

            String date = working;
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            Date enteredDate = null;
            try {
                enteredDate = dateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currentDate = Calendar.getInstance().getTime();

            //MONTH
            if (Integer.parseInt(enteredMonth) < 1 || Integer.parseInt(enteredMonth)>12) {
                dateIsValid = false;
                dateText.setText("");
                dateLayout.setError("Month must be within range: 1-12");
            }
            //DAY
            else if (Integer.parseInt(enteredDay) < 1 || Integer.parseInt(enteredDay) > 31) {
                dateIsValid = false;
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 31");
            }
            else if (enteredMonth.equals("02") && (Integer.parseInt(enteredDay) < 1 || Integer.parseInt(enteredDay) > 29)) {
                dateIsValid = false;
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 29 for February");
                Log.d("sub", working.substring(0,3));
            }
            else if (enteredDay.equals("31") &&
                    (enteredMonth.equals("04") || enteredMonth .equals("06") || enteredMonth.equals("09") || enteredMonth.equals("11"))) {
                dateIsValid = false; // only 1,3,5,7,8,10,12 has 31 days
                dateText.setText(working.substring(0,3));
                dateText.setSelection(working.substring(0,3).length());
                length = 3;
                dateLayout.setError("Day cannot be greater than 30 for this month");
            }
            //YEAR
            else if (enteredDate.compareTo(currentDate) > 0) {
                dateIsValid = false; // date is in the future
                dateLayout.setError("Date cannot be in the future.");
            }
            else if (intYear % 4 != 0 && enteredMonth.equals("02") && enteredDay.equals("29")) {
                dateIsValid = false; //this is not a leap year, day < 29
                dateLayout.setError("This is not a leap year, day must be within range: 1-28");
            }
        }
        else {
            dateIsValid = true;
            dateLayout.setError(null);
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
