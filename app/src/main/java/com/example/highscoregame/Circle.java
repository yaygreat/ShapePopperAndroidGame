/******************************************************************************
 * Class that extends the class Shape
 ******************************************************************************/

package com.example.highscoregame;

import java.time.Instant;

public class Circle extends Shape {
    private float radius;

    public Circle() {
    }

    public Circle(String type, int x, int y, float size, int color, int lifeSpan, Instant startTime) {
        super(type, x, y, size, color, lifeSpan, startTime);
        setRadius(size);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
