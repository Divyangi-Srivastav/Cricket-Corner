package com.example.livecricketapp.model;

import java.io.Serializable;

public class SingleMatchInfo implements Serializable {

    private String matchNo;
    private String Date;
    private String Time;
    private TeamScoreCard team1Score;
    private TeamScoreCard team2Score;
    private String winningTeam = "";
    private String matchResult = "";
    private int matchStatus = 0 ;

    // 0 = not occured yet
    // 1 = ongoing
    // 2 = completed

    public SingleMatchInfo ()
    {

    }

    public int getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getWinningTeam() {
        return winningTeam;
    }

    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public TeamScoreCard getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(TeamScoreCard team1Score) {
        this.team1Score = team1Score;
    }

    public TeamScoreCard getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(TeamScoreCard team2Score) {
        this.team2Score = team2Score;
    }
}
