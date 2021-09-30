package com.example.livecricketapp.model;

import java.io.Serializable;

public class Subscription implements Serializable {

    private String userId;
    private String tournamentId = "";
    private Boolean matchSubscription = false ;
    private Boolean tourSubscription = false;
    private String matchId = "";
    private String transactionId ;
    private String validFrom;
    private String validTill;
    private int money;




}
