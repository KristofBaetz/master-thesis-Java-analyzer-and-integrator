package analyser;

import org.baetz.christoph.sentimentanalyzer.sentimentAnalyser.SentimentAnalyserExecutor;
import org.junit.Test;

public class TestSentimentAnalyserExecutor {

    @Test
    public void testSentimentAnalyserExecutor(){

        SentimentAnalyserExecutor sentimentAnalyserExecutor = new SentimentAnalyserExecutor();
        sentimentAnalyserExecutor.execute();

    }

}
