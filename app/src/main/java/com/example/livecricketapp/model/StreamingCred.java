package com.example.livecricketapp.model;

import java.io.Serializable;

public class StreamingCred implements Serializable {

    private String name ;
    private String id ;

    public StreamingCred ()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
