package com.RMIT;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;



import java.util.Date;

public class Interaction {
    @CsvBindByName(column = "id", required = true)
    private String id;

    @CsvBindByName(column = "DOI", required = true)
    @CsvDate(value="yyyy-MM-dd") // Annotation to parse Date format.
    private Date DOI;

    @CsvBindByName(column = "leadId", required = true)
    private String leadId;

    //private Lead lead; // Object referencing of lead

    @CsvBindByName(column = "mean", required = true)
    private String mean;

    @CsvBindByName(column = "potential", required = true)
    private String potential;

    public Interaction() {}

    public Interaction(Date DOI, String leadId, String mean, String potential) {
        this.id = "inter_" + Generator.generateInterIdNumber();
        this.DOI = DOI;
        this.leadId = leadId;
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

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    /*public Lead getLeadRef() {
        return lead;
    }

    public void setLeadRef(Lead lead) {
        this.lead = lead;
    }*/

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
