package org.baetz.christoph.models;

import java.util.Date;

public class CandidateHit {

    private int id;
    private String name;
    private Date date;

    public CandidateHit() {
    }

    public CandidateHit(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
