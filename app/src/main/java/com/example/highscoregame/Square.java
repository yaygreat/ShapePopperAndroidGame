/******************************************************************************
 * Class that extends Shape
 ******************************************************************************/

package com.example.highscoregame;

import java.time.Instant;

public class Square extends Shape {
    private float side;

    public Square() {
    }

    public Square(String type, int x, int y, float size, int color, int lifeSpan, Instant startTime) {
        super(type, x, y, size, color, lifeSpan, startTime);
        setSide(size);
    }

    public float getSide() {
        return side;
    }

    public void setSide(float side) {
        this.side = (float) .5 * side;
    }
}
