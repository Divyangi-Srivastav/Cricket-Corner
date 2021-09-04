package com.example.livecricketapp.model;

import java.io.Serializable;

public class DashboardTournamentInfo implements Serializable {

    private String tournamentId = "";
    private String hostName = "";
    private String tournamentName = "";

    public DashboardTournamentInfo ()
    {

    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}
