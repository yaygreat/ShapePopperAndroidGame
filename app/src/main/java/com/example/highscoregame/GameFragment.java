/******************************************************************************
 * This fragment allows the player to play the game.
 *
 * Shapes (circles or squares) of random sizes and colors are placed randomly on the screen.
 * The shape and color that the player needs to touch is shown at the top
 * along with a Restart button in case the player wishes to start over with a new shape.
 *
 * Each shape has a screen lifespan in milliseconds. Once its lifespan runs out,
 * it will disappear from the screen and a new random shape will replace it.
 * The score is the reaction time that it takes the player to touch each shape
 * so lower scores are better.
 * Whenever a shape is touched, whether it's correct or not, the shape will disappear
 * from the screen and a new one will take its place.
 * If the player fails to touch a correct shape before it disappears from the screen,
 * it's lifespan time is added to the score.
 * The game ends when the player has touched ten correct shapes.
 *
 * Once the game ends, it will navigate to the Second Fragment in the Scores Activity
 * so that the player can add their high score or play again.
 * If the Restart button is pressed instead, it will navigate to the Instructions Fragment
 * to start the game over.
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class GameFragment extends Fragment {

    Context mainContext;
    OnPopListener callback;
    private CanvasView canvas;
    int popped = 0;
    int score = 0;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainContext = context;
        try {
            callback = (OnPopListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement callback");
        }
    }

    public void setOnPopListener(OnPopListener callback) {
        this.callback = callback;
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnPopListener {
        public void onLastPop(int score, int missed);
    }

    public void endGame(int score, int missed) {
        // Send the event to the host activity
        callback.onLastPop(score, missed);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Shape desiredShape = (Shape) getArguments().getSerializable("desiredShape");

        // get screen size
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Log.d("Width", Integer.toString(width));
        Log.d("Height",  Integer.toString(height));

        // set the header
        TextView header = (TextView) getView().findViewById(R.id.header);
        String strColor = "color";
        switch (desiredShape.getColor()) {
            case Color.RED:
                strColor = "red";
                break;
            case 0xFFFFA500: //ORANGE
                strColor = "orange";
                break;
            case Color.YELLOW:
                strColor = "yellow";
                break;
            case Color.GREEN:
                strColor = "green";
                break;
            case Color.BLUE:
                strColor = "blue";
                break;
            case 0XFF800080: //PURPLE
                strColor = "purple";
                break;
            case Color.WHITE:
                strColor = "white";
                break;
        }
        header.setText("Touch the " + strColor + " " + desiredShape.getType() + "s!");

        // create the drawing canvas
        canvas = new CanvasView(getContext(), null, width, height);
        canvas.setDesiredShape(desiredShape);
        canvas.setBackgroundColor(Color.BLACK);
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.canvasLinearLayout);
        linearLayout.addView(canvas);

        Shape finalDesiredShape = desiredShape;
        canvas.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    popShape(event, finalDesiredShape);
                    return true;
                }
                return false;
            }
        });

        view.findViewById(R.id.restart_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(GameFragment.this)
                        .navigate(R.id.actionRestartGame);
            }
        });
    }

    // removes all touched shapes and adds the reaction time or correct shapes
    // If 10 shapes are touched, it ends the game
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void popShape(MotionEvent event, Shape finalDesiredShape) {
        ArrayList<Shape> shapeList = canvas.getShapeList();
        float touchX = event.getX();
        float touchY = event.getY();
        Shape delShape = null;
        Shape clearShape = null;

        if(!shapeList.isEmpty()){
            for (Shape shape:shapeList) {
                double size = shape instanceof Circle ? shape.getSize() : .5 * shape.getSize();
                // add to score if correct shape is touched, otherwise indicate that the wrong shape was touched
                if ( (touchX > shape.getX()-size && touchX < shape.getX()+size)
                        && (touchY > shape.getY()-size && touchY < shape.getY()+size) ) {
                    if (finalDesiredShape.getType() == shape.getType()
                            && finalDesiredShape.getColor() == shape.getColor() ) {
                        //calculate reaction time
                        Duration elapsedTime = Duration.between(shape.getStartTime(), Instant.now());
                        score += elapsedTime.toMillis();
                        delShape = shape;
                    }
                    else {
                        clearShape = shape;
                    }
                }
            }
            // wrong shape touched, delete shape and make another one
            if (clearShape != null) {
                shapeList.remove(clearShape);
                canvas.setShapeList(shapeList);
                clearShape = null;
                canvas.updateCanvasView();
            }
            // correct shape touched, delete shape and make another one
            if (delShape != null) {
                shapeList.remove(delShape);
                canvas.setShapeList(shapeList);
                popped++;
                // end game if 10 correct shapes are touched
                if (popped == 10) {
                    Log.d("preScore", Integer.toString(score));
                    score += canvas.getScore();
                    Log.d("canvasScore", Integer.toString(canvas.getScore()));
                    Log.d("postScore", Integer.toString(score));
                    endGame(score, canvas.getMissed());
                }
                delShape = null;
                canvas.updateCanvasView();
            }
        }
    }
}
