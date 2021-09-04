package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.List;

public class SingleTeamInfo implements Serializable {

    private String captainName;
    private String upiId;
    private List<String> playerNames;
    private String teamName;

    public SingleTeamInfo()
    {

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        this.playerNames = playerNames;
    }
}
