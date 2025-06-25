package org.baetz.christoph.models;

import java.util.Date;

public class Article {

    private long id;
    private String url;
    private String sourceSite;
    private String candidateTage;
    private String title;
    private String teaser;
    private String content;
    private Date date;


    private CandidateHit candidateHit;

    public Article() {
    }

    public Article(long id, String url, String sourceSite, String candidateTage, String title, String teaser, String content, Date date) {
        this.id = id;
        this.url = url;
        this.sourceSite = sourceSite;
        this.candidateTage = candidateTage;
        this.title = title;
        this.teaser = teaser;
        this.content = content;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    public String getCandidateTage() {
        return candidateTage;
    }

    public void setCandidateTage(String candidateTage) {
        this.candidateTage = candidateTage;
    }

    public String getTitle() {

        if(title == null){
            return "";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeaser() {
        if(teaser == null){
            return "";
        }
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getContent() {
        if(content == null){
            return "";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CandidateHit getCandidateHit() {
        return candidateHit;
    }

    public void setCandidateHit(CandidateHit candidateHit) {
        this.candidateHit = candidateHit;
    }
}
