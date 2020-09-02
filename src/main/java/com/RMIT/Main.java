package com.RMIT;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	    // call functions here
        CSVManager csvmanager = CSVManager.getInstance();
        csvmanager.print();
        /*csvmanager.addFile();
        Lead lead1 = new Lead("Minh", Converter.StrToDate("2001-08-124"), true, "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu");
        csvmanager.addLead(lead1);
        Lead lead2 = new Lead("Kha", Converter.StrToDate("2001-01-17"), false, "0702599935", "buithuykha@gmail.com", "312 Ly Thuong Kiet");
        csvmanager.addLead(lead2);*/
        //csvmanager.updateLead("lead_002", "Kha No.1 Hehe", Converter.StrToDate("2001-01-17"), false, "0702599935", "kittybui@gmail.com", "312 Ly Thuong Kiet");
        //csvmanager.deleteLead("lead_001");
        /*ArrayList<Lead> myLead = csvmanager.getLeadAll();
        for(Lead lead: myLead) {
            System.out.println("ID: " + lead.getId());
            System.out.println("Name : " + lead.getName());
            System.out.println("Email : " + lead.getEmail());
            System.out.println("PhoneNo : " + lead.getPhone());
            System.out.println("DOB: " + lead.getDOBString());
            System.out.println("Gender: " + lead.getGender());
            System.out.println("==========================");
        }*/
        //System.out.println(csvmanager.getLastLeadId());
        //System.out.println(lead2.getId());
    }

}
