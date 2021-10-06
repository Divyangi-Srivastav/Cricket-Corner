package com.example.livecricketapp.model;

import java.io.Serializable;

public class AdBanner implements Serializable {

    private String adId;
    private String transactionId;
    private String userId;
    private String adImageUrl;
    private int adTime;
    private int amountPaid;
    private String adMessage;
    private int adStatus;
    private String requestDate;
    private String requestTime;

    // 0 - payment done
    // 1 - showing

    public AdBanner ()
    {

    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(String adImageUrl) {
        this.adImageUrl = adImageUrl;
    }

    public int getAdTime() {
        return adTime;
    }

    public void setAdTime(int adTime) {
        this.adTime = adTime;
    }

    public int getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getAdMessage() {
        return adMessage;
    }

    public void setAdMessage(String adMessage) {
        this.adMessage = adMessage;
    }

    public int getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(int adStatus) {
        this.adStatus = adStatus;
    }
}
