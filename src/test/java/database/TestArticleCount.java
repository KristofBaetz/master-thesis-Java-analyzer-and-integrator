package database;

import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.models.projections.ArticlesPerSource;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertTrue;

public class TestArticleCount {

    @Test
    public void testArticleCount(){

        ArticleDao articleDao = new ArticleDao();
        long numberOfArticles = articleDao.getNumberOfArticles();

        System.out.println("Number os Articles: " + numberOfArticles);

        assertTrue(0 < numberOfArticles);

    }

    @Test
    public void testCountArticlesWithinPresidencyTimeSpan(){

        ArticleDao articleDao = new ArticleDao();
        long numberOfArticles = articleDao.getNumberOfArticlesWithinPresidencyElectionCampaign();

        System.out.println("Number of Articles within Presidency Campaign: " + numberOfArticles);

        assertTrue(0 < numberOfArticles);

    }

    @Test
    public void testForDuplicateArticlesByUrl(){

        String query =
                """
                        SELECT *
                        FROM (
                        SELECT DISTINCT(url), COUNT(url) as number, title, sourceSite
                        FROM article
                        GROUP BY url) t1
                        WHERE number > 1;
                        """;

    }

    @Test
    public void testGetNumberOfArticleBySource(){

        ArticleDao articleDao = new ArticleDao();
        List<ArticlesPerSource> articlesPerSource = articleDao.getNumberOfArticleBySource();

        for (ArticlesPerSource articlePerSource : articlesPerSource) {
            System.out.println(articlePerSource.getSource() + ": " + articlePerSource.getNumber());
        }

    }

}
