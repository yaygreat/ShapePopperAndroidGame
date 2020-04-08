/******************************************************************************
 * This custom view displays the shapes on the screen and appears in the Game Fragment.
 *
 * Once this view is created, it is initialized with 12 random shapes (circles or squares)
 * of random sizes and colors are placed randomly on the screen.
 *
 * This view is responsible for creating new shapes every time that a shape disappears
 * from the screen via player touch or lifespan expiration.
 ******************************************************************************/

package com.example.highscoregame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class CanvasView extends View {

    Random rand = new Random();
    private Paint drawPaint;
    private Canvas canvas;
    int screenWidth;
    int screenHeight;
    ArrayList<Shape> shapeList = new ArrayList<>();
    Shape desiredShape;
    int score = 0;
    int missed = 0;

    Handler h = new Handler();

     // Each shape lifespan is a unique thread that executes at the end of every shape lifespan.
     // If the shape is still on the screen, it will remove it and its lifespan to the score.
     // It causes the screen to be redrawn.
    public class MyRunnable implements Runnable {
        private Shape s;
        public MyRunnable(Shape _shape) {
            this.s = _shape;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public void run() {
            if (shapeList.contains(s)) {
                if (s.getType() == desiredShape.getType() && s.getColor() == desiredShape.getColor()) {
                    score += s.getLifeSpan();
                    Log.d("score", Integer.toString(score));
                    missed++;
                }
                shapeList.remove(s);
            }
            updateCanvasView();
        }
    }

    public void setDesiredShape(Shape desiredShape) {
        this.desiredShape = desiredShape;
        Log.d("setDesiredShape", desiredShape.getType());
    }

    public void setShapeList(ArrayList<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public ArrayList<Shape> getShapeList() {
        return shapeList;
    }

    public int getScore() {
        return score;
    }

    public int getMissed() {
        return missed;
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public CanvasView(Context context, @Nullable AttributeSet attrs, int width, int height) {
        super(context, attrs);
        this.screenWidth = width;
        this.screenHeight = height;
        initShapeList();
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStrokeWidth(4);
        drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        Paint paint = new Paint();
        paint.setColor( Color.RED );
        paint.setStrokeWidth( 6f );
        paint.setStyle( Paint.Style.STROKE );
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        Log.d("onDraw", "onDraw called");
        Log.d("init", String.valueOf(shapeList.size()));
        while (shapeList.size() < 12) {
            shapeList.add(createShape());
        }
        Collections.sort(shapeList, Comparator.comparingInt(Shape::getLifeSpan));
        for (Shape shape:shapeList) {
            if (shape instanceof Circle) {
                drawCircle(shape);
            }
            else {
                drawRectangle(shape);
            }
        }
    }

    public void drawCircle(Shape shape) {
        drawPaint.setColor(shape.getColor());
        canvas.drawCircle(shape.getX(), shape.getY(), shape.getSize(), drawPaint);
    }

    public void drawRectangle(Shape shape) {
        drawPaint.setColor(shape.getColor());
        Rect rectangle = createRect(shape);
        canvas.drawRect(rectangle, drawPaint);
    }

    public Rect createRect(Shape shape) {
        Rect rectangle = new Rect(
                (int) (shape.getX() - (.5 * shape.getSize())),
                (int) (shape.getY() - (.5 * shape.getSize())),
                (int) (shape.getX() + (.5 * shape.getSize())),
                (int) (shape.getY() + (.5 * shape.getSize())));
        return rectangle;
    }

    // creates random shapes that do not overlap with the current shapes on screen
    // adds it the shapeList
    @RequiresApi(api = Build.VERSION_CODES.P)
    public Shape createShape() {
        String[] typeArr = {"circle","square"};
        int randTypeIndex = rand.nextInt(typeArr.length);
        String randType = typeArr[randTypeIndex];

        // set X & Y coordinates and shape size
        // make sure that shapes are within the screen size
        int minSize = 65;
        int maxSize = 130;
        int randX = maxSize + rand.nextInt(screenWidth - 2 * maxSize);
        int randY = maxSize + rand.nextInt(screenHeight - 2 * maxSize - 300);
        float randSize = minSize + rand.nextFloat() * (maxSize - minSize);

        //makes sure that the shapes don't overlap
        boolean conflict = true;
        if(!shapeList.isEmpty()){
            while (conflict == true) {
                randX = maxSize + rand.nextInt(screenWidth - 2 * maxSize);
                randY = maxSize + rand.nextInt(screenHeight - 2 * maxSize - 300);
                randSize = minSize + rand.nextFloat() * (maxSize - minSize);

                for (Shape shape:shapeList) {
                    // assumes squares are circles with a radius length = square side length
                    // gives a good cushion between shapes
                    if (Math.hypot(Math.abs(shape.getX() - randX), Math.abs(shape.getY() - randY)) <= 1.1*(shape.getSize() + randSize)) {
                        conflict = true;
                        break;
                    }
                    else conflict = false;
                }
            }
        }

        // set shape color
        int randColor = rand.nextInt(7);
        switch (randColor) {
            case 0:
                randColor = Color.RED;
                break;
            case 1: //ORANGE
                randColor = 0xFFFFA500;
                break;
            case 2:
                randColor = Color.YELLOW;
                break;
            case 3:
                randColor = Color.GREEN;
                break;
            case 4:
                randColor = Color.BLUE;
                break;
            case 5: //PURPLE
                randColor = 0XFF800080;
                break;
            case 6:
                randColor = Color.WHITE;
                break;
        }

        // sets a random lifespan between 3 and 7 seconds
        int randLifeSpan = 3000 + rand.nextInt(7000 - 3000);

        // sets the start tie when the shape was created
        Instant startTime = Instant.now();

        // create a new shape
        Shape shape;
        if (randType == "circle") {
            shape = new Circle(randType, randX, randY, randSize, randColor, randLifeSpan, startTime);
        }
        else {
            shape = new Square(randType, randX, randY, randSize, randColor, randLifeSpan, startTime);
        }

        // will wait the lifespan of the shape before removing it from the list
        Runnable r = new MyRunnable(shape);
        h.postDelayed(r, shape.getLifeSpan());

        return shape;
    }

    // initializes the starting shapeList of initial shapes that will show on the screen once the game starts
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void initShapeList(){
        for (int i = 0; i < 12; i++) {
            shapeList.add(createShape());
        }
    }

    public void updateCanvasView() {
        invalidate();
    }
}
