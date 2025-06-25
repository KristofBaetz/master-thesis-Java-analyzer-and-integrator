package org.baetz.christoph.models;

import java.util.Date;

public class Candidate {

    int id;
    String name;
    Date candidacyEnd;
    Date candidacyDeclared;

    String campaignSpeechSummaryUrl;

    String campaignStatementSummaryUrl;

    String getCampaignPressReleasesSummaryUrl;

    public Candidate() {
    }

    public Candidate(int id, String name, Date candidacyEnd, Date candidacyDeclared, String campaignSpeechSummaryUrl, String campaignStatementSummaryUrl, String getCampaignPressReleasesSummaryUrl) {
        this.id = id;
        this.name = name;
        this.candidacyEnd = candidacyEnd;
        this.candidacyDeclared = candidacyDeclared;
        this.campaignSpeechSummaryUrl = campaignSpeechSummaryUrl;
        this.campaignStatementSummaryUrl = campaignStatementSummaryUrl;
        this.getCampaignPressReleasesSummaryUrl = getCampaignPressReleasesSummaryUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCandidacyEnd() {
        return candidacyEnd;
    }

    public void setCandidacyEnd(Date candidacyEnd) {
        this.candidacyEnd = candidacyEnd;
    }

    public Date getCandidacyDeclared() {
        return candidacyDeclared;
    }

    public void setCandidacyDeclared(Date candidacyDeclared) {
        this.candidacyDeclared = candidacyDeclared;
    }

    public String getCampaignSpeechSummaryUrl() {
        return campaignSpeechSummaryUrl;
    }

    public void setCampaignSpeechSummaryUrl(String campaignSpeechSummaryUrl) {
        this.campaignSpeechSummaryUrl = campaignSpeechSummaryUrl;
    }

    public String getCampaignStatementSummaryUrl() {
        return campaignStatementSummaryUrl;
    }

    public void setCampaignStatementSummaryUrl(String campaignStatementSummaryUrl) {
        this.campaignStatementSummaryUrl = campaignStatementSummaryUrl;
    }

    public String getGetCampaignPressReleasesSummaryUrl() {
        return getCampaignPressReleasesSummaryUrl;
    }

    public void setGetCampaignPressReleasesSummaryUrl(String getCampaignPressReleasesSummaryUrl) {
        this.getCampaignPressReleasesSummaryUrl = getCampaignPressReleasesSummaryUrl;
    }
}
