package org.baetz.christoph.analyser;

import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.models.Article;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashTagExtractor {

    public void analyse(){

        System.out.println("Loading Articles");
        // TODO: load all articles
        ArticleDao articleDao = new ArticleDao();
        List<Article> articles = articleDao.readArticlesWithinPresidentialCampaign();

        System.out.println("Looking for Hashtags");
        // Definiere das gewünschte Muster
        String regex = "\\B(#[a-zA-Z]+\\b)(?!;)";

        // Kompiliere das Muster
        Pattern pattern = Pattern.compile(regex);

        for (Article article : articles) {

            // Erzeuge einen Matcher für den Eingabestring
            Matcher matcher = pattern.matcher(article.getContent());

            if(article.getContent().contains("Hashtag") ||
               article.getContent().contains("hashtag"))
            {
                System.out.println("Hashtag is mentioned");
            }

            // Finde alle Treffer
            while (matcher.find()) {
                // Gib den gefundenen Treffer aus
                System.out.println("Gefunden: " + matcher.group(1));

            }
        }

        // TODO: save it in a way that it is related to a certain article

    }
}
