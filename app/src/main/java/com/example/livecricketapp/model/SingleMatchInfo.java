package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleMatchInfo implements Serializable {

    private String matchNo;
    private String Date;
    private String Time;
    private TeamScoreCard team1Score;
    private TeamScoreCard team2Score;
    private String winningTeam = "";
    private String matchResult = "";
    private int matchStatus = 0 ;
    private String batsman1 ;
    private String batsman2 ;
    private String bowler ;
    private String battingTeam ;
    private List<Integer> score = new ArrayList<>();

    // 0 = not occured yet
    // 1 = ongoing
    // 2 = completed

    // in score 7 for out and -1 to be not included

    public SingleMatchInfo ()
    {
        for ( int i=0 ; i < 6 ; i++ )
        {
            score.add(-1);
        }
    }

    public String getBatsman1() {
        return batsman1;
    }

    public void setBatsman1(String batsman1) {
        this.batsman1 = batsman1;
    }

    public String getBatsman2() {
        return batsman2;
    }

    public void setBatsman2(String batsman2) {
        this.batsman2 = batsman2;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public List<Integer> getScore() {
        return score;
    }

    public void setScore(List<Integer> score) {
        this.score = score;
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

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }
}
