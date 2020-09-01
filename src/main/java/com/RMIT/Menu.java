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

        String phone = null;
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
            System.out.println(" ");
            if  (gender == "male"){
                gend = true;
                break;
            }
            if (gender == "female"){
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
        System.out.print("Lead email : ");
        String in_address = sys_in.nextLine();
        System.out.println(" ");
        while (true){
            if (Validator.getInstance().get_email_validated(in_address)[1] == "true"){
                return in_address;
            }
            else {
                System.out.println("Invalid address input, please type address again");
                System.out.print("Lead address : ");
                in_address = sys_in.nextLine();

            }
        }
    }




    // design update/delete_lead phase here

    // design input_interaction phase here.

    // design delete/update interaction phase here.

}
