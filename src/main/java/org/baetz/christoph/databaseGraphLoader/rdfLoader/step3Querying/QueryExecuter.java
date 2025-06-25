package org.baetz.christoph.databaseGraphLoader.rdfLoader.step3Querying;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.tdb2.TDB2;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.dao.TweetDao;
import org.baetz.christoph.databaseGraphLoader.rdfLoader.models.Tweet;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;


public class QueryExecuter {

    public void execute(String n3FilePath) {

        // Festlegen einiger Parameter, um Puffergroessen zu erhoehen, damit die Prozessierung schneller laufen kann
        System.setProperty("tdb2.block_size", String.valueOf(8 * 1024));
        System.setProperty("tdb2.node2id_cache_size", String.valueOf(1000000));
        System.setProperty("tdb2.nodemiss_cache_size", String.valueOf(1000000));

        // Wichtig! Hier wird ein Dataset im Arbeitsspeicher erstellt. Dadurch, dass diese nicht auf der Festplatte
        // erstellt wird, verkürzt sich die Bearbeitungszeit auf ungefaehr ein Sechzigstel
        Dataset dataset = DatasetFactory.createTxnMem();

        System.out.println("Starting to load N3 file into TDB2...");

        // Fuer die Zeitmessung dieses Schrittes wird hier die Startzeit bestimmt
        Instant start = Instant.now();

        // Daten aus der .n3-Datei, die im Übergabewert der Methode angegebenen worden ist, werden in das
        // Dataset geladen
        dataset.begin(ReadWrite.WRITE);
        try {
            Model model = dataset.getDefaultModel();
            RDFDataMgr.read(model, n3FilePath, RDFLanguages.N3);
            dataset.commit();
            System.out.println(n3FilePath + " loaded successfully into TDB2.");
        } catch (Exception e) {
            System.out.println("Error during load: " + e.getMessage());
            dataset.abort();
        } finally {
            dataset.end();
        }

        // Fuer die Zeitmessung dieses Schrittes wird hier die Endzeit bestimmt
        Instant end = Instant.now();
        Duration loadTime = Duration.between(start, end);
        System.out.printf("Time taken to load: %d minutes, %d seconds%n", loadTime.toMinutes(), loadTime.getSeconds() % 60);

        // Hier wird eine Abfragee gestartet, um alle Tweets der Dateien in Zeilen abfragen zu koennen
        System.out.println("Executing query to filter relevant tweets that mention presidential candidates");
        start = Instant.now();

        dataset.begin(ReadWrite.READ);
        try {
            String queryString =

                    """
                            PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
                            PREFIX sioc: <http://rdfs.org/sioc/ns#>
                            PREFIX dc: <http://purl.org/dc/terms/>
                            PREFIX schema: <http://schema.org/>
                            PREFIX onyx: <http://www.gsi.dit.upm.es/ontologies/onyx/ns#>
                            PREFIX wna: <http://www.gsi.dit.upm.es/ontologies/wnaffect/ns#>
                            PREFIX nee: <http://www.ics.forth.gr/isl/oae/core#>
                            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
                                                        
                            SELECT ?tweet ?tweetId ?creationDate ?creatorId
                                   ?entityDetected ?entityConfidence
                                   ?positiveEmotionIntensity ?negativeEmotionIntensity
                                   ?likeCount ?shareCount
                                   (GROUP_CONCAT(DISTINCT ?mentionedUser; SEPARATOR=", ") AS ?mentionedUsers)
                            WHERE {
                              ?tweet rdf:type sioc:Post ;
                                     sioc:id ?tweetId ;
                                     dc:created ?creationDate ;
                                     sioc:has_creator ?creator .
                             
                              ?creator sioc:id ?creatorId .
                             
                              OPTIONAL {
                                ?tweet schema:mentions ?entity .
                                ?entity rdf:type nee:Entity ;
                                        nee:detectedAs ?entityDetected ;
                                        nee:confidence ?entityConfidence .
                              }
                             
                              OPTIONAL {
                                ?tweet onyx:hasEmotionSet ?emotionSet .
                                OPTIONAL {
                                  ?emotionSet onyx:hasEmotion ?positiveEmotion .
                                  ?positiveEmotion onyx:hasEmotionCategory wna:positive-emotion ;
                                                   onyx:hasEmotionIntensity ?positiveEmotionIntensity .
                                }
                                OPTIONAL {
                                  ?emotionSet onyx:hasEmotion ?negativeEmotion .
                                  ?negativeEmotion onyx:hasEmotionCategory wna:negative-emotion ;
                                                   onyx:hasEmotionIntensity ?negativeEmotionIntensity .
                                }
                              }
                             
                              OPTIONAL {
                                ?tweet schema:interactionStatistic ?likeStats .
                                ?likeStats schema:interactionType schema:LikeAction ;
                                           schema:userInteractionCount ?likeCount .
                              }
                             
                              OPTIONAL {
                                ?tweet schema:interactionStatistic ?shareStats .
                                ?shareStats schema:interactionType schema:ShareAction ;
                                            schema:userInteractionCount ?shareCount .
                              }
                             
                              OPTIONAL {
                                ?tweet schema:mentions ?mentionedAccount .
                                ?mentionedAccount rdf:type sioc:UserAccount ;
                                                  sioc:name ?mentionedUser .
                              }
                            }
                            GROUP BY ?tweet ?tweetId ?creationDate ?creatorId
                                     ?entityDetected ?entityConfidence
                                     ?positiveEmotionIntensity ?negativeEmotionIntensity
                                     ?likeCount ?shareCount
                                                        
                                """;

            // Die Ergebnisse der Abfrage werden hier in Java-Objekte umgewandelt
            try (QueryExecution qexec = QueryExecutionFactory.create(queryString, dataset)) {
                try {
                    ResultSet results = qexec.execSelect();
                    while (results.hasNext()) {
                        QuerySolution solution = results.nextSolution();

                        Tweet tweet = new Tweet();

                        if (solution.contains("tweet")) tweet.setId(solution.getResource("tweet").getURI());

                        if (solution.contains("tweetId")) tweet.setId(solution.getLiteral("tweetId").getString());

                        if (solution.contains("creationDate")) {
                            String dateStr = solution.getLiteral("creationDate").getString();
                            tweet.setCreationDate(LocalDateTime.parse(dateStr));
                        }

                        if (solution.contains("creatorId"))
                            tweet.setCreatorId(solution.getLiteral("creatorId").getString());

                        if (solution.contains("entityDetected"))
                            tweet.setEntityName(solution.get("entityDetected").asLiteral().getString());

                        if (solution.contains("entityConfidence"))
                            tweet.setEntityConfidence(solution.getLiteral("entityConfidence").getDouble());

                        if (solution.contains("mentionedUserId"))
                            tweet.setMentionedUsers(solution.getLiteral("mentionedUserId").getString());

                        if (solution.contains("positiveEmotionIntensity"))
                            tweet.setPositiveEmotionIntensity(solution.getLiteral("positiveEmotionIntensity").getDouble());

                        if (solution.contains("negativeEmotionIntensity"))
                            tweet.setNegativeEmotionIntensity(solution.getLiteral("negativeEmotionIntensity").getDouble());

                        if (solution.contains("likeCount"))
                            tweet.setLikeCount(solution.getLiteral("likeCount").getInt());

                        if (solution.contains("shareCount"))
                            tweet.setShareCount(solution.getLiteral("shareCount").getInt());

                        if (solution.contains("mentionedUsers")) {
                            String mentionedUsers = solution.getLiteral("mentionedUsers").getString();
                            tweet.setMentionedUsers(Arrays.asList(mentionedUsers.split(", ")).toString());
                        }

                        saveTweet(tweet);
                    }
                } finally {
                    qexec.close();
                }

            }
        } finally {
            dataset.end();
        }

        // Fuer die Zeitmessung dieses Schrittes wird hier die Endzeit bestimmt
        end = Instant.now();
        Duration queryTime = Duration.between(start, end);
        System.out.printf("Time taken for query: %d minutes, %d seconds%n", queryTime.toMinutes(), queryTime.getSeconds() % 60);

        // Datenbank-Ressourcen werden freigegeben, um sicherzustellen, dass die Daten geloescht werden koennen.
        // So wird gewaehrleistet, dass der Arbeitsspeicher nicht überlaeuft und die Verarbeitung nicht an
        // Geschwindigkeit verliert
        dataset.close();
        TDB2.getContext().clear();

        // Loesche alle Daten in Datenbank
        deleteContent(dataset);

        // Bestimme Endzeit des gesamten Prozesses, bevor die naechste .n3-Datei dran ist
        end = Instant.now();
        queryTime = Duration.between(start, end);
        System.out.printf("Time taken for one splitted file query: %d minutes, %d seconds%n", queryTime.toMinutes(), queryTime.getSeconds() % 60);
    }

