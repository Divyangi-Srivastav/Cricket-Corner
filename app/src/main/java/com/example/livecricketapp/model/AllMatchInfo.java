package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.List;

public class AllMatchInfo implements Serializable {

    private String tournamentId;
    private List<SingleMatchInfo> matchInfos;

    public AllMatchInfo ()
    {

    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public List<SingleMatchInfo> getMatchInfos() {
        return matchInfos;
    }

    public void setMatchInfos(List<SingleMatchInfo> matchInfos) {
        this.matchInfos = matchInfos;
    }


}
