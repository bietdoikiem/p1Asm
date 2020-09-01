package com.RMIT;


public class Main {

    public static void main(String[] args) {

        String mail = "minhlucky2408@gmail.com";
//          String address = "23b  ";
//        String phone = "0947970106";
//        String phone2 = "0934717924";
        System.out.println(Validator.getInstance().get_email_validated(mail)[0]);
//        Validator.getInstance().get_phone(phone2);
//        boolean a = Validator.getInstance().not_overlap_data(phone2,"phone");
//        System.out.println(a);

	    // call functions here
//        CSVManager csvmanager = CSVManager.getInstance();
//        csvmanager.print();
        //Lead lead1 = new Lead("Minh", Converter.StrToDate("2001-08-24"), true, "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu");
        //csvmanager.writeFile();
        //csvmanager.writeLead();
//        csvmanager.readLead();
//        csvmanager.readLastId();
//        CSVManager csvmanager = CSVManager.getInstance();
//        csvmanager.print();
//        Lead lead2 = new Lead("Minh", Converter.StrToDate("2001-08-24"), true, "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu");
        //csvmanager.writeFile();
        //csvmanager.writeLead();
        //csvmanager.getLeadAll();
//        System.out.println(lead2.getId());
    }

}
