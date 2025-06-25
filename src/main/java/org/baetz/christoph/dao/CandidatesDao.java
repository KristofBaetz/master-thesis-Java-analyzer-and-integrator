package org.baetz.christoph.dao;

import org.baetz.christoph.database.NewsArticleDatabase;
import org.baetz.christoph.models.Article;
import org.baetz.christoph.models.Candidate;
import org.checkerframework.checker.units.qual.C;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidatesDao {


    public List<Candidate> readAllCandidates() {

        List<Candidate> returnList = new ArrayList<>();

        String query =
                """
                        SELECT *
                        FROM candidates
                        """;


        ResultSet resultSet = NewsArticleDatabase.readQuery(query);
        try {
            if (resultSet != null) {
                while (resultSet.next()){

                    // map data to article objects

                    Candidate candidate = new Candidate();
                    candidate.setId(resultSet.getInt("id"));
                    candidate.setName(resultSet.getString("name"));
                    candidate.setCandidacyEnd(resultSet.getDate("candidacyEnd"));
                    candidate.setCandidacyDeclared(resultSet.getDate("candidacyDeclared"));
                    candidate.setCampaignSpeechSummaryUrl(resultSet.getString("campaignSpeechSummaryUrl"));
                    candidate.setCampaignStatementSummaryUrl(resultSet.getString("campaignPressReleasesSummaryUrl"));
                    candidate.setGetCampaignPressReleasesSummaryUrl(resultSet.getString("campaignStatementSummaryUrl"));

                    returnList.add(candidate);

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


}
