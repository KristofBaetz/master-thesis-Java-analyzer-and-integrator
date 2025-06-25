package database;

import org.baetz.christoph.dao.CandidatesDao;
import org.baetz.christoph.models.Candidate;
import org.junit.Test;

import java.util.List;

public class TestCandidates {

    @Test
    public void testReadCandidates(){

        CandidatesDao candidatesDao = new CandidatesDao();
        List<Candidate> candidates = candidatesDao.readAllCandidates();

        for (Candidate candidate : candidates) {
            System.out.println(candidate.getName());
        }
    }

}
