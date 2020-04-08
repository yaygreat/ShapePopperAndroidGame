/******************************************************************************
 * This fragment shows a list of the top 12 scores.
 *
 * They are read from the Scoresfile.txt and placed into an arraylist of Player objects.
 * The scores are listed in order from lowest to highest.
 * All scores must be greater than 0.
 * If there is a tie, the score with the most recent time will take precedence.
 *
 * If the Play Again button is pressed, it will navigate back to the Instructions Fragment
 * within the Game Activity.
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class FirstFragment extends Fragment {

    Context mainContext;
    ArrayList<Player> playersList = new ArrayList<Player>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        ArrayList<HashMap<String, String>> header = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "Name");
        map.put("score", "Score");
        map.put("time", "Time");
        header.add(map);

        ListView playerListViewHeader = (ListView) rootView.findViewById(R.id.listViewHeader);
        SimpleAdapter headerAdapter = new SimpleAdapter(getActivity(), header, R.layout.listview_header,
                new String[] {"name", "score", "time"},
                new int[] {R.id.nameTextView, R.id.scoreTextView, R.id.timeTextView});
        playerListViewHeader.setAdapter(headerAdapter);

        ListView playerListView = (ListView) rootView.findViewById(R.id.listView);

        FileIO file = new FileIO();
        playersList = file.readFile(mainContext);
        Collections.sort(playersList, Comparator.comparingInt(Player::getScore).reversed().thenComparing(Player::getDatetime).reversed());

        PlayerListAdapter listAdapter = new PlayerListAdapter(getActivity(), R.layout.listview_adapter, playersList);
        playerListView.setAdapter(listAdapter);
        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
