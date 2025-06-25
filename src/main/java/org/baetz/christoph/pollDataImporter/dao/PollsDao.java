package org.baetz.christoph.pollDataImporter.dao;

import org.baetz.christoph.database.NewsArticleDatabase;
import org.baetz.christoph.pollDataImporter.models.Poll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PollsDao {

    public void addAll(List<Poll> pollList){

        for (Poll poll : pollList) {

            add(poll);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public void add(Poll poll) {

        String statement =
                """
                    INSERT INTO polls
                    (pollSource, startDate, endDate, democraticCandidateName, democraticCandidateResult,
                        republicanCandidateName, republicanCandidateResult, leadingByPoints,
                        sampleSize, marginError)
                    VALUES (?,?,?,?,?,?,?,?,?,?)
                    """;

        List<String> values = new ArrayList<>();
        values.add(poll.getPollSource());
        values.add(poll.getStartDate());
        values.add(poll.getEndDate());
        values.add(poll.getDemocraticCandidateName());
        values.add(String.valueOf(poll.getDemocraticCandidateResult()));
        values.add(poll.getRepublicanCandidateName());
        values.add(String.valueOf(poll.getRepublicanCandidateResult()));
        values.add(String.valueOf(poll.getLeadingByPoints()));
        values.add(String.valueOf(poll.getLeadingByPoints()));
        values.add(String.valueOf(poll.getSampleSize()));
        values.add(String.valueOf(poll.getMarginError()));

        try (ResultSet resultSet = NewsArticleDatabase.readQuery(statement, values)) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(){

        String statement = """
                
                DELETE FROM polls
                WHERE id > 0
                
                """;

        NewsArticleDatabase.readQuery(statement);

    }
}
