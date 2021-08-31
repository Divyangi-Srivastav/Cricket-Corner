package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.List;

public class TournamentInfo implements Serializable {

    private String tournamentId;
    private String tournamentName;
    private int number_of_teams;
    private List<String> teamNames;
    private float fees;
    private int no_of_matches_day;
    private String start_date;
    private String end_date;
    private String match_start_time;
    private String match_end_time;

    public TournamentInfo ()
    {

    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getNumber_of_teams() {
        return number_of_teams;
    }

    public void setNumber_of_teams(int number_of_teams) {
        this.number_of_teams = number_of_teams;
    }

    public List<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(List<String> teamNames) {
        this.teamNames = teamNames;
    }

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public int getNo_of_matches_day() {
        return no_of_matches_day;
    }

    public void setNo_of_matches_day(int no_of_matches_day) {
        this.no_of_matches_day = no_of_matches_day;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMatch_start_time() {
        return match_start_time;
    }

    public void setMatch_start_time(String match_start_time) {
        this.match_start_time = match_start_time;
    }

    public String getMatch_end_time() {
        return match_end_time;
    }

    public void setMatch_end_time(String match_end_time) {
        this.match_end_time = match_end_time;
    }
}
