package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comments implements Serializable {

    private String tournamentId = "";
    private String matchNo  = "";
    private List<String> commentsList = new ArrayList<>();

    public Comments()
    {

    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(String matchNo) {
        this.matchNo = matchNo;
    }

    public List<String> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<String> commentsList) {
        this.commentsList = commentsList;
    }
}
