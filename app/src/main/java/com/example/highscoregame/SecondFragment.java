/******************************************************************************
 * This fragment shows the required fields to add a new score.
 *
 * All fields except the name are pre-populated from the results of the Game Activity.
 * If the player achieved a new high score, the player can type their name in and save
 * their score to the top 12 high scores.
 * The name field must have valid input before the Save button becomes enabled.
 * If the player did not achieve a new high score, the player will not be able to save
 * their score.
 *
 * The list can hold up to 12 scores.
 * If the score to be added is less than the last score, the score is added
 * and the last score is deleted.
 * If the score to be added is the same as the last score, the score is only added
 * if it has a more recent time than the last score and the last score is deleted, otherwise
 * the original list remains the same.
 *
 * If the Save button is presed, the modified list of high scores is then written
 * to the Scoresfile.txt and it navigates to the First Fragment to view the high scores.
 * If the High Scores button is pressed, the player's score is not saved
 * and it navigates to the First Fragment to view the high scores.
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SecondFragment extends Fragment {

    Context mainContext;
    ArrayList<Player> playersList;
    Player player;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainContext = context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        Button saveButton = (Button) rootView.findViewById(R.id.save_button);

        // get edit text boxes and layouts for each field
        TextInputLayout nameLayout = (TextInputLayout) rootView.findViewById(R.id.outlinedNameField);
        TextInputLayout scoreLayout = (TextInputLayout) rootView.findViewById(R.id.outlinedScoreField);
        TextInputLayout missedLayout = (TextInputLayout) rootView.findViewById(R.id.outlinedMissedBalloonsField);
        TextInputLayout dateLayout = (TextInputLayout) rootView.findViewById(R.id.outlinedDateField);
        TextInputLayout timeLayout = (TextInputLayout) rootView.findViewById(R.id.outlinedTimeField);

        TextInputEditText nameText = (TextInputEditText) rootView.findViewById(R.id.nameEditText);
        TextInputEditText scoreText = (TextInputEditText) rootView.findViewById(R.id.scoreEditText);
        TextInputEditText missedText = (TextInputEditText) rootView.findViewById(R.id.missedShapesEditText);
        TextInputEditText dateText = (TextInputEditText) rootView.findViewById(R.id.dateEditText);
        TextInputEditText timeText = (TextInputEditText) rootView.findViewById(R.id.timeEditText);

        // get score and missed shape count from Scores Activity
        // and set the time and date
        ScoresActivity scoresActivity = (ScoresActivity) getActivity();

        int score = scoresActivity.getScore();
        scoreText.setText(Integer.toString(score));
        scoreText.setEnabled(false);

        int missed = scoresActivity.getMissed();
        missedText.setText(Integer.toString((missed)));
        missedText.setEnabled(false);

        dateText.setText(getDate());
        dateText.setEnabled(false);

        timeText.setText(getTime());
        timeText.setEnabled(false);


        TextWatcher nameTextWatcher = new nameTextWatcher(saveButton, nameLayout, nameText, scoreLayout, scoreText, dateLayout, dateText, timeLayout, timeText);
        nameText.addTextChangedListener(nameTextWatcher);
        TextWatcher scoreTextWatcher = new scoreTextWatcher(saveButton, nameLayout, nameText, scoreLayout, scoreText, dateLayout, dateText, timeLayout, timeText);
        scoreText.addTextChangedListener(scoreTextWatcher);
        TextWatcher dateTextWatcher = new dateTextWatcher(saveButton, nameLayout, nameText, scoreLayout, scoreText, dateLayout, dateText, timeLayout, timeText);
        dateText.addTextChangedListener(dateTextWatcher);
        TextWatcher timeTextWatcher = new timeTextWatcher(saveButton, nameLayout, nameText, scoreLayout, scoreText, dateLayout, dateText, timeLayout, timeText);
        timeText.addTextChangedListener(timeTextWatcher);


        // listeners not used in this part of the project
//        dateText.setOnFocusChangeListener((view, hasFocus) -> {
//            if (hasFocus) {
//                dateText.setHint("MM-DD-YYYY");
//            } else {
//                dateText.setHint("");
//            }
//        });
//        timeText.setOnFocusChangeListener((view, hasFocus) -> {
//            if (hasFocus) {
//                timeText.setHint("hh:mm");
//            } else {
//                timeText.setHint("");
//            }
//        });

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Play Again button
        view.findViewById(R.id.startover_button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),GameActivity.class);
//                startActivity(intent);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.actionSave);
            }
        });

        // determine if high score should be added
        // disable Save button if not a high score
        boolean write = addPlayer();
        TextView highscoreTextView = (TextView) getView().findViewById(R.id.highscoreTextView);
        if (write) {
            highscoreTextView.setText("Congratulations! This is a new high score!");
        }
        else {
            highscoreTextView.setText("Sorry, this is not a new high score. Please try again!");
            TextInputEditText nameText = (TextInputEditText) getView().findViewById(R.id.nameEditText);
            nameText.setEnabled(false);
        }
        // Save the highscore to file and go to highscore list view
        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (write) {
                    TextInputEditText nameText = (TextInputEditText) getView().findViewById(R.id.nameEditText);
                    String name = nameText.getText().toString();
                    player.setName(name);
                    playersList.add(player);
                    Collections.sort(playersList, Comparator.comparingInt(Player::getScore).reversed().thenComparing(Player::getDatetime).reversed());

                    FileIO file = new FileIO();
                    file.writeFile(mainContext, playersList);
                }
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.actionSave);
            }
        });
    }

    public String getDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int mth = month + 1;
        String m = String.valueOf(mth);
        if (m.length() == 1) {
            m = "0" + m;
        }
        String d = String.valueOf(day);
        if (d.length() == 1) {
            d = "0" + d;
        }
        return m + "-" + d + "-" + year;
    }

    public String getTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String hr = String.valueOf(hour);
        if (hr.length() == 1) {
            hr = "0" + hr;
        }
        Log.d("min", String.valueOf(minute));
        String min = String.valueOf(minute);
        if (min.length() == 1) {
            min = "0" + min;
        }
        return hr + ":" + min;
    }


    // returns true if the score is a high score and will be written to the file
    // otherwise returns false
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addPlayer() {
        TextInputEditText nameText = (TextInputEditText) getView().findViewById(R.id.nameEditText);
        TextInputEditText scoreText = (TextInputEditText) getView().findViewById(R.id.scoreEditText);
        TextInputEditText dateText = (TextInputEditText) getView().findViewById(R.id.dateEditText);
        TextInputEditText timeText = (TextInputEditText) getView().findViewById(R.id.timeEditText);

        String name = nameText.getText().toString();
        int score = Integer.parseInt(scoreText.getText().toString());

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

        player = new Player(name, score , datetime);
        FileIO file = new FileIO();
        playersList = file.readFile(mainContext);
        Collections.sort(playersList, Comparator.comparingInt(Player::getScore).reversed().thenComparing(Player::getDatetime).reversed());

        boolean write = false;
        if(!playersList.isEmpty()){
            if (playersList.size() >= 12) { //list is full check min score
                Player minScorePlayer = playersList.get(playersList.size()-1);
                if(player.getScore() <= minScorePlayer.getScore() && player.getDatetime().compareTo(minScorePlayer.getDatetime()) > 0){
                    playersList.remove(playersList.size()-1);
                    write = true;
                }
            }
            else {
                write = true;
            }
        }
        else{
            write = true;
        }
        return write;
    }
}
