package com.example.livecricketapp.model;

import java.io.Serializable;

public class TournamentHostInfo implements Serializable {

    private String hostName;
    private String upiId;
    private String phoneNumber;
    private String address;
    private String TournamentId;

    public TournamentHostInfo ()
    {

    }

    public TournamentHostInfo(String hostName, String upiId, String phoneNumber, String address, String tournamentId) {
        this.hostName = hostName;
        this.upiId = upiId;
        this.phoneNumber = phoneNumber;
        this.address = address;
        TournamentId = tournamentId;
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

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
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
