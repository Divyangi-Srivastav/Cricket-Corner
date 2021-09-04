package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.List;

public class AllTeamInfo implements Serializable {

    private List<SingleTeamInfo> teamInfos;
    private String TournamentId;

    public AllTeamInfo ()
    {

    }

    public List<SingleTeamInfo> getTeamInfos() {
        return teamInfos;
    }

    public void setTeamInfos(List<SingleTeamInfo> teamInfos) {
        this.teamInfos = teamInfos;
    }

    public String getTournamentId() {
        return TournamentId;
    }

    public void setTournamentId(String tournamentId) {
        TournamentId = tournamentId;
    }
}
