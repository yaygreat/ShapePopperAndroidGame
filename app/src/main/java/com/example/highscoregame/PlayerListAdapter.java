/******************************************************************************
 * List Adapter to create each row in the ListView
 ******************************************************************************/

package com.example.highscoregame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlayerListAdapter extends ArrayAdapter<Player> {

    private Context myContext;
    private int myResource;

    public PlayerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Player> objects) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        String name = getItem(position).getName();
        int score = getItem(position).getScore();
        Date time  = getItem(position).getDatetime();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(myResource, parent, false);

        TextView nameTV = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView scoreTV = (TextView) convertView.findViewById(R.id.scoreTextView);
        TextView timeTV = (TextView) convertView.findViewById(R.id.timeTextView);

        nameTV.setText(name);
        scoreTV.setText(Integer.toString(score));
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
        String strTime = dateFormat.format(time);
        timeTV.setText(strTime);

        return convertView;
    }
}
