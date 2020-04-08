/******************************************************************************
 * TextWatcher to ensure that score field is greater than 0
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

public class scoreTextWatcher implements TextWatcher {

    private Button saveButton;
    private TextInputLayout nameLayout;
    private TextInputEditText nameText;
    private TextInputLayout scoreLayout;
    private TextInputEditText scoreText;
    private TextInputLayout dateLayout;
    private TextInputEditText dateText;
    private TextInputLayout timeLayout;
    private TextInputEditText timeText;

    public scoreTextWatcher(Button saveButton,
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

    //ONly accepts scores greater than 0
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String enteredScore = "";
        int length = 0;

        String working = s.toString();
        length = working.length();

        //Month
        if (length==1 && before ==0) {
            enteredScore = working;
            if (Integer.parseInt(enteredScore) == 0) {
                scoreText.setText("");
                scoreLayout.setError("Score must be greater than 0");
            }
            else {
                scoreText.setText(working);
                scoreText.setSelection(working.length());
                scoreLayout.setError(null);
            }
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
