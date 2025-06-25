package org.baetz.christoph.driver;


import org.apache.commons.lang3.time.DateUtils;
import org.baetz.christoph.dao.ArticleDao;
import org.baetz.christoph.dao.CandidatesDao;
import org.baetz.christoph.models.Article;
import org.baetz.christoph.models.Candidate;
import org.baetz.christoph.models.CandidateHit;
import org.baetz.christoph.models.SummaryCandidatesHitPerDay;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class CandidatesMentionedOverTime {
    public static void main(String[] args) {

        System.out.println("Loading Candidates.");
        // load all candidates
        CandidatesDao candidatesDao = new CandidatesDao();
        List<Candidate> candidateList = candidatesDao.readAllCandidates();
        System.out.println("Done loading Candidates.");

        System.out.println("Loading all articles in relevant timerange.");
        // load all articles that content that...
        //      ... is within the defined time period for the election campaign
        ArticleDao articleDao = new ArticleDao();
        List<Article> articleList = articleDao.readArticlesWithinPresidentialCampaign();
        System.out.println("Done loading all articles in relevant timerange.");

        // create a list of all articles which mentions a candidate
        //      ... contains a candidates name
        List<Article> articleListToAnalyse = new ArrayList<>();
        List<CandidateHit> candidateHitList = new ArrayList<>();

        System.out.println("Check if an article mentions a candidate at all. Also count on how many articles a candidate was mentioned.");

        for (Article article : articleList) {
            for (Candidate candidate : candidateList) {
                if (article.getContent().contains(candidate.getName())) {
                    // TODO: add article Tag to article
                    articleListToAnalyse.add(article);
                    candidateHitList.add(new CandidateHit(candidate.getName(), article.getDate()));
                }
            }
        }

        System.out.println("Articles to analyze shrinked from " + articleList.size() + " to: " + articleListToAnalyse.size() + " articles ");

        System.out.println("Done check if an article mentions a candidate at all. " +
                           "Also count on how many articles a candidate was mentioned.");

        summarizeHitsPerCandidate(candidateHitList);
    }

    private static void summarizeHitsPerCandidate(List<CandidateHit> candidateHitList) {

        List<SummaryCandidatesHitPerDay> summaryCandidatesHitPerDayList = new ArrayList<>();

        Date startDateOfCampaign = new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime();
        Date endDateOfCampaign = new GregorianCalendar(2016, Calendar.NOVEMBER, 8).getTime();

        Date countDateVariable = startDateOfCampaign;
        // step 1: create list of all date to consider
        while (countDateVariable.getTime() <= endDateOfCampaign.getTime()) {

            SummaryCandidatesHitPerDay temp = new SummaryCandidatesHitPerDay(countDateVariable);
            summaryCandidatesHitPerDayList.add(temp);
            countDateVariable = DateUtils.addDays(countDateVariable, 1);

        }


        // step 2 order hitlist to summarylist
        for (CandidateHit candidateHit : candidateHitList) {

            for (SummaryCandidatesHitPerDay summaryCandidatesHitPerDay : summaryCandidatesHitPerDayList) {

                if (candidateHit.getDate().getTime() == summaryCandidatesHitPerDay.getDate().getTime()) {

                    if (candidateHit.getName().equals("Hillary Clinton")) {
                        summaryCandidatesHitPerDay.setHillaryClinton(summaryCandidatesHitPerDay.getHillaryClinton() + 1);

                    } else if (candidateHit.getName().equals("Bernie Sanders")) {
                        summaryCandidatesHitPerDay.setBernieSanders(summaryCandidatesHitPerDay.getBernieSanders() + 1);

                    } else if (candidateHit.getName().equals("Martin O'Malley")) {
                        summaryCandidatesHitPerDay.setMartinOMalley(summaryCandidatesHitPerDay.getMartinOMalley() + 1);

                    } else if (candidateHit.getName().equals("Lincoln Chafee")) {
                        summaryCandidatesHitPerDay.setLincolnChafee(summaryCandidatesHitPerDay.getLincolnChafee() + 1);

                    } else if (candidateHit.getName().equals("Jim Webb")) {
                        summaryCandidatesHitPerDay.setJimWebb(summaryCandidatesHitPerDay.getJimWebb() + 1);

                    } else if (candidateHit.getName().equals("Donald Trump")) {
                        summaryCandidatesHitPerDay.setDonaldTrump(summaryCandidatesHitPerDay.getDonaldTrump() + 1);

                    } else if (candidateHit.getName().equals("John Kasich")) {
                        summaryCandidatesHitPerDay.setJohnKasich(summaryCandidatesHitPerDay.getJohnKasich() + 1);

                    } else if (candidateHit.getName().equals("Ted Cruz")) {
                        summaryCandidatesHitPerDay.setTedCruz(summaryCandidatesHitPerDay.getTedCruz() + 1);

                    } else if (candidateHit.getName().equals("Marco Rubio")) {
                        summaryCandidatesHitPerDay.setMarcoRubio(summaryCandidatesHitPerDay.getMarcoRubio() + 1);

                    } else if (candidateHit.getName().equals("Ben Carson")) {
                        summaryCandidatesHitPerDay.setBenCarson(summaryCandidatesHitPerDay.getBenCarson() + 1);

                    } else if (candidateHit.getName().equals("Jeb Bush")) {
                        summaryCandidatesHitPerDay.setJebBush(summaryCandidatesHitPerDay.getJebBush() + 1);

                    } else if (candidateHit.getName().equals("Chris Christie")) {
                        summaryCandidatesHitPerDay.setChrisChristie(summaryCandidatesHitPerDay.getChrisChristie() + 1);

                    } else if (candidateHit.getName().equals("Carly Fiorina")) {
                        summaryCandidatesHitPerDay.setCarlyFiorina(summaryCandidatesHitPerDay.getCarlyFiorina() + 1);

                    } else if (candidateHit.getName().equals("Rick Santorum")) {
                        summaryCandidatesHitPerDay.setRickSantorum(summaryCandidatesHitPerDay.getRickSantorum() + 1);

                    } else if (candidateHit.getName().equals("Rand Paul")) {
                        summaryCandidatesHitPerDay.setRandPaul(summaryCandidatesHitPerDay.getRandPaul() + 1);

                    } else if (candidateHit.getName().equals("Mike Huckabee")) {
                        summaryCandidatesHitPerDay.setMikeHuckabee(summaryCandidatesHitPerDay.getMikeHuckabee() + 1);

                    } else if (candidateHit.getName().equals("George Pataki")) {
                        summaryCandidatesHitPerDay.setGeorgePataki(summaryCandidatesHitPerDay.getGeorgePataki() + 1);

                    } else if (candidateHit.getName().equals("Lindsey Graham")) {
                        summaryCandidatesHitPerDay.setLindseyGraham(summaryCandidatesHitPerDay.getLindseyGraham() + 1);

                    } else if (candidateHit.getName().equals("Bobby Jindal")) {
                        summaryCandidatesHitPerDay.setBobbyJindal(summaryCandidatesHitPerDay.getBobbyJindal() + 1);

                    } else if (candidateHit.getName().equals("Scott Walker")) {
                        summaryCandidatesHitPerDay.setScottWalker(summaryCandidatesHitPerDay.getScottWalker() + 1);

                    } else if (candidateHit.getName().equals("Rick Perry")) {
                        summaryCandidatesHitPerDay.setRickPerry(summaryCandidatesHitPerDay.getRickPerry() + 1);

                    }
                }
            }
        }

        // TODO: export to .csv
        // Pfad zur CSV-Datei
        String csvFile = "./results/candidatesMentionedOverTime.csv";
        String header =
                "id, date, Hillary Clinton, Bernie Sanders, Martin O'Malley, Lincoln Chafee, Jim Webb, " +
                "Donald Trump, John Kasich, Ted Cruz, Marco Rubio, Ben Carson, Jeb Bush, " +
                "Chris Christie, Carly Fiorina, Rick Santorum, Rand Paul, Mike Huckabee, George Pataki, " +
                "Lindsey Graham, Bobby Jindal, Scott Walker, Rick Perry";

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(csvFile);

            writer.println(header);

            for (SummaryCandidatesHitPerDay summaryCandidatesHitPerDay : summaryCandidatesHitPerDayList) {

                writer.println(
                        summaryCandidatesHitPerDay.getId()
                        + "," + summaryCandidatesHitPerDay.getDate()
                        + "," + summaryCandidatesHitPerDay.getHillaryClinton()
                        + "," + summaryCandidatesHitPerDay.getBernieSanders()
                        + "," + summaryCandidatesHitPerDay.getMartinOMalley()
                        + "," + summaryCandidatesHitPerDay.getLincolnChafee()
                        + "," + summaryCandidatesHitPerDay.getJimWebb()

                        + "," + summaryCandidatesHitPerDay.getDonaldTrump()
                        + "," + summaryCandidatesHitPerDay.getJohnKasich()
                        + "," + summaryCandidatesHitPerDay.getTedCruz()
                        + "," + summaryCandidatesHitPerDay.getMarcoRubio()
                        + "," + summaryCandidatesHitPerDay.getBenCarson()
                        + "," + summaryCandidatesHitPerDay.getJebBush()

                        + "," + summaryCandidatesHitPerDay.getChrisChristie()
                        + "," + summaryCandidatesHitPerDay.getCarlyFiorina()
                        + "," + summaryCandidatesHitPerDay.getRickSantorum()
                        + "," + summaryCandidatesHitPerDay.getRandPaul()
                        + "," + summaryCandidatesHitPerDay.getMikeHuckabee()
                        + "," + summaryCandidatesHitPerDay.getGeorgePataki()

                        + "," + summaryCandidatesHitPerDay.getLindseyGraham()
                        + "," + summaryCandidatesHitPerDay.getBobbyJindal()
                        + "," + summaryCandidatesHitPerDay.getScottWalker()
                        + "," + summaryCandidatesHitPerDay.getRickPerry()
                );
            }
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
