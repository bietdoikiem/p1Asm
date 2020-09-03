package com.RMIT;
import com.opencsv.bean.CsvBindByName;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class Interaction {
    @CsvBindByName(column = "id", required = true)
    private String id;

    @CsvBindByName(column = "DOI", required = true)
    private Date DOI;

    @CsvBindByName(column = "lead", required = true)
    private Lead lead;

    @CsvBindByName(column = "mean", required = true)
    private String mean;

    @CsvBindByName(column = "potential", required = true)
    private String potential;

    public Interaction() {}

    public Interaction(Date DOI, Lead lead, String mean, String potential) {
        this.id = "inter_" + Math.floor(100*Math.random());
        this.DOI = DOI;
        this.lead = lead;
        this.mean = mean;
        this.potential = potential;
    }

    public String getId() {
        return id;
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
