/******************************************************************************
 * Class that represent each shape as a Shape object:
 * type, x & y, size, color, lifespan, and startTime
 *
 * type: circle or square
 * x & y: screen coordinates
 * size: circle radius length or square side length
 * lifespan: time shape will stay on screen in milliseconds
 * startTime: time when shape was created
 ******************************************************************************/

package com.example.highscoregame;

import java.io.Serializable;
import java.time.Instant;

public class Shape implements Serializable {
    private String type;
    private int x;
    private int y;
    private float size;
    private int color;
    private int lifeSpan;
    private Instant startTime;

    public Shape(){}

    public Shape(String type, int x,int y, float size, int color, int lifeSpan, Instant startTime) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.lifeSpan = lifeSpan;
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
}
