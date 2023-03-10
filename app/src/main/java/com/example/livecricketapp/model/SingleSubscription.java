package com.example.livecricketapp.model;

import java.io.Serializable;

public class SingleSubscription implements Serializable {

    private String userId;
    private String tournamentId = "";
    private Boolean matchSubscription = false ;
    private Boolean tourSubscription = false;
    private String matchId = "";
    private String transactionId ;
    private String validFrom;
    private String validTill;
    private int money;
    private String tournament_name;
    private String date_of_purchase;

    public SingleSubscription()
    {

    }

    public String getTournament_name() {
        return tournament_name;
    }

    public void setTournament_name(String tournament_name) {
        this.tournament_name = tournament_name;
    }

    public String getDate_of_purchase() {
        return date_of_purchase;
    }

    public void setDate_of_purchase(String date_of_purchase) {
        this.date_of_purchase = date_of_purchase;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Boolean getMatchSubscription() {
        return matchSubscription;
    }

    public void setMatchSubscription(Boolean matchSubscription) {
        this.matchSubscription = matchSubscription;
    }

    public Boolean getTourSubscription() {
        return tourSubscription;
    }

    public void setTourSubscription(Boolean tourSubscription) {
        this.tourSubscription = tourSubscription;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
