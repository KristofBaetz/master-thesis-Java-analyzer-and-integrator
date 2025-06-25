package database;

import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.models.Article;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;

public class TestReadArticlesOutOfDatabase {

    @Test
    public void testReadArticlesOutOfDatabaseWithinPresidencyElectionCampaign() {


        ArticleDao articleDao = new ArticleDao();
        List<Article> list = articleDao.readArticlesWithinPresidentialCampaign();

        System.out.println("Number of Articles: " + list.size());

        // display title of first 100 headlines
        for (int i = 0; i < 100; i++) {

            System.out.println(list.get(i).getTitle());

        }

        assertFalse(list.isEmpty());

    }
}
