package com.RMIT;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    // Convert string input to date
    public static Date Str_Date(String input) throws ParseException {
        // listing the available date format
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter3=new SimpleDateFormat("MM dd, yyyy");
        SimpleDateFormat formatter4=new SimpleDateFormat("E, MM dd yyyy");
        SimpleDateFormat formatter5=new SimpleDateFormat("E, MM dd yyyy HH:mm:ss");
        SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // create array of format
        SimpleDateFormat[] formats = new SimpleDateFormat[6];
        formats[0] = formatter1;
        formats[1] = formatter2;
        formats[2] = formatter3;
        formats[3] = formatter4;
        formats[4] = formatter5;
        formats[5] = formatter6;
        Date data = null;
        Date data2 =null;
        // Iterate through Datr format, match input with the right one and convert it to Date
        for (int i=0; i<formats.length;i++){
            try{
                data2 = data;
                data = formats[i].parse(input);
            }catch (ParseException exception){
                data = data2;
            }
        }

        return data;


    }
}
