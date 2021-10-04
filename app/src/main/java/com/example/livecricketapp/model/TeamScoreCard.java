package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.List;

public class TeamScoreCard implements Serializable {

    private String teamName;
    private int TeamRuns;
    private int TeamWickets;
    private List<PlayerScoreCard> cards;

    public  TeamScoreCard ()
    {

    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamRuns() {
        return TeamRuns;
    }

    public void setTeamRuns(int teamRuns) {
        TeamRuns = teamRuns;
    }

    public int getTeamWickets() {
        return TeamWickets;
    }

    public void setTeamWickets(int teamWickets) {
        TeamWickets = teamWickets;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<PlayerScoreCard> getCards() {
        return cards;
    }

    public void setCards(List<PlayerScoreCard> cards) {
        this.cards = cards;
    }
}
