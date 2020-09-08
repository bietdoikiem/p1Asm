package com.RMIT;

import java.util.Date;
import java.util.Scanner;

public class MenuReport extends Menu{
    private static MenuReport single_instance = null; // make this class singleton to be easier to manage
    private static Scanner sys_in = new Scanner(System.in);
    public MenuReport(){}
    public static MenuReport getInstance() {
        if (single_instance == null) {
            single_instance = new MenuReport();
        }
        return single_instance;
    }
    public void MainMenu(int input){ // the menu to access report
        int i = input;
        if (i==0){
            System.out.println("------Report Manager Page------");
        }
        System.out.println("Choose an option from the option list below by typing the number associate with it(1-4): "); // display the option to the user
        System.out.println("1. View Lead by age report : ");
        System.out.println("2. View Interaction by potential report : ");
        System.out.println("3. View Interaction by time : ");
        System.out.println("0. Quit the systems : ");
        System.out.println("================================================================================================ ");
        System.out.print("Your option is: ");
        String option = sys_in.nextLine();
        if (option.length()==1){
            switch (option){
                case "1":
                    Generator.generateLeadReport(); // generate report of Lead by age
                    break;
                case "2":
//                    handle day needed
                    PotentialReport();
                    break;
                case "3":
                    InteractionReport();
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
    private static void PotentialReport(){
        Date begin = InputGetter.input_DOI("begin"); // get the begin and end date from user
        Date end = InputGetter.input_DOI("end");
        Generator.generatePotentialReport(begin,end); // display the potential report
        InputGetter.Continue(); // let the user choose to continue or quit
    }
    private static void InteractionReport(){
        Date begin = InputGetter.input_DOI("begin"); // get the begin and end date from user
        Date end = InputGetter.input_DOI("end"); // display the Interaction report
        Generator.generateInteractionReport(begin,end);
        InputGetter.Continue(); // let the user choose to continue or quit
    }


}
