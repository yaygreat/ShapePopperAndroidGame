/******************************************************************************
 * This activity tracks the top 12 high scores of a game.
 * It operates in two modes:
 * First, you can view up to 12 high scores in the High Scores screen.
 * Second, you can add a new score through the Add screen.
 *
 * The scores are firstly listed from high to low scores and
 * secondly listed from most recent time to least recent time.
 *
 * Up to 12 scores can be added to the list.
 * If a new score to be added is greater than the minimum score in the list,
 * it adds it to the list and deletes the min score to maintain the list length at 12.
 * If a new score to be added is the same as minimum score in the list,
 * it adds the new score only if its time is more recent than the min score
 * and deletes the min score to maintain the list length at 12.
 *
 * This project will create a file called Scoresfile.txt in your directory.
 * e.g.: /data/user/0/com.example.highscoregame/files/Scoresfile.txt
 * This is where your 12 high scores will be saved using tab delimeters:
 * Name \t Score \t DateTime
 *
 * Written by Nancy Dominguez (nsd093020) at The University of Texas at Dallas.
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScoresActivity extends AppCompatActivity{
    Integer score;
    Integer missed;

    public Integer getScore(){
        return score;
    }

    public Integer getMissed() {
        return missed;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        // get score and missed shape count from Game Activity
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 1);
        missed = intent.getIntExtra("missed", 0);
        Log.d("score", Integer.toString(score));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // date/time pickers not used in this project
//    public void showDatePickerDialog(View v) {
//        DialogFragment newFragment = new DateFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//    }
//
//    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimeFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
//    }

}
