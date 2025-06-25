package org.baetz.christoph.dao;

import org.baetz.christoph.database.NewsArticleDatabase;
import org.baetz.christoph.models.Article;
import org.baetz.christoph.models.projections.ArticlesPerSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {



    public List<Article> readArticlesWithinPresidentialCampaign() {

        List<Article> returnList = new ArrayList<>();

        String startDate = "2015-01-01";
        String endDate = "2016-11-08";

        String query =
                """
                        SELECT *
                        FROM article
                        WHERE ? < article.date AND article.date < ?
                        """;

        List<String> values = new ArrayList<>();
        values.add(startDate);
        values.add(endDate);

        ResultSet resultSet = NewsArticleDatabase.readQuery(query, values);
        try {
            if (resultSet != null) {
                while (resultSet.next()){

                    // map data to article objects

                    Article article = new Article();
                    article.setId(resultSet.getLong("id"));
                    article.setUrl(resultSet.getString("url"));
                    article.setSourceSite(resultSet.getString("sourceSite"));
                    article.setCandidateTage(resultSet.getString("candidateTag"));
                    article.setTitle(resultSet.getString("title"));
                    article.setTeaser(resultSet.getString("teaser"));
                    article.setContent(resultSet.getString("content"));
                    article.setDate(resultSet.getDate("date"));

                    returnList.add(article);

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return returnList;
    }

    public List<ArticlesPerSource> getNumberOfArticleBySource(){

        List<ArticlesPerSource> returnList = new ArrayList<>();

        String startDate = "2015-01-01";
        String endDate = "2016-11-08";

        String query =
                """
                SELECT sourceSite, count(*) as number
                FROM article
                WHERE ? < article.date AND article.date < ?
                GROUP BY sourceSite
                """;

        List<String> values = new ArrayList<>();
        values.add(startDate);
        values.add(endDate);

        ResultSet resultSet = NewsArticleDatabase.readQuery(query, values);

        try {
            if(resultSet != null) {
                while (resultSet.next()){

                    ArticlesPerSource articlesPerSource = new ArticlesPerSource();

                    articlesPerSource.setSource(resultSet.getString("sourceSite"));
                    articlesPerSource.setNumber(resultSet.getLong("number"));

                    returnList.add(articlesPerSource);

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return returnList;
    }

    public long getNumberOfArticles() {

        long returnValue = 0;

        String query =
                """
                        SELECT COUNT(*) as number
                        FROM article
                        """;

        return this.articleNumberQuery(query);

    }

    public long getNumberOfArticlesWithinPresidencyElectionCampaign() {

        String startDate = "2015-01-01";
        String endDate = "2016-11-08";

        String query =
                """
                        SELECT COUNT(*) as number
                        FROM article
                        WHERE ? < article.date AND article.date < ?
                        """;

        List<String> values = new ArrayList<>();
        values.add(startDate);
        values.add(endDate);

        return articleNumberQuery(query, values);
    }


    private long articleNumberQuery(String query) {

        return articleNumberQuery(query, new ArrayList<>());

    }

    private long articleNumberQuery(String query, List<String> values) {

        long returnValue = 0;

        ResultSet resultSet = NewsArticleDatabase.readQuery(query, values);
        try {
            if (resultSet != null) {
                resultSet.next();
                returnValue = resultSet.getLong("number");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return returnValue;

    }
}
