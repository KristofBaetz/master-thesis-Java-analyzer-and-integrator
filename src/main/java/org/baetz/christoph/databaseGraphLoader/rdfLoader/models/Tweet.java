package org.baetz.christoph.databaseGraphLoader.rdfLoader.models;

import java.time.LocalDateTime;

public class Tweet {
    private String id;

    private String tweetURI;
    private LocalDateTime creationDate;
    private String creatorId;
    private String entityName;
    private Double entityConfidence = 0.0;
    private String entityDetected;
    private String mentionedUsers;
    private String mentionedTag;
    private Double positiveEmotionIntensity = 0.0;
    private Double negativeEmotionIntensity = 0.0;
    private Integer likeCount = 0;

    private Integer shareCount;

    // Konstruktor
    public Tweet() {}

    // Getter und Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTweetURI() {
        return tweetURI;
    }

    public void setTweetURI(String tweetURI) {
        this.tweetURI = tweetURI;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Double getEntityConfidence() {
        return entityConfidence;
    }

    public void setEntityConfidence(Double entityConfidence) {

        if(entityConfidence == null){
            this.entityConfidence = 0.0;
        }
        else {
            this.entityConfidence = entityConfidence;
        }
    }

    public String getEntityDetected() {
        return entityDetected;
    }

    public void setEntityDetected(String entityDetected) {
        this.entityDetected = entityDetected;
    }

    public String getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(String mentionedUsers) {
        this.mentionedUsers = mentionedUsers;
    }

    public String getMentionedTag() {
        return mentionedTag;
    }

    public void setMentionedTag(String mentionedTag) {
        this.mentionedTag = mentionedTag;
    }

    public Double getPositiveEmotionIntensity() {
        return positiveEmotionIntensity;
    }

    public void setPositiveEmotionIntensity(Double positiveEmotionIntensity) {

        if(positiveEmotionIntensity == null){
            this.positiveEmotionIntensity = 0.0;
        }
        else {
            this.positiveEmotionIntensity = positiveEmotionIntensity;
        }
    }

    public Double getNegativeEmotionIntensity() {
        return negativeEmotionIntensity;
    }

    public void setNegativeEmotionIntensity(Double negativeEmotionIntensity) {

        if(negativeEmotionIntensity == null){
            this.negativeEmotionIntensity = 0.0;
        }
        else {
            this.negativeEmotionIntensity = negativeEmotionIntensity;
        }
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }



    @Override
    public String toString() {
        return "Tweet{" +
               "id='" + id + '\'' +
               "uri='" + tweetURI + '\'' +
               ", creationDate=" + creationDate +
               ", creatorId='" + creatorId + '\'' +
               ", entityName='" + entityName + '\'' +
               ", entityConfidence=" + entityConfidence +
               ", entityDetected='" + entityDetected + '\'' +
               ", mentionedUser='" + mentionedUsers + '\'' +
               ", mentionedTag='" + mentionedTag + '\'' +
               ", positiveEmotionIntensity=" + positiveEmotionIntensity +
               ", negativeEmotionIntensity=" + negativeEmotionIntensity +
               ", likeCount=" + likeCount +
               ", shareCount=" + shareCount +
               '}';
    }
}