    /**
     *
     * @param tweet
     *
     * Rufe TweetDao auf, um die Daten eines Tweets zu speichern
     *
     */
    private void saveTweet(Tweet tweet) {

        if (tweet.getMentionedUsers() != null) {

            if (tweet.getMentionedUsers().toLowerCase().contains("realDonaldTrump".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("JohnKasich".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("tedcruz".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("marcorubio".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("RealBenCarson".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("JebBush".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("ChrisChristie".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("CarlyFiorina".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("RickSantorum".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("RandPaul".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("GovMikeHuckabee".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("GovernorPataki".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("LindseyGrahamSC".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("BobbyJindal".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("ScottWalker".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("GovernorPerry".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("HillaryClinton".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("BernieSanders".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("MartinOMalley".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("LincolnChafee".toLowerCase())
                || tweet.getMentionedUsers().toLowerCase().contains("JimWebbUSA".toLowerCase())) {

                TweetDao tweetDao = new TweetDao();
                tweetDao.saveSingleTweet(tweet);
            }
        }
    }


    /**
     *
     * @param dataset
     *
     * Methode, um alle Daten aus der Datenbank zu loeschen
     */
    private void deleteContent(Dataset dataset) {

        // Fuer die Zeitmessung dieses Schrittes wird hier die Startzeit bestimmt
        Instant start = Instant.now();

        dataset.begin(ReadWrite.WRITE);
        try {
            dataset.asDatasetGraph().clear();
            dataset.commit();
        } finally {
            dataset.end();
        }

        Instant end = Instant.now();
        Duration queryTime = Duration.between(start, end);
        System.out.printf("Time taken for deleting dataset: %d minutes, %d seconds%n", queryTime.toMinutes(), queryTime.getSeconds() % 60);
    }
}


