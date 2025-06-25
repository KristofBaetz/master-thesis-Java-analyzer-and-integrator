package org.baetz.christoph.databaseGraphLoader.rdfLoader.dao;

import org.baetz.christoph.database.NewsArticleDatabase;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.models.Tweet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TweetDao {

    /**
     *
     * @param tweet
     *
     * Speichert einen Tweet, mit all seinen Daten, in der Datenbank
     */
    public void saveSingleTweet(Tweet tweet) {

        String query = """
                                
                INSERT INTO tweets
                (tweetId, tweetURI, created, creatorId, entityName, entityConfidence, entityDetected,
                mentionedUsers, mentionedTag, positiveEmotionIntensity,
                negativeEmotionIntensity, likeCount, shareCount)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
                                
                """;

        List<String> values = new ArrayList<>();
        values.add(tweet.getId());
        values.add(tweet.getTweetURI());
        values.add(tweet.getCreationDate().toString());
        values.add(tweet.getCreatorId());
        values.add(tweet.getEntityName());
        values.add(String.valueOf(tweet.getEntityConfidence()));
        values.add(tweet.getEntityDetected());
        values.add(tweet.getMentionedUsers());
        values.add(tweet.getMentionedTag());
        values.add(String.valueOf(tweet.getPositiveEmotionIntensity()));
        values.add(String.valueOf(tweet.getNegativeEmotionIntensity()));
        values.add(String.valueOf(tweet.getLikeCount()));
        values.add(String.valueOf(tweet.getShareCount()));

        try{
            ResultSet resultSet = NewsArticleDatabase.readQuery(query, values);

            if (resultSet != null) {
                resultSet.close();
            }
        }
        catch (Exception exception){
            // exception.printStackTrace();
        }
    }
}
