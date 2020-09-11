package com.RMIT;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuLead extends Menu{
    private static MenuLead single_instance = null; // make this class singleton to be easier to manage
    private static Scanner sys_in = new Scanner(System.in);
    public MenuLead(){}
    public static MenuLead getInstance() {
        if (single_instance == null) {
            single_instance = new MenuLead();
        }
        return single_instance;
    }
    public void MainMenu(int input){
        int i = input;
        if (i==0){
            System.out.println("------Lead Manager Page------");
        } // Display option for user
        System.out.println("Choose an option from the option list below by typing the number associate with it(1-5): ");
        System.out.println("1. View all Lead details: ");
        System.out.println("2. Adding a new Lead: ");
        System.out.println("3. Update a Lead : ");
        System.out.println("4. Delete a lead : ");
        System.out.println("0. Quit the system : ");
        System.out.println("================================================================================================ ");
        System.out.print("Your option is: ");
        String option = sys_in.nextLine();
        if (option.length()==1){
            switch (option){ // activate function associate with input
                case "1":
                    MenuLead.ViewLeadAll();
                    break;
                case "2":
                    InputGetter.InputLead();
                    break;
                case "3":
                    InputGetter.updateLead();
                    break;
                case "4":
                    InputGetter.confirmDeleteLead();
                    break;
                case "0":
                    System.out.println("See you later, bye");
                    System.exit(0);
                    break;
            }
        }else {
            i++;
            System.out.println("Your input option is invalid");
            MenuLead.getInstance().MainMenu(i);
        }

    }
    private static void ViewLeadAll(){ // view all the lead's information in details
        ArrayList <Lead> ListOfLead = CSVManager.getInstance().getLeadAll(); // get and loop through the array of lead
        Generator.generateLoading(50);
        //set up columns table format for displaying data
        System.out.println("======================================================================================List of Leads=========================================================================================");
        System.out.format("%-20s", "ID");
        System.out.format("%-35s", "Name");
        System.out.format("%-35s", "Date of birth");
        System.out.format("%-20s", "Gender");
        System.out.format("%-20s", "PhoneNo");
        System.out.format("%-40s", "Email");
        System.out.format("%-35s", "Address");
        System.out.println(" ");
        for (int i =0;i < ListOfLead.size();i++){
            Lead lead = ListOfLead.get(i);
            System.out.format("%-20s", lead.getId());
            System.out.format("%-35s", lead.getName());
            System.out.format("%-35s", lead.getDOBString());
            System.out.format("%-20s", Converter.BooleanToGender(lead.getGender()));
            System.out.format("%-20s", lead.getPhone());
            System.out.format("%-40s",  lead.getEmail());
            System.out.format("%-35s", lead.getAddress());
            System.out.println(" ");
        }
        System.out.println("==============================================================================================================================================================================================");
        System.out.println(" ");
        InputGetter.Continue();
    }

}
