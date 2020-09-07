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
        }
        System.out.println("Choose an option from the option list below by typing the number associate with it(1-5): ");
        System.out.println("1. View all Lead details: ");
        System.out.println("2. Adding a new Lead: ");
        System.out.println("3. Update a Lead : ");
        System.out.println("4. Delete a lead : ");
        System.out.println("5. Quit the system : ");
        System.out.println("================================================================================================ ");
        System.out.print("Your option is: ");
        String option = sys_in.nextLine();
        if (option.length()==1){
            switch (option){
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
                    InputGetter.DeleteLead();
                    break;
                case "5":
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
    private static void ViewLeadAll(){
        ArrayList <Lead> ListOfLead = CSVManager.getInstance().getLeadAll();
        for (int i =0;i < ListOfLead.size();i++){
            Lead lead = ListOfLead.get(i);
            System.out.println("ID: " + lead.getId());
            System.out.println("Name : " + lead.getName());
            System.out.println("Email : " + lead.getEmail());
            System.out.println("PhoneNo : " + lead.getPhone());
            System.out.println("DOB: " + lead.getDOBString());
            System.out.println("Gender: " + lead.getGender());
            System.out.println("==========================");
        }
        InputGetter.Continue();
    }

}
