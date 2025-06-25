package analyser;

import org.baetz.christoph.sentimentanalyzer.SentimentAnalyser;
import org.junit.Test;

public class TestSentimentAnalyser {

    @Test
    public void testSentimentAnalyser(){

        SentimentAnalyser sentimentAnalyser = new SentimentAnalyser();
        sentimentAnalyser.analyse();

    }

}
