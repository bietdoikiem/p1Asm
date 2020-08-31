package com.RMIT;


public class Main {

    public static void main(String[] args) {

        String mail = "Dat@rmit.edu";


        boolean a = Validator.getInstance().validate_email(mail);
        System.out.println(a);

	    // call functions here
//        CSVManager csvmanager = CSVManager.getInstance();
//        csvmanager.print();
        //Lead lead1 = new Lead("Minh", Converter.StrToDate("2001-08-24"), true, "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu");
        //csvmanager.writeFile();
        //csvmanager.writeLead();
//        csvmanager.readLead();
//        csvmanager.readLastId();
    }
}
