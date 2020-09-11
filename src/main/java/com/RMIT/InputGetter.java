package com.RMIT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class InputGetter {
//    public static
    private static Scanner sys_in = new Scanner(System.in);
    public static void InputLead(){ // this function used for adding lead
    // getting lead information from user
    System.out.println("New lead's information");
    String Name = inputName(); // getting all needed information of a list
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
                    switch (orderCase){ // get the new value of feature that will be updated
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

    public static void confirmDeleteLead(){ // function to delete an lead by ID
        System.out.println("Enter the id of lead to delete"); // get the ID from user
        String Id = sys_in.nextLine();
        ArrayList <Interaction> allInter = CSVManager.getInstance().getInterAll();
        ArrayList <Interaction> InterRelate = new ArrayList<Interaction>();
        String choice = null;
        for (int i = 0; i <allInter.size();i++){
            Interaction thisInter = allInter.get(i);
            if(thisInter.getLeadId().equals(Id)){
                InterRelate.add(thisInter);
            }
        }
        if(InterRelate.size()>0){ // if the lead have interaction relate to it, display all interaction in detailed
            System.out.println("If you delete the the lead with the ID you just input");
            System.out.println("The following Interaction will be deleted :");
            System.out.println("========================================List of Related Interactions===========================================");
            //set up columns table format for displaying data
            System.out.format("%-20s", "ID");
            System.out.format("%-30s", "Date of interaction");
            System.out.format("%-20s", "Lead's ID");
            System.out.format("%-30s", "Mean");
            System.out.format("%-20s", "Potential");
            System.out.println(" ");
            int index = 0;
            while (index < InterRelate.size()){
                Interaction Inter = InterRelate.get(index);
                System.out.format("%-20s", Inter.getId());
                System.out.format("%-30s", Inter.getDOIString());
                System.out.format("%-20s", Inter.getLeadId());
                System.out.format("%-30s", Inter.getMean());
                System.out.format("%-20s", Inter.getPotential());
                System.out.println(" ");
                index++;
            }
            System.out.println("===============================================================================================================");
            System.out.println(" ");
        }
        System.out.println("Are you sure you want to delete this Lead ? "); // instruct user to confirm deleting a lead
        System.out.println("1. Yes");
        System.out.println("2. No");
        choice = sys_in.nextLine();
        if(choice.equals("1")) {
            Generator.generateLoading(50);
            CSVManager.getInstance().deleteLead(Id);
        }
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
            int i = 0;
            while(i<inputWords.length()){ // check if the string converted from input above contain invalid word
                if (Character.isLetter(inputWords.charAt(i)) == false){
                    NotContainInvalidChar = false;
                    break;
                }else {
                    NotContainInvalidChar = true;
                }
                i++;
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
            int d =0;
            while (d < day.length()){
                if (day.charAt(d) <= '9' & day.charAt(d) >= '0') { // if the character of input are between 1-9
                    Valid1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Valid1 = false;
                    break;
                }
                d++;
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


        String DateParse = year+"/"+pmon+"/"+pday; // combined valid day,month, year to a string for parsing
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
                System.out.println("Invalid email input, please type the Email again");
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
        Date DOI = input_DOI("doi");


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
            System.out.println("Choose an option available from the option list below by typing the num ber associate with it ");
            System.out.println("1. Email");
            System.out.println("2. Face to face");
            System.out.println("3. Phone");
            System.out.println("4. Social media: Facebook");
            System.out.println("5. Social media: Twitter");
            System.out.println("6. Social media: Zalo");
            System.out.println("7. Social media: Instagram");
            System.out.println("8. Adding new means: ");
            System.out.println("9. Adding new means in the field of social media");
            System.out.println("====================================================================================================");
            System.out.print("Your choice is: ");
            firstStr = sys_in.nextLine();
            switch (firstStr.toLowerCase()){
                case "1":
                    ValidatedMean = Validator.getInstance().getMean("email");
                    break;
                case "2":
                    ValidatedMean = Validator.getInstance().getMean("face to face");
                    break;
                case "3":
                    ValidatedMean = Validator.getInstance().getMean("phone");
                    break;
                case "4":
                    ValidatedMean = Validator.getInstance().getMean("facebook");
                    break;
                case "5":
                    ValidatedMean = Validator.getInstance().getMean("twitter");
                    break;
                case "6":
                    ValidatedMean = Validator.getInstance().getMean("zalo");
                    break;
                case "7":
                    ValidatedMean = Validator.getInstance().getMean("instagram");
                    break;
                case "8":
                    System.out.println("please type this new type of mean:");
                    SecondStr = sys_in.nextLine();
                    ValidatedMean = Validator.getInstance().getMean(SecondStr,"new");
                    break;
                case "9":
                    ValidatedMean = Validator.getInstance().getMean(SecondStr,"new social network");
                    break;
                default:
                    try {
                        int k = Integer.parseInt(firstStr);
                        if (k>9 |  k<1){
                            System.out.println("Your input option is out of valid range, it should be an integer in range 1-9");
                            System.out.println("Let choose the option again");
                            ValidatedMean[0] = "false";
                        }
                    }catch (Exception exception){
                        System.out.println("Your input option may contain invalid character, please choose the option again");
                        ValidatedMean[0] = "false";
                    }

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
            System.out.println("Type neutral or = for neutral potential");
            System.out.println(" ");
            Potential = sys_in.nextLine();
            ValidatedPotential = Validator.getInstance().getPotential(Potential);
            NotValid = !Boolean.parseBoolean(ValidatedPotential[0]); // if the input is valid the loop will be broke
        }
        return ValidatedPotential[1];

    }

    public static Date input_DOI(String mode){
        // first time input date of interaction
        // Validate input day
        String inmode = mode.toLowerCase();
        boolean dayValid = false;
        String day = null;
        while (!dayValid){ // get and validate the input of day
            if(inmode.equals("doi")){
                System.out.println("Please complete information about Day of Interaction by input these information : ");
            }
            if(inmode.equals("begin") ){
                System.out.println("Please complete information about begin Day by input these information");
            }
            if(inmode.equals("end")){
                System.out.println("Please complete information about the end Day by input these information");
            }
            System.out.print("Day : ");
            day = sys_in.nextLine();
            boolean Valid1 = false;
            boolean Valid2 = false;
            int d = 0;
            while (d<day.length()){
                if (day.charAt(d) <= '9' & day.charAt(d) >= '0') { // if the character of input are between 1-9
                    Valid1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Valid1 = false;
                    break;
                }
                d++;
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
            int m = 0;
            while (m<mon.length() ){
                if (mon.charAt(m) <= '9' & mon.charAt(m) >= '0') { // if the character of input are between 1-9
                    Validmon1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validmon1 = false;
                    break;
                }
                m++;
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
            int y = 0;
            while (y<year.length()){
                if (year.charAt(y) <= '9' & year.charAt(y) >= '0') { // if the character of input are between 1-9
                    Validy1 = true;
                }else { // if the valid input
                    System.out.println("The input contains invalid characters");
                    Validy1 = false;
                    break;
                }
                y++;
            }
            // In real life, the interaction data should be updated in days since the interaction happened
            // so that we validate that the year should be as closed as possible to the current years.
            if (year.length() == 4 & Integer.parseInt(year) <= curYear ){
                Validy2 = true;
            }else {
                System.out.println("invalid year input, check whether your year input in the future ");
                Validy2 = false;
            }
            yearValid = Validy1&Validy2; // the year only valid if satisfy the condition of having valid character and in the valid time range
        }

        String DateParse = year+"/"+pmon+"/"+pday;
        System.out.println(DateParse);
        Date DOI = Converter.StrToDate(DateParse);
        boolean InFuture = true;
        int i = 0;
        Date current = Date.from(LocalDate.now().atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
        while (InFuture){ // check if the input is in the future or not
            if(i > 0){
                System.out.println("your input date is in the future, please type the date of interaction again");
                DOI = input_DOI(inmode);
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
                            DOI = input_DOI("doi");
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

    public static void confirmDeleteInter(){
        System.out.println("please type in the interID to delete");
        String InterID = sys_in.nextLine();
        Generator.generateLoading(30);
        CSVManager.getInstance().deleteInter(InterID);
        Continue();

    }

    public static void Continue(){
        System.out.println("Do you want to continue use the system ? , type yes for continue type no for quitting");
        String bool = sys_in.nextLine().toLowerCase();
        if (bool.equals("yes")) {
            System.out.println("Enter your decision by type in the number associated with that option :");
            System.out.println("1. Go to the Main menu");
            System.out.println("2. Go to the Lead Manager Page");
            System.out.println("3. Go to the Interaction Manager Page");
            System.out.println("4. Go to the Report Manager Page");
            System.out.println("0. Quit");
            String order = sys_in.nextLine();
            if (order.length() == 1){ // using switch to activate a function at a time when user chose the function to activate
                switch (order){
                    case "1":
                        Menu.getInstance().MainMenu(0);
                        break;
                    case "2":
                        MenuLead.getInstance().MainMenu(0);
                        break;
                    case "3":
                        MenuInter.getInstance().MainMenu(0);
                        break;
                    case "4":
                        MenuReport.getInstance().MainMenu(0);
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
