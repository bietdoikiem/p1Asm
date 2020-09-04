package com.RMIT;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private static Menu single_instance = null; // make this class singleton to be easier to manage
    private static Scanner sys_in = new Scanner(System.in);
    public Menu(){}
    public static Menu getInstance() {
        if (single_instance == null) {
            single_instance = new Menu();
        }
        return single_instance;
    }


    // Design begining phase here

    // design input_lead phase here.
    public void Input_lead(){
        String Name = input_name();
        String phone = input_phone();
        Date DOB = input_DOB();
        boolean gender = input_gend();
        String email = input_email();
        String address = input_address();
        Lead new_lead = new Lead(Name,DOB,gender,phone,email,address);
//        new_lead.setEmail(email);
//        new_lead.setName(Name);
//        new_lead.setGender(gender);
//        new_lead.setAddress(address);
//        new_lead.setDOB(DOB);
//        new_lead.setPhone(phone);

        CSVManager.getInstance().addLead(new_lead);
        Continue();

    }

    // Phase for Update Lead
    public void updateLead(){
        // get the id of lead need to update
        System.out.println("Type in ID of list to update");
        String Id = sys_in.nextLine();
        Lead Lead_c = CSVManager.getInstance().getLead(Id);
        if (Lead_c == null){
            System.out.println("ID not exists ");
        }else {
            String Name = Lead_c.getName();
            String phone = Lead_c.getPhone();
            String email = Lead_c.getEmail();
            String address = Lead_c.getAddress();
            boolean gender = Lead_c.getGender();
            String str_gend = "male";
            if (gender == false){
                str_gend = "female";
            }
            Date Dob = Lead_c.getDOB();
            // print the guidance for input the features to updates
            System.out.println("let choose features needed to update ");
            System.out.println("Type 1 for Name "+Name);
            System.out.println("Type 2 for Date of birth "+ Converter.DateToStr(Dob));
            System.out.println("Type 3 for Phone "+ phone);
            System.out.println("Type 4 for email "+ email);
            System.out.println("Type 5 for address "+ address);
            System.out.println("Type 6 for gender " +str_gend );
            System.out.print(" Type your order here: ");
            System.out.println(" ");
            String orderIn = sys_in.nextLine(); // get the list of features to updates

            // get the validated list of order
            String[] order_validated = Validator.getInstance().GetValidatedLeadOrder(orderIn);
            if (order_validated[0].equals("false")){ // if the order is invalid print message
                System.out.println(order_validated[1]);

            }else { // the order is valid
                for (int i = 0;i < orderIn.length();i++){
                    String orderCase = String.valueOf(orderIn.charAt(i)) ;
                    switch (orderCase){
                        case "1" :
                            Name = input_name();
                            break;
                        case "2":
                            Dob = input_DOB();
                            break;
                        case "3":
                            phone = input_phone();
                            break;
                        case "4":
                            email = input_email();
                            break;
                        case "5":
                            address = input_address();
                            break;
                        case "6":
                            gender = input_gend();
                            break;
                    }
                }
                CSVManager.getInstance().updateLead(Id,Name,Dob,gender,phone,email,address);
            }

        }
        Continue();


//        System.out.println();
//        System.out.println();
    }

    public void DeleteLead(){
        System.out.println("enter the id of lead to delete");
        String Id = sys_in.nextLine();
        CSVManager.getInstance().deleteLead(Id);
        Continue();


    }

    private static String input_name(){
        System.out.println("New lead's information");
        System.out.print("Lead Name : ");
        String lead_name = sys_in.nextLine(); // get the leadname
        System.out.println("\n");
        return lead_name;
    }

    private static Date input_DOB(){
        // first time input date of birth
        // Validate input day
        boolean dayValid = false;
        String day = null;
        while (!dayValid){ // get and validate the input of day
            System.out.println("Lead Date of Birth by input these information : ");
            System.out.print("Day : ");
            day = sys_in.nextLine();
            boolean Valid1 = false;
            boolean Valid2 = false;
            for (int i = 0; i<day.length();i++){
                if (day.charAt(i) < '9' & day.charAt(i) > '1') { // if the character of input are between 1-9
                    Valid1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Valid1 = false;
                    break;
                }
            }
            if (day.length() > 2 & Integer.parseInt(day) > 31 & Integer.parseInt(day) != 0 ){
                System.out.println("Your Day input is invalid, the input should be an integer smaller than 32");
            }else {
                Valid2 = true;
            }
            dayValid = Valid1&Valid2;
        }


        String pday = null;
        if(day.length() == 1){
            pday = "0"+day;
        }

        // Validate month input
        boolean monValid = false;
        String mon = null;
        while (!monValid){ // get and validate the input of day
            System.out.println("Lead Date of Birth by input these information : ");
            System.out.print("Day : ");
            day = sys_in.nextLine();
            boolean Validmon1 = false;
            boolean Validmon2 = false;
            for (int i = 0; i<day.length();i++){
                if (day.charAt(i) <= '9' & day.charAt(i) >= '0') { // if the character of input are between 1-9
                    Valid1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validmon1 = false;
                    break;
                }
            }
            if (day.length() > 2 & Integer.parseInt(day) > 12 & Integer.parseInt(day) != 0 ){
                System.out.println("Your month input is invalid, the input should be an integer smaller than 13");
            }else {
                Validmon2 = true;
            }
            monValid = Validmon1&Validmon2;
        }


        String pmon = null;
        if(mon.length() == 1){
            pmon = "0"+day;
        }


        // Validate year input

        int curYear = Calendar.getInstance().get(Calendar.YEAR); // get the current year
        int borderDownYear = curYear - 13; // the youngest customer should be at age of 12
        int borderUpYear = curYear - 105; // We want to make sure the customer is alive
        boolean yearValid = false;
        String year = null;
        while (!yearValid){ // get and validate the input of day
            System.out.println("Lead Date of Birth by input these information : ");
            System.out.print("Day : ");
            year = sys_in.nextLine();
            boolean Validy1 = false;
            boolean Validy2 = false;
            for (int i = 0; i<day.length();i++){
                if (year.charAt(i) < '9' & year.charAt(i) > '1') { // if the character of input are between 1-9
                    Validy1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validy1 = false;
                    break;
                }
            }
            // check if the customer in the valid range of year 13-105 and the validity of the input length
            if (year.length() == 4 & Integer.parseInt(year) > borderUpYear & Integer.parseInt(year) < borderDownYear){
                Validy2 = true;
            }else {
                System.out.println("invalid year input");
                Validy2 = false;
            }
            dayValid = Validy1&Validy2;
        }


        String DateParse = year+"-"+mon+"-"+day;
        Date DOB = Converter.DateToStr(DateParse);


        return DOB;
    }

    private static boolean input_gend(){
        // input gender section
        System.out.print("Lead Gender(male|female only): ");
        String gender = null;
        boolean gend;
        // loop until got the right input
        while (true){
            gender = sys_in.nextLine().toLowerCase();
            System.out.println(gender);
            System.out.println(" ");
            if  (gender.equals("male")){
                gend = true;
                System.out.println("it worked");
                break;
            }
            if (gender.equals("female")){
                gend = false;
                break;
            }
            else {
                System.out.println("Wrong input, please type again");
                System.out.print("Lead Gender(male|female only): ");
            }
        }
        return gend;
    }

    private static String input_phone(){
        System.out.print("Lead Phone : ");
        String in_phone = sys_in.nextLine();
        System.out.println(" ");
        while (true){
            if (Validator.getInstance().get_phone_validated(in_phone)[1] == "true"){
                return in_phone;
            }
            else {
                System.out.println("Invalid phone input, please type lead phone again\n the phonenuber should be combined from 0-9 interger and have length between 9 and 11");
                System.out.print("Lead Phone : ");
                in_phone = sys_in.nextLine();

            }
        }
    }

    private static String input_email(){
        System.out.print("Lead email : ");
        String in_mail = sys_in.nextLine();
        System.out.println(" ");
        while (true){
            if (Validator.getInstance().get_email_validated(in_mail)[1] == "true"){
                return in_mail;
            }
            else {
                System.out.println("Invalid email input, please type lead phone again");
                System.out.print("Lead email : ");
                in_mail = sys_in.nextLine();

            }
        }
    }

    private static String input_address(){
        System.out.print("Lead adđress : ");
        String in_address = sys_in.nextLine();
        System.out.println(" ");
        while (true){
            if (Validator.getInstance().get_validated_address(in_address)[1] == "true"){
                return in_address;
            }
            else {
                System.out.println("Invalid address input, please type address again");
                System.out.print("Lead address : ");
                in_address = sys_in.nextLine();

            }
        }
    }

    private static void Continue(){
        System.out.println("Do you want to continue use the system ? , type yes for continue type no for quitting");
        String bool = sys_in.nextLine().toLowerCase();
        if (bool.equals("yes")) {
            System.out.println("Please choose a function you want to continue, one character input only:");
            System.out.println("Type 1 for adding a new leads");
            System.out.println("Type 2 for update an existed leads");
            System.out.println("Type 3 for delete an leads");
            String order = sys_in.nextLine();
            if (order.length() == 1){
                switch (order){
                    case "1":
                        Menu.getInstance().Input_lead();
                        break;
                    case "2":
                        Menu.getInstance().updateLead();
                        break;
                    case "3":
                        Menu.getInstance().DeleteLead();
                        break;
                }
            }else {
                System.out.println("The order have invalid length, your input should only contains a numbers.");
                Continue();

            }
        }else {
            if (bool.equals("no")){
                System.exit(0);
                System.out.println("Quited...");
            } else {
                System.out.println("Your input is invalid, yes or no only, please type again");
                Continue();
            }

        }
    }




    // design update/delete_lead phase here

    // design input_interaction phase here.

    // design delete/update interaction phase here.

}
