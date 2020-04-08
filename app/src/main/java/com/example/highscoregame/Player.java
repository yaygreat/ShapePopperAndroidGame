/******************************************************************************
 * Class that represent each high score as a Player object: name, score, datetime
 ******************************************************************************/

package com.example.highscoregame;

import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable {

    private String name;
    private int score;
    private Date datetime;

    public Player(){}

    public Player(String name, int score, Date datetime) {
        this.name = name;
        this.score = score;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
