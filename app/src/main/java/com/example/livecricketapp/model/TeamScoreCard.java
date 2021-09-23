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
