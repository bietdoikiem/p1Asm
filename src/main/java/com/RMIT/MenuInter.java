package com.RMIT;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuInter extends Menu{
    private static MenuInter single_instance = null; // make this class singleton to be easier to manage
    private static Scanner sys_in = new Scanner(System.in);
    public MenuInter(){}
    public static Menu getInstance() {
        if (single_instance == null) {
            single_instance = new MenuInter();
        }
        return single_instance;
    }
    public void MainMenu(int input){
        int i = input;
        if (i==0){
            System.out.println("------Inter Manager Page------");
        }
        System.out.println("Choose an option from the option list below by typing the number associate with it(1-5): ");
        System.out.println("1. View all Inter details: ");
        System.out.println("2. Adding a new Interaction: ");
        System.out.println("3. Update an Interaction : ");
        System.out.println("4. Delete an Interaction : ");
        System.out.println("0. Quit the system : ");
        System.out.println("================================================================================================ ");
        System.out.print("Your option is: ");
        String option = sys_in.nextLine();
        if (option.length()==1){
            switch (option){
                case "1":
                    MenuInter.ViewInterAll();
                    break;
                case "2":
                    InputGetter.InputInter();
                    break;
                case "3":
                    InputGetter.updateInter();
                    break;
                case "4":
                    InputGetter.DeleteInter();
                    break;
                case "0":
                    System.out.println("See you later, bye");
                    System.exit(0);
                    break;
            }
        }else {
            i++;
            System.out.println("Your input option is invalid");
            MenuInter.getInstance().MainMenu(i);
        }
    }
    private static void ViewInterAll(){
        ArrayList<Interaction> ListOfInter = CSVManager.getInstance().getInterAll();
        for (int i =0;i < ListOfInter.size();i++){
            Interaction Inter = ListOfInter.get(i);
            System.out.println("Interaction ID :" + Inter.getId());
            System.out.println("Lead ID: " + Inter.getLeadId());
            System.out.println("Mean : " + Inter.getMean());
            System.out.println("Date of interaction : " + Converter.DateToStr(Inter.getDOI()));
            System.out.println("Potential : " + Inter.getPotential());
            System.out.println("==========================");
        }
        InputGetter.Continue();
    }
}
