package org.baetz.christoph.sentimentanalyzer;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.models.Article;

import java.util.List;
import java.util.Properties;

public class SentimentAnalyser {

    public void analyse(){

        // load articles
        System.out.println("Load articles");
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.readArticlesWithinPresidentialCampaign();

        System.out.println("Done loading articles");

        // do analyse
        System.out.println("Init Core NLP");
        init();

        System.out.println("Done init Core NLP");

        System.out.println("Start Analysing");
        System.out.println("-------------------------");
        for (int i = 0; i < articles.size(); i++) {

            long start = System.currentTimeMillis();

            // preparing article string

            String newText = articles.get(i).getContent().replace("\r\n", " ").replace("\n", " ");
            articles.get(i).setContent(newText);

            estimatingSentiment(articles.get(i).getContent());

            long finish = System.currentTimeMillis();

            long timeElapsed = finish - start;

            System.out.println("Finished " + i + " out of " + articles.size());
            System.out.printf("Time consumed: %,d ms", timeElapsed);
            System.out.println();
            System.out.println("-------------------------");
        }


        // TODO: save results

    }

    static StanfordCoreNLP pipeline;
    public static void init()
    {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }
    public static void estimatingSentiment(String text)
    {
        int sentimentInt;
        String sentimentName;
        Annotation annotation = pipeline.process(text);
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            System.out.println(sentimentName + "\t" + sentimentInt + "\t" + sentence);
        }
    }

}
