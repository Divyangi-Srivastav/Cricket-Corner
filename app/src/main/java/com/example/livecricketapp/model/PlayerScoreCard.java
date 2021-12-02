package com.example.livecricketapp.model;

import java.io.Serializable;

public class PlayerScoreCard implements Serializable {

    private String playerName;
    private int runs = 0;
    private int wickets = 0;
    private int balls = 0;

    public PlayerScoreCard ()
    {

    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }
}
