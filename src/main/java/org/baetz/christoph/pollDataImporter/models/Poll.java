package org.baetz.christoph.pollDataImporter.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Poll {

    private int id;
    private String pollSource;

    private String startDate;
    private String endDate;

    private String dateRaw;

    private String democraticCandidateName;

    private double democraticCandidateResult;

    private String republicanCandidateName;

    private double republicanCandidateResult;

    private double leadingByPoints;

    private int sampleSize;

    private Double marginError;

    public Poll() {
    }

    public void parseStartAndEnddate(String rawdate){

        boolean crackedTheCase = false;

        // case 1: "October 31 – November 6, 2016"
        String regexCase1 = "\\b(January|February|March|April|May|June|" +
                            "July|August|September|October|November|December) \\d{1,2} – " +
                            "(January|February|March|April|May|June|" +
                            "July|August|September|October|November|December) \\d{1,2}, \\d{4}\\b";

        // if found this, continue with method case 1
        Pattern pattern = Pattern.compile(regexCase1);
        Matcher matcher = pattern.matcher(rawdate);

        while (matcher.find()) {
            parseCase1(rawdate);
            crackedTheCase = true;
        }

        if (crackedTheCase) return;


        // case 2 "November 1–7, 2016"
        String regexCase2 = "\\b(January|February|March|April|May|June|" +
                            "July|August|September|October|November|December) \\d{1,2}–\\d{1,2}, \\d{4}\\b";

        // if found this, continue with method case 2
        Pattern patternCase2 = Pattern.compile(regexCase2);
        Matcher matcherCase2 = patternCase2.matcher(rawdate);

        while (matcherCase2.find()) {
            parseCase2(rawdate);
            crackedTheCase = true;
        }

        if (crackedTheCase) return;

        // case 3 "October 10, 2016"
        // Regex pattern to match the date
        String regexCase3 = "\\b(January|February|March|April|May|June|July|August|September|October|November|December) \\d{1,2}, \\d{4}\\b";


        Pattern patternCase3 = Pattern.compile(regexCase3);
        Matcher matcherCase3 = patternCase3.matcher(rawdate);

        while (matcherCase3.find()) {
            parseCase3(rawdate);
            crackedTheCase = true;
        }

        if (crackedTheCase) return;
    }

    private void parseCase1(String dateRaw){

        // Regex pattern to match the date range
        Pattern pattern = Pattern.compile("(\\w+ \\d{1,2}) – (\\w+ \\d{1,2}), (\\d{4})");
        Matcher matcher = pattern.matcher(dateRaw);

        if (matcher.find()) {
            String startDateStr = matcher.group(1) + ", " + matcher.group(3);
            String endDateStr = matcher.group(2) + ", " + matcher.group(3);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            this.startDate = startDate.toString();

            this.endDate = endDate.toString();
        } else {
            System.out.println("No match found");
        }
    }

    private void parseCase2(String dateRaw){

        // Regex pattern to match the date range
        Pattern pattern = Pattern.compile("(\\w+) (\\d{1,2})–(\\d{1,2}), (\\d{4})");
        Matcher matcher = pattern.matcher(dateRaw);

        if (matcher.find()) {
            String month = matcher.group(1);
            String startDay = matcher.group(2);
            String endDay = matcher.group(3);
            String year = matcher.group(4);

            String startDateStr = month + " " + startDay + ", " + year;
            String endDateStr = month + " " + endDay + ", " + year;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);

            this.startDate = startDate.toString();
            this.endDate = endDate.toString();

        } else {
            System.out.println("No match found");
        }
    }

    private void parseCase3 (String dateRaw){

        // Regex pattern to match the date
        Pattern pattern = Pattern.compile("(\\w+) (\\d{1,2}), (\\d{4})");
        Matcher matcher = pattern.matcher(dateRaw);

        if (matcher.find()) {
            String month = matcher.group(1);
            String day = matcher.group(2);
            String year = matcher.group(3);

            String dateStr = month + " " + day + ", " + year;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);

            LocalDate date = LocalDate.parse(dateStr, formatter);

            this.startDate = date.toString();
            this.endDate = date.toString();

        } else {
            System.out.println("No match found");
        }

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPollSource() {
        return pollSource;
    }

    public void setPollSource(String pollSource) {
        this.pollSource = pollSource;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDateRaw() {
        return dateRaw;
    }

    public void setDateRaw(String dateRaw) {
        this.dateRaw = dateRaw;
    }

    public double getDemocraticCandidateResult() {
        return democraticCandidateResult;
    }

    public void setDemocraticCandidateResult(double democraticCandidateResult) {
        this.democraticCandidateResult = democraticCandidateResult;
    }

    public double getRepublicanCandidateResult() {
        return republicanCandidateResult;
    }

    public void setRepublicanCandidateResult(double republicanCandidateResult) {
        this.republicanCandidateResult = republicanCandidateResult;
    }

    public double getLeadingByPoints() {
        return leadingByPoints;
    }

    public void setLeadingByPoints(double leadingByPoints) {
        this.leadingByPoints = leadingByPoints;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public Double getMarginError() {
        return marginError;
    }

    public void setMarginError(Double marginError) {
        this.marginError = marginError;
    }

    public String getDemocraticCandidateName() {
        return democraticCandidateName;
    }

    public void setDemocraticCandidateName(String democraticCandidateName) {
        this.democraticCandidateName = democraticCandidateName;
    }

    public String getRepublicanCandidateName() {
        return republicanCandidateName;
    }

    public void setRepublicanCandidateName(String republicanCandidateName) {
        this.republicanCandidateName = republicanCandidateName;
    }
}
