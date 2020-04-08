/******************************************************************************
 * Reads and writes high scores to Scoresfile.txt
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileIO {

    private File dir;

    // Opening a file for output in Java under Android.
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean writeFile(Context context, ArrayList<Player> playersList){
        File fScores;
        PrintWriter scores = null;
        try
        {
            dir = new File(context.getFilesDir(), "Scoresfile.txt");
            Log.d("dir", dir.toString());

            FileWriter fw = new FileWriter(dir);
            playersList.forEach((p) -> write(fw, p));
            fw.close();
            return true;
        }
        catch (Exception ex)
        {
            // Handle the error.
            return false;
        }
    }

    public void write(FileWriter fw, Player p) {
        try{
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");

            String line = p.getName() + "\t" + p.getScore() + "\t" + dateFormat.format(p.getDatetime()) + "\n";
//            Log.d("line", line);
            fw.write(line);
        } catch (Exception ex) {
            //Handle the error
            Log.d("errWrite", "There was an error trying to write to the file.");
        }

    }

    public ArrayList<Player> readFile(Context context){
            // Opening a file for input under Android.
        ArrayList<Player> playersLists = new ArrayList<>();
        try
        {
            dir = new File(context.getFilesDir(), "Scoresfile.txt");
            if(!dir.exists()){
                dir.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(dir));
            String line;

            while ((line = br.readLine()) != null) {
                Player player = new Player();
                String[] column = line.split("\\t");
                player.setName(column[0]);
                player.setScore(Integer.parseInt(column[1]));
                DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm");
                Date time = null;
                try {
                    time = dateFormat.parse(column[2]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                player.setDatetime(time);
                playersLists.add(player);
            }
            br.close();
        }
        catch (Exception ex)
        {
            // Handle the error
            Log.d("errRead", "There was an error trying to read the file.");
        }
        return playersLists;
    }

}
