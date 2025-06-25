package analyser;

import org.baetz.christoph.analyser.CandidateAppearanceAnalyser;
import org.junit.Test;

public class TestCandidacyAppearanceAnalyser {

    @Test
    public void testCandidateAppearanceAnalyser() {

        CandidateAppearanceAnalyser candidateAppearanceAnalyser = new CandidateAppearanceAnalyser();
        candidateAppearanceAnalyser.analyse();

    }
}
