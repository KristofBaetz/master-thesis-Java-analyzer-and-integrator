package org.baetz.christoph.sentimentanalyzer.sentimentAnalyser;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.baetz.christoph.models.Article;

import java.util.List;
import java.util.Properties;

public class SentimentAnalyserThread extends Thread{

    int numberOfCore;

    StanfordCoreNLP pipeline;

    private List<Article> articles;

    public SentimentAnalyserThread(int numberOfCore, List<Article> articles) {
        this.numberOfCore = numberOfCore;
        this.articles = articles;
    }

    @Override
    public void run() {
        super.run();

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);

        while (!articles.isEmpty()){

            long start = System.currentTimeMillis();

            Article tempArticle = articles.remove(0);
            String newText = tempArticle.getContent();
            newText = newText.replace("\r\n", " ").replace("\n", " ");
            tempArticle.setContent(newText);

            estimatingSentiment(tempArticle.getContent());


            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            reportTimeElapsed(this.numberOfCore, timeElapsed, articles.size());

        }
    }

    private synchronized static void reportTimeElapsed(int numberOfCore, long timeElapsed, long articlesLeftToProcess){

        System.out.println("Thread: " + numberOfCore + " has " + articlesLeftToProcess + " articles left to process");
        System.out.printf("Time consumed: %,d ms", timeElapsed);
        System.out.println();
        System.out.println("-------------------------");

    }

    private void estimatingSentiment(String text)
    {
        int sentimentInt;
        String sentimentName;
        Annotation annotation = pipeline.process(text);
        for(CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class))
        {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentimentInt = RNNCoreAnnotations.getPredictedClass(tree);
            sentimentName = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            // System.out.println(sentimentName + "\t" + sentimentInt + "\t" + sentence);
        }
    }
}
