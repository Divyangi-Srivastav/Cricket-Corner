package com.example.livecricketapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllSubscriptions implements Serializable {

    private String userId;
    private List<SingleSubscription> list = new ArrayList<>();

    public AllSubscriptions ()
    {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<SingleSubscription> getList() {
        return list;
    }

    public void setList(List<SingleSubscription> list) {
        this.list = list;
    }
}
