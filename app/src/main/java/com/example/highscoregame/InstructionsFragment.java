/******************************************************************************
 * This fragment gives the player instructions on how to play the game.
 *
 * A random shape type and color is chosen.
 * This is the shape that the player will have to touch.
 *
 * If Play button is pressed, it will navigate to the Game Fragment to start the game.
 ******************************************************************************/

package com.example.highscoregame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Random;

public class InstructionsFragment extends Fragment {

    String color = "color";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);
        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Shape shape = touchShape();

        TextView instructionsText = (TextView) getView().findViewById(R.id.instructionsText);
        instructionsText.setText("Touch 10 correct shapes to win!" +
                "\nNo penalty when touching incorrect shapes." +
                "\nScore is based on your touch reaction time from when a correct shape appears." +
                "\n\nTouch the " + color + " " + shape.getType() + "s to win!");

        Shape finalShape = shape;
        view.findViewById(R.id.play_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putSerializable("desiredShape", finalShape);
                NavHostFragment.findNavController(InstructionsFragment.this)
                        .navigate(R.id.actionPlay, args);
            }
        });
    }

    // creates a random target shape that the player will need to touch in the game
    public Shape touchShape() {
        Random rand = new Random();

        String[] typeArr = {"circle","square"};
        int randTypeIndex = rand.nextInt(typeArr.length);
        String randType = typeArr[randTypeIndex];

        int randColor = rand.nextInt(7);
        switch (randColor) {
            case 0:
                this.color = "red";
                randColor = Color.RED;
                break;
            case 1: //ORANGE
                this.color = "orange";
                randColor = 0xFFFFA500;
                break;
            case 2:
                this.color = "yellow";
                randColor = Color.YELLOW;
                break;
            case 3:
                this.color = "green";
                randColor = Color.GREEN;
                break;
            case 4:
                this.color = "blue";
                randColor = Color.BLUE;
                break;
            case 5: //PURPLE
                this.color = "purple";
                randColor = 0XFF800080;
                break;
            case 6:
                this.color = "white";
                randColor = Color.WHITE;
                break;
        }

        Shape shape = new Shape();
        shape.setType(randType);
        shape.setColor(randColor);
        return shape;
    }

}
