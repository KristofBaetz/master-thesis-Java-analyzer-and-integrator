package org.baetz.christoph.pollDataImporter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.baetz.christoph.pollDataImporter.dao.PollsDao;
import org.baetz.christoph.pollDataImporter.models.Poll;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PollsDataImporter {

    public static void main(String[] args) {

        // delete afterConventionPolls before insert data
        PollsDao pollsDao = new PollsDao();
        pollsDao.deleteAll();

        importAfterConventionPolls();

        import2015Polls();

        import2016Polls();

    }

    private static void importAfterConventionPolls() {

        try {

            File file = new File("After convention polls-UTF-8.csv");

            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();


            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData = csvReader.readAll();

            // print Data
            // put data in objects

            List<Poll> pollList = new ArrayList<>();

            for (String[] row : allData) {

                Poll poll = new Poll();

                for (int i = 0; i < row.length; i++) {

                    // System.out.print(row[i] + "\t");
                    if(i == 0) poll.setPollSource(row[i]);

                    if(i == 1) poll.parseStartAndEnddate(row[i]);

                    if(i == 2) poll.setDemocraticCandidateResult(Double.parseDouble(row[i].replace("%","")) / 100);

                    if(i == 3) poll.setRepublicanCandidateResult(Double.parseDouble(row[i].replace("%","")) / 100);

                    if(i == 4) {
                        if (row[i].equals("Tied")){
                            poll.setLeadingByPoints(0.0);
                        }
                        else {
                            poll.setLeadingByPoints(Double.parseDouble(row[i]));
                        }
                    }

                    if(i == 5) poll.setSampleSize(Integer.parseInt(row[i].replace(",","")));

                    if(i == 6) {

                        String temp =
                                row[i]
                                .replace("±", "")
                                .replace("%","")
                                .trim();

                        if(temp.isEmpty()){
                            poll.setMarginError(0.0);
                        }
                        else {
                            poll.setMarginError(
                                    Double.parseDouble(temp));
                        }
                    }

                    // set remaining fields
                    poll.setDemocraticCandidateName("Hillary Clinton");
                    poll.setRepublicanCandidateName("Donald Trump");
                }

                pollList.add(poll);
            }

            // save in database
            PollsDao pollsDao = new PollsDao();
            pollsDao.addAll(pollList);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void import2015Polls(){

        try {

            File file = new File("Polls Conducted in 2015-UTF-8.csv");

            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();


            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData = csvReader.readAll();

            // print Data
            // put data in objects

            List<Poll> pollList = new ArrayList<>();

            for (String[] row : allData) {

                Poll poll = new Poll();

                for (int i = 0; i < row.length; i++) {

                    // System.out.print(row[i] + "\t");
                    if(i == 0) poll.setPollSource(row[i]);

                    if(i == 1) poll.parseStartAndEnddate(row[i]);

                    if(i == 2) poll.setDemocraticCandidateName(row[i]);

                    if(i == 3) poll.setDemocraticCandidateResult(Double.parseDouble(row[i].replace(",",".")));

                    if(i == 4) poll.setRepublicanCandidateName(row[i]);

                    if(i == 5) poll.setRepublicanCandidateResult(Double.parseDouble(row[i].replace(",",".")));

                    if(i == 6) {
                        if (row[i].equals("Tied")){
                            poll.setLeadingByPoints(0.0);
                        }
                        else {
                            poll.setLeadingByPoints(Double.parseDouble(row[i]));
                        }
                    }

                    if(i == 7) {

                        String temp = row[i].replace(",","");

                        if(temp.isEmpty()){
                            poll.setSampleSize(0);
                        }
                        else {
                            poll.setSampleSize(Integer.parseInt(temp));
                        }
                    }

                    if(i == 8) {

                        String temp =
                                row[i]
                                        .replace("±", "")
                                        .replace("%","")
                                        .replace("?","")
                                        .replace(" ","")
                                        .trim();

                        if(temp.isEmpty()){
                            poll.setMarginError(0.0);
                        }
                        else {
                            poll.setMarginError(
                                    Double.parseDouble(temp));
                        }
                    }
                }

                pollList.add(poll);
            }

            // save in database
            PollsDao pollsDao = new PollsDao();
            pollsDao.addAll(pollList);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void import2016Polls(){

        try {

            File file = new File("Polls Conducted in 2016-UTF-8.csv");

            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator(';').build();


            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withCSVParser(parser)
                    .withSkipLines(1)
                    .build();

            List<String[]> allData = csvReader.readAll();

            // print Data
            // put data in objects

            List<Poll> pollList = new ArrayList<>();

            for (String[] row : allData) {

                Poll poll = new Poll();

                for (int i = 0; i < row.length; i++) {

                    // System.out.print(row[i] + "\t");
                    if(i == 0) poll.setPollSource(row[i]);

                    if(i == 1) poll.parseStartAndEnddate(row[i]);

                    if(i == 2) poll.setDemocraticCandidateName(row[i]);

                    if(i == 3) poll.setDemocraticCandidateResult(Double.parseDouble(row[i].replace(",",".")));

                    if(i == 4) poll.setRepublicanCandidateName(row[i]);

                    if(i == 5) poll.setRepublicanCandidateResult(Double.parseDouble(row[i].replace(",",".")));

                    if(i == 6) {
                        if (row[i].equals("Tied") || row[i].equals("Tie")){
                            poll.setLeadingByPoints(0.0);
                        }
                        else {
                            poll.setLeadingByPoints(Double.parseDouble(row[i]));
                        }
                    }

                    if(i == 7) {

                        String temp = row[i].replace(",","");

                        if(temp.isEmpty()){
                            poll.setSampleSize(0);
                        }
                        else {
                            poll.setSampleSize(Integer.parseInt(temp));
                        }
                    }

                    if(i == 8) {

                        String temp =
                                row[i]
                                        .replace("±", "")
                                        .replace("%","")
                                        .replace("?","")
                                        .replace(" ","")
                                        .trim();

                        if(temp.isEmpty()){
                            poll.setMarginError(0.0);
                        }
                        else {
                            poll.setMarginError(
                                    Double.parseDouble(temp));
                        }
                    }

                    // set remaining fields
                    poll.setDemocraticCandidateName("Hillary Clinton");
                    poll.setRepublicanCandidateName("Donald Trump");
                }

                pollList.add(poll);
            }

            // save in database
            PollsDao pollsDao = new PollsDao();
            pollsDao.addAll(pollList);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
