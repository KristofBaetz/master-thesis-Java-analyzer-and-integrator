package org.baetz.christoph.analyser;

import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.dao.CandidatesDao;
import org.baetz.christoph.models.Article;
import org.baetz.christoph.models.Candidate;

import java.util.List;

public class CandidateAppearanceAnalyser {

    public void analyse() {

        System.out.println("Start Candidate Appearance Analyser");

        System.out.println("Load Candidates");

        // load candidates
        CandidatesDao candidatesDao = new CandidatesDao();
        List<Candidate> candidates = candidatesDao.readAllCandidates();

        System.out.println("Load relevant articles in right timespan");

        // load all relevant articles
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.readArticlesWithinPresidentialCampaign();

        System.out.println("Check for appearance");

        int numberOfOccurrences = 0;

        // check if one of the candidates appeared
        for (Article article : articles) {
            for (Candidate candidate : candidates) {
                if (
                        article.getTitle().contains(candidate.getName()) ||
                        article.getTeaser().contains(candidate.getName()) ||
                        article.getContent().contains(candidate.getName())) {

                    // TODO: mark that:
                    System.out.println("Article '" + article.getTitle() + "' Contained Candidate '" + candidate.getName() + "'");
                    numberOfOccurrences = numberOfOccurrences + 1;

                }
            }

        }

        System.out.println(numberOfOccurrences + " findings");

        // TODO: save finding in database

        // TODO: consider what happens in an article without a finding

    }

}
