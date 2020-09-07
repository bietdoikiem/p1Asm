package com.RMIT;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.lang.*;

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
    public void MainMenu(int input){
        int i = input;
        if (i==0){
            System.out.println("Welcome to CRM program");
        }
        System.out.println("Choose an option from the option list below by typing the number associate with it(1-3): ");
        System.out.println("1. Execute the lead manager function: ");
        System.out.println("2. Execute the Inter manager function: ");
        System.out.println("3. View the reports : ");
        System.out.println("4. Quit the systems: ");
        System.out.println("================================================================================================ ");
        System.out.print("Your option is: ");
        String option = sys_in.nextLine();
        if (option.length()==1){
            switch (option){
                case "1":
                    MenuLead.getInstance().MainMenu(0);
                    break;
                case "2":
                    MenuInter.getInstance().MainMenu(0);
                    break;
                case "3":
                    MenuReport.getInstance().MainMenu(0);
                    break;
                case "4":
                    System.out.println("See you later, bye");
                    System.exit(0);
                    break;
            }
        }else {
            i++;
            System.out.println("Your input option is invalid");
            Menu.getInstance().MainMenu(i);
        }
    }
}
