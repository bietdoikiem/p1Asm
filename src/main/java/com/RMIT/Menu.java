package com.RMIT;

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
        System.out.print("Lead Date of Birth : ");
        String Dob = sys_in.nextLine();
        System.out.println(" ");
        Date DOB = Converter.StrToDate(Dob);

        // loop until got the right Date of birth.
        while(DOB == null){
            System.out.println("Invalid input for date of birth \n you should retype the date as one of these form " +
                    "\n yyyy/MM/dd" + "\n yyyy-MM-dd" + "\n MM dd, yyyy" +"\n E, MM dd yyyy"+ "\n E, MM dd yyyy HH:mm:ss" +
                    "\n yyyy-MM-dd HH:mm:ss" + "\n dd/MM/yyyy" + "\n dd-MM-yyyy");
            System.out.print("Lead Date of Birth : ");
            Dob = sys_in.nextLine();
            System.out.println(" ");
            DOB = Converter.StrToDate(Dob);
        }
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
