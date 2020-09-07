package com.RMIT;

import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

    public static void main(String[] args) {
        Menu.getInstance().MainMenu(0);
        // call functions here
        CSVManager csvmanager = CSVManager.getInstance();
        csvmanager.print();
//        csvmanager.addFile("lead");
//        Lead lead1 = new Lead("Minh", Converter.StrToDate("2001-08-24"), true, "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu");
//        csvmanager.addLead(lead1);
//        Lead lead2 = new Lead("Kha", Converter.StrToDate("2001-01-17"), false, "0702599935", "buithuykha@gmail.com", "352 Ly Thuong Kiet");
//        csvmanager.addLead(lead2);
//        Lead lead3 = new Lead("Dat", Converter.StrToDate("2000-09-09"), true, "0909090909", "ngodat@gmail.com", "Q.7");
//        csvmanager.addLead(lead3);
        //csvmanager.deleteLead("lead_004");
        //csvmanager.addFile("inter");
//        Interaction inter1 = new Interaction(Converter.StrToDate("2020-08-01"), "lead_001", "email", "neutral");
//        csvmanager.addInter(inter1);
//        Interaction inter2 = new Interaction(Converter.StrToDate("2020-08-03"), "lead_002", "facebook", "positive");
//        csvmanager.addInter(inter2);
//        Interaction inter3 = new Interaction(Converter.StrToDate("2020-08-04"), "lead_003", "facebook", "negative");
//        csvmanager.addInter(inter3);
//        csvmanager.deleteInter("inter_001");
//        csvmanager.deleteInter("inter_002");
//        csvmanager.deleteInter("inter_003");
        //csvmanager.getLead("lead_004");
/*        for(Lead lead: csvmanager.getLeadAll()) {
            System.out.println("Lead id is: " + lead.getId());
        }

        for(Interaction inter: csvmanager.getInterAll()) {
            System.out.println("Inter Id is: " + inter.getId() + " with potential: " + inter.getPotential());
        }*/
        Generator.generatePotentialReport(Converter.StrToDate("2020-03-01"), Converter.StrToDate("2020-08-04"));
        Generator.generateInteractionReport(Converter.StrToDate("2020-03-1"), Converter.StrToDate("2020-08-04"));

    }


}
