package com.example.livecricketapp.model;

import java.io.Serializable;

public class TournamentHostInfo implements Serializable {

    private String hostName;
    private String phoneNumber;
    private String address;
    private String TournamentId;

    public TournamentHostInfo ()
    {

    }

    public String getTournamentId() {
        return TournamentId;
    }

    public void setTournamentId(String tournamentId) {
        TournamentId = tournamentId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
