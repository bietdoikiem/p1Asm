package com.RMIT;


import java.util.Date;

public class Interaction {
    private String id;
    private Date DOI;
    private Lead lead;
    private String mean;
    private String potential;

    public Interaction() {}

    public Interaction(Date DOI, Lead lead, String mean, String potential) {
        this.DOI = DOI;
        this.lead = lead;
        this.mean = mean;
        this.potential = potential;
    }

    public Date getDOI() {
        return DOI;
    }

    public void setDOI(Date DOI) {
        this.DOI = DOI;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getPotential() {
        return potential;
    }

    public void setPotential(String potential) {
        this.potential = potential;
    }
}
