package org.baetz.christoph.sentimentanalyzer.sentimentAnalyser;

import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.models.Article;

import java.util.ArrayList;
import java.util.List;

public class SentimentAnalyserExecutor {

    public void execute() {

        // load data to process
        System.out.println("Load articles");
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.readArticlesWithinPresidentialCampaign();

        // get the amount of available cores "amountOfCores"
        int amountOfCores = Runtime.getRuntime().availableProcessors();
        System.out.println(amountOfCores + " cores detected");

        // share List<Article> articles between other number of other lists
        List<List<Article>> listOfArticleLists = new ArrayList<>();
        List<SentimentAnalyserThread> sentimentAnalyserThreads = new ArrayList<>();

        for (int i = 0; i < amountOfCores; i++) {
            listOfArticleLists.add(new ArrayList<>());
        }

        for (int i = 0; !articles.isEmpty(); i++) {
            listOfArticleLists.get(i % amountOfCores).add(articles.remove(0));
        }

        for (int i = 0; i < listOfArticleLists.size(); i++) {
            System.out.println("List Number " + i + " has " + listOfArticleLists.get(i).size() + " articles to analyze");
        }

        // create processing instances
        for (int i = 0; i < listOfArticleLists.size(); i++) {

            SentimentAnalyserThread sentimentAnalyserThread = new SentimentAnalyserThread(i, listOfArticleLists.get(i));
            sentimentAnalyserThreads.add(sentimentAnalyserThread);
            sentimentAnalyserThreads.get(i).start();

        }

        // wait for all processes
        for(int i = 0; i < sentimentAnalyserThreads.size(); i++){

            try {
                sentimentAnalyserThreads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
