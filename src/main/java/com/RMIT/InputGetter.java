package com.RMIT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class InputGetter {
//    public static
    private static Scanner sys_in = new Scanner(System.in);
    public static void InputLead(){ // this function used for adding lead
    // getting lead information from user
    System.out.println("New lead's information");
    String Name = inputName();
    String phone = inputPhone();
    Date DOB = inputDOB();
    boolean gender = inputGend();
    String email = inputEmail();
    String address = inputAddress();
    // create and adding new leads
    Lead new_lead = new Lead(Name,DOB,gender,phone,email,address);
    CSVManager.getInstance().addLead(new_lead);
    Continue();

    // ask user to continue or quit

}

    // Phase for Update Lead
    public static void updateLead(){ // this function used for updating leads
        // get the id of lead need to update
        System.out.println("Type in ID of list to update");
        String Id = sys_in.nextLine();
        Lead Lead_c = CSVManager.getInstance().getLead(Id); // get the lead by ID
        if (Lead_c == null){
            System.out.println("ID not exists ");
        }else {
            // Making a deepcopy if the leadID is valid
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
                            Name = inputName();
                            break;
                        case "2":
                            Dob = inputDOB();
                            break;
                        case "3":
                            phone = inputPhone();
                            break;
                        case "4":
                            email = inputEmail();
                            break;
                        case "5":
                            address = inputAddress();
                            break;
                        case "6":
                            gender = inputGend();
                            break;
                    }
                }
                CSVManager.getInstance().updateLead(Id,Name,Dob,gender,phone,email,address);
            }
        }
        Continue();

    }

    public static void DeleteLead(){ // function to delete an lead by ID
        System.out.println("Enter the id of lead to delete"); // get the ID from user
        String Id = sys_in.nextLine();
        CSVManager.getInstance().deleteLead(Id);
        Continue();
    }

    public static String inputName(){ // Valid the name of the lead by make sure it only contains letter from a-z
        boolean NotContainInvalidChar = false;
        String lead_name = null;
        while (true){
            System.out.print("Lead Name : ");
            lead_name = sys_in.nextLine(); // get the leadname
            String[] listOfWord = lead_name.split(" ");
            String inputWords = String.join("",listOfWord); // convert the name from multiple words to a string
            System.out.println(inputWords);
            for(int i =0; i < inputWords.length();i++){ // check if the string converted from input above contain invalid word
                if (Character.isLetter(inputWords.charAt(i)) == false){
                    NotContainInvalidChar = false;
                    break;
                }else {
                    NotContainInvalidChar = true;
                }
            }
            if(NotContainInvalidChar == true){ // if the input is valid, breaks the loop
                break;
            }
        }
        System.out.println("\n");
        return lead_name;
    }

    public static Date inputDOB(){
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
                if (day.charAt(i) <= '9' & day.charAt(i) >= '0') { // if the character of input are between 1-9
                    Valid1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Valid1 = false;
                    break;
                }
            }
            if (day.length() > 2 & Integer.parseInt(day) > 31 & Integer.parseInt(day) != 0 ){ // the input should be maximum of 31 ad more than 0
                System.out.println("Your Day input is invalid, the input should be an integer smaller than 32");
            }else {
                Valid2 = true;
            }
            dayValid = Valid1&Valid2;
        }
        String pday = day;
        if(day.length() == 1){
            pday = "0"+day;
        }

        // Validate month input
        boolean monValid = false;
        String mon = null;
        while (!monValid){ // get and validate the input of mon
            System.out.print("Month : ");
            mon = sys_in.nextLine();
            boolean Validmon1 = false;
            boolean Validmon2 = false;
            int m =0;
            while(m < mon.length()){
                if (mon.charAt(m) <= '9' & mon.charAt(m) >= '0') { // if the character of input are between 1-9
                    Validmon1 = true;
                }else { // if the input is valid
                    System.out.println("The input contains invalid characters");
                    Validmon1 = false;
                    break;
                }
                m ++;
            }
            if (mon.length() > 2 | Integer.parseInt(mon) > 12 | Integer.parseInt(mon) == 0 ){ // the range value of a month is 1-12
                System.out.println("Your month input is invalid, the input should be an integer smaller than 13");
            }else {
                Validmon2 = true;
            }
            monValid = Validmon1&Validmon2;
        }


        String pmon = mon;
        if(mon.length() == 1){
            pmon = "0"+mon;
        }


        // Validate year input

        int curYear = Calendar.getInstance().get(Calendar.YEAR); // get the current year
        int borderDownYear = curYear - 5; // the youngest customer should be at age of 5
        int borderUpYear = curYear - 105; // We want to make sure the customer is alive
        boolean yearValid = false;
        String year = null;
        while (!yearValid){ // get and validate the input of day
            System.out.print("Year : ");
            year = sys_in.nextLine();
            boolean Validy1 = false;
            boolean Validy2 = false;
            int y =0;
            while (y<year.length()){
                if (year.charAt(y) <= '9' & year.charAt(y) >= '0') { // if the character of input are between 1-9
                    Validy1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validy1 = false;
                    break;
                }
                y ++;
            }
            // check if the customer in the valid range of age: 5-105 and the validity of the input length
            if (year.length() == 4 & Integer.parseInt(year) > borderUpYear & Integer.parseInt(year) < borderDownYear){
                Validy2 = true;
            }else {
                System.out.println("invalid year input");
                Validy2 = false;
            }
            yearValid = Validy1&Validy2;
        }


        String DateParse = pday+"/"+pmon+"/"+year; // combined valid day,month, year to a string for parsing
        System.out.println(DateParse);
        Date DOB = Converter.StrToDate(DateParse);



        return DOB;
    }

    public static boolean inputGend(){
        // input gender section
        System.out.print("Lead Gender(male|female only): ");
        String gender = null;
        boolean gend;
        // loop until got the right input, which is male and female
        while (true){
            gender = sys_in.nextLine().toLowerCase();
            System.out.println(gender);
            System.out.println(" ");
            if  (gender.equals("male")){ // if input is male
                gend = true;
                System.out.println("it worked");
                break; // break the loop if the input match one of two valid condition
            }
            if (gender.equals("female")){ // if input is female
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

    public static String inputPhone(){ // get the phone after validated
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

    public static String inputEmail(){ // get the email from user and validate it
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

    public static String inputAddress(){ // get and validate address from users
        System.out.print("Lead adđress : ");
        String in_address = sys_in.nextLine();
        System.out.println(" "); // split the input to check if it follow the form of <number> <street name> or not
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

    public static void InputInter(){ // input inter
        // geting information from user
        System.out.println("You are at create interaction phase");
        System.out.println("Type in the Lead_Id of this interaction");
        String Id = sys_in.nextLine();
        String mean = getMean();
        String potential = getPotential();
        Date DOI = input_DOI();


        Interaction thisInteraction = new Interaction(DOI,Id,mean,potential); // create and add new interaction
        CSVManager.getInstance().addInter(thisInteraction);
        Continue();

    }

//    private

    public static String getMean(){ // get the mean from user and validate it
        String[] ValidatedMean = new String[2];
        ValidatedMean[0] = "false";
        String SecondStr = null;
        boolean NotValid = true;
        String firstStr = null;
        while (NotValid){
            System.out.println("Please input the mean of this interaction,\n Type new or new social network for adding new type of means ");
            firstStr = sys_in.nextLine();
            switch (firstStr.toLowerCase()){
                case "new":
                    System.out.print("Please input this new type of means ");
                    System.out.println(" ");
                    SecondStr = sys_in .nextLine();
                    ValidatedMean = Validator.getInstance().getMean(SecondStr,"new");
                    break;
                case "new social network":
                    System.out.print("Please input this new type of means ");
                    System.out.println(" ");
                    SecondStr = sys_in .nextLine();
                    ValidatedMean = Validator.getInstance().getMean(SecondStr,"new social network");
                    break;
                default:
                    ValidatedMean = Validator.getInstance().getMean(firstStr);
                    // the message of invalid input cotent only pop up if it's not overlap new type of input ;
                    if(!Boolean.parseBoolean(ValidatedMean[0]) & !firstStr.equals("new") & !firstStr.equals("new social network")){
                        System.out.println(ValidatedMean[1]);
                    }
                    break;
            }
            NotValid = !Boolean.parseBoolean(ValidatedMean[0]);
        }
        return ValidatedMean[1];
    }

    public static String getPotential(){ // get the potential from user and return the validated input
        String[] ValidatedPotential = new String[2];
        ValidatedPotential[0] = "false";
        boolean NotValid = true;
        String Potential;
        while (NotValid){
            System.out.println("Please input the potential by follow the guides below: ");
            System.out.println("Type positive or + for positive potential"); // we allow user to use mathematic operator for convenience
            System.out.println("Type negative or - for negative potential ");
            System.out.println("Type neural or = for neural potential");
            System.out.println(" ");
            Potential = sys_in.nextLine();
            ValidatedPotential = Validator.getInstance().getPotential(Potential);
            NotValid = !Boolean.parseBoolean(ValidatedPotential[0]); // if the input is valid the loop will be broke
        }
        return ValidatedPotential[1];

    }

    public static Date input_DOI(){
        // first time input date of interaction
        // Validate input day
        boolean dayValid = false;
        String day = null;
        while (!dayValid){ // get and validate the input of day
            System.out.println("Please complete information about Day of Interaction by input these information : ");
            System.out.print("Day : ");
            day = sys_in.nextLine();
            boolean Valid1 = false;
            boolean Valid2 = false;
            for (int i = 0; i<day.length();i++){
                if (day.charAt(i) <= '9' & day.charAt(i) >= '0') { // if the character of input are between 1-9
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


        String pday = day;
        if(day.length() == 1){
            pday = "0"+day;
        }

        // Validate month input
        boolean monValid = false;
        String mon = null;
        while (!monValid){ // get and validate the input of day
            System.out.print("Month : ");
            mon = sys_in.nextLine();
            boolean Validmon1 = false;
            boolean Validmon2 = false;
            for (int i = 0; i<day.length();i++){
                if (mon.charAt(i) <= '9' & mon.charAt(i) >= '0') { // if the character of input are between 1-9
                    Validmon1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validmon1 = false;
                    break;
                }
            }
            if (mon.length() > 2 | Integer.parseInt(mon) > 12 | Integer.parseInt(mon) == 0 ){
                System.out.println("Your month input is invalid, the input should be an integer smaller than 13");
            }else {
                Validmon2 = true;
            }
            monValid = Validmon1&Validmon2;
        }


        String pmon = mon;
        if(mon.length() == 1){
            pmon = "0"+mon;
        }


        // Validate year input

        int curYear = Calendar.getInstance().get(Calendar.YEAR); // get the current year
        int borderDownYear = curYear - 5; // the youngest customer should be at age of 5
        int borderUpYear = curYear - 105; // We want to make sure the customer is alive
        boolean yearValid = false;
        String year = null;
        while (!yearValid){ // get and validate the input of day
            System.out.print("Year : ");
            year = sys_in.nextLine();
            boolean Validy1 = false;
            boolean Validy2 = false;
            for (int i = 0; i<day.length();i++){
                if (year.charAt(i) <= '9' & year.charAt(i) >= '0') { // if the character of input are between 1-9
                    Validy1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validy1 = false;
                    break;
                }
            }
            // In real life, the interaction data should be updated in days since the interaction happened
            // so that we validate that the year should be as closed as possible to the current years.
            if (year.length() == 4 & Math.abs(Integer.parseInt(year) - curYear) <=1 & Integer.parseInt(year) <= curYear ){
                Validy2 = true;
            }else {
                System.out.println("invalid year input, check whether your year input in the future or more than a year from the current time ");
                Validy2 = false;
            }
            yearValid = Validy1&Validy2; // the year only valid if satisfy the condition of having valid character and in the valid time range
        }

        String DateParse = pday+"/"+pmon+"/"+year;
        System.out.println(DateParse);
        Date DOI = Converter.StrToDate(DateParse);
        boolean InFuture = true;
        int i = 0;
        Date current = Date.from(LocalDate.now().atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
        while (InFuture){ // check if the input is in the future or not
            if(i > 0){
                System.out.println("your input date is in the future, please type the date of interaction again");
                DOI = input_DOI();
            }
            if (!DOI.after(current)){
                InFuture = false;
                break;
            }
            i++;
        }
        return DOI;
    }

    public static void updateInter(){ // this function used for updating leads
        // get the id of lead need to update
        System.out.println("Type in ID of Inter to update");
        String Id = sys_in.nextLine();
        Interaction Inter_c = CSVManager.getInstance().getInter(Id); // get the lead by ID
        if (Inter_c == null){
            System.out.println("ID not exists ");
        }else {
            // Making a deepcopy of the original interaction if the InterID is valid
            String potential = Inter_c.getPotential();
            String mean = Inter_c.getMean();
            String LeadId = Inter_c.getLeadId();
            Date DOI = Inter_c.getDOI();
            // print the guidance for input the features to updates
            System.out.println("let choose features needed to update ");
            System.out.println("Type 1 for LeadID "+LeadId);
            System.out.println("Type 2 for Date of Interaction "+ Converter.DateToStr(DOI));
            System.out.println("Type 3 for Mean "+ mean);
            System.out.println("Type 4 for potential "+ potential);
            System.out.print(" Type your order here: ");
            System.out.println(" ");
            String orderIn = sys_in.nextLine(); // get the list of features to updates

            // get the validated list of order
            String[] order_validated = Validator.getInstance().GetValidatedInterOrder(orderIn);
            if (order_validated[0].equals("false")){ // if the order is invalid print message
                System.out.println(order_validated[1]);

            }else { // the order is valid
                for (int i = 0;i < orderIn.length();i++){
                    String orderCase = String.valueOf(orderIn.charAt(i)) ;
                    switch (orderCase){
                        case "1" :
                            LeadId = getLeadId();
                            break;
                        case "2":
                            DOI = input_DOI();
                            break;
                        case "3":
                            mean = getMean();
                            break;
                        case "4":
                            potential = getPotential();
                            break;
                    }
                }
                CSVManager.getInstance().updateInter(Id,DOI,LeadId,mean,potential);// update the interaction
            }

        }
        Continue();


    }

    public static String getLeadId(){
        boolean Valid = false;
        String LeadID = null;
        String[] ValidatedID = new String[2];
        while (!Valid){
            System.out.println("please input new leadId");
            LeadID = sys_in.nextLine();
            ValidatedID = Validator.getInstance().get_validated_leadid(LeadID);
            if(Boolean.parseBoolean(ValidatedID[1])){
                break;
            }
            else{
                System.out.println("the LeadID is invalid");
            }

        }
        return LeadID;
    }

    public static void DeleteInter(){
        System.out.println("please type in the interID to delete");
        String InterID = sys_in.nextLine();
        CSVManager.getInstance().deleteInter(InterID);
        Continue();

    }

    private static void Continue(){
        System.out.println("Do you want to continue use the system ? , type yes for continue type no for quitting");
        String bool = sys_in.nextLine().toLowerCase();
        if (bool.equals("yes")) {
            System.out.println("Please choose a function you want to continue, one character input only:");
            System.out.println("Type 1 for go back to the Main menu");
            System.out.println("Type 2 for go to the Lead Manager Page");
            System.out.println("Type 3 for go to the Interaction Manager Page");
            System.out.println("Type 4 for go to the Report Manager Page");
            System.out.println("Type 0 quit");
            String order = sys_in.nextLine();
            if (order.length() == 1){ // using switch to activate a function at a time when user chose the function to activate
                switch (order){
                    case "1":
                        Menu.MainMenu(0);
                        break;
                    case "2":
                        MenuLead.MainMenu(0);
                        break;
                    case "3":
                        MenuInter.MainMenu(0);
                        break;
                    case "4":
                        MenuReport.MainMenu(0);
                        break;
                    case "0":
                        System.out.println("Thanks for your work,bye");
                        System.exit(0);
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

}
