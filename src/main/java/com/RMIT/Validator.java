package com.RMIT;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Validator {
    private static Validator single_instance = null; // make this class singleton for easier to manage
    public Validator(){}
    public static Validator getInstance() {
        if (single_instance == null) {
            single_instance = new Validator();
        }
        return single_instance;
    }
    public boolean validate_email  (String input_mail){ // validate email form
        String email_regex =  "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(email_regex);
        if (input_mail == null){
            return false;}
        else{
            return pat.matcher(input_mail).matches();
        }
    }

    public static String return_mail (String input_mail){
        boolean valid = Validator.getInstance().validate_email(input_mail);
        if (valid == true){
            return input_mail;
        } else {
            System.out.println("Invalid input mail");
            return null;
        }
    }

    private static boolean Validate_phone(String input_phone){ // validate phone by format of 9-11 intergers combined
        if (input_phone != null){
            if(input_phone.length()<= 11 && input_phone.length()>=9){
                for(int i =0;i<input_phone.length();i++){
                    char char_i = input_phone.charAt(i);
                    if (char_i < '0' || char_i >'9' ){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        else {
            System.out.println("You didn't type the phone number yet");
            return false;
        }
    }

    private static boolean not_overlap_data(String input_data,String type){ // iterate through the lead_csv file to find overlap
        String adjusted = type.toLowerCase();                            // data such as phone and email.

        try (
                Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/leads.csv"))
        ) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();

            if (adjusted == "phone"){
                while (csvLeadIterator.hasNext()) {
                    Lead lead = csvLeadIterator.next();
                    if (lead.getPhone().equals(input_data)){
                        System.out.println("Another lead having this phone number :");
                        System.out.println("ID: " + lead.getId());
                        System.out.println("Name : " + lead.getName());
                        System.out.println("Email : " + lead.getEmail());
                        System.out.println("PhoneNo : " + lead.getPhone());
                        System.out.println("DOB: " + lead.getDOBString());
                        System.out.println("Gender: " + lead.getGender());
                        System.out.println("==========================");
                        return false;
                    }

                }
            }
            if (adjusted == "email"){
                while (csvLeadIterator.hasNext()){
                    Lead lead = csvLeadIterator.next();
                    if (lead.getEmail().equals(input_data)){
                        System.out.println("Another lead having this email :");
                        System.out.println("ID: " + lead.getId());
                        System.out.println("Name : " + lead.getName());
                        System.out.println("Email : " + lead.getEmail());
                        System.out.println("PhoneNo : " + lead.getPhone());
                        System.out.println("DOB: " + lead.getDOBString());
                        System.out.println("Gender: " + lead.getGender());
                        System.out.println("==========================");
                        return false;
                    }
                }
            }
            if (adjusted == "id"){
                while (csvLeadIterator.hasNext()){
                    Lead lead = csvLeadIterator.next();
                    if (lead.getId().equals(input_data)){
                        return false;
                    }
                }
            }
        } catch(IOException err) {
            err.printStackTrace();
        }
        return true;
    }

    public String[] get_phone_validated (String input_phone){ // get the phone number after two type of validated
        boolean valid_phone_form =Validator.getInstance().Validate_phone(input_phone);
        boolean phone_not_overlap = Validator.not_overlap_data(input_phone,"phone");

        String[] return_value = new String[2];

        boolean ready = valid_phone_form & phone_not_overlap;

        if (ready == true){
            return_value[0] = input_phone;
            return_value[1] = "true";

        }else {
            return_value[1] = "false";
        }

        return  return_value;

    }

    public String[] get_email_validated (String input_mail){ // get the phone number after two type of validated
        boolean valid_mail_form =Validator.getInstance().validate_email(input_mail);
        boolean mail_not_overlap = Validator.not_overlap_data(input_mail,"email");

        String[] return_value = new String[2];

        boolean ready = valid_mail_form & mail_not_overlap;

        if (ready == true){
            return_value[0] = input_mail;
            return_value[1] = "true";

        }else {
            return_value[1] = "false";
        }

        return  return_value;

    }

    public boolean Validate_address (String input_address){ // validate address as form interger and string combined
        String[] List_word = input_address.split(" ");
        if (List_word.length >= 2 ){
            String a = List_word[0];
            for(int i =0;i<a.length()-1;i++){
                char char_i = a.charAt(i);
                if (char_i < '0' || char_i >'9' ){
                    return false;
                }
            }
            return true;
        }
        System.out.println("There are not enough information in the address");
        return false;

    }

    public String[] get_validated_address(String input_address){
        String[] return_value = new String[2];
        boolean ready = Validator.getInstance().Validate_address(input_address);
        if (  ready == true){
            return_value[0] = input_address;
            return_value[1] = "true";
        }else{
            return_value[1] = "false";
        }
        return return_value;
    }

    public String[] get_validated_leadid (String input_id){ //Check and return the valid Lead_id for create new data
        String[] return_value = new String[2];
        boolean ready = !Validator.not_overlap_data(input_id,"id");
        if (ready == true){
            return_value[0] = input_id;
            return_value[1] = "true";
        }else {
            System.out.println("Invalid lead_id, please retype ID");
            return_value[1] = "false";
        }
        return return_value;
    }

    // Validate order for update Lead

    public String[] GetValidatedLeadOrder(String order){
        String[] return_value = new String[order.length()+2]; // Create return value of list order and valid, message of exception data
        boolean repeated = false;
        if(order.length() > 6){ // check for order not to be too long
            return_value[0] = "false";
            return_value[1] = "Out of order for feature change, you should have maximum of 6 feature change for an order";
        }else {
            for (int i =0;i<order.length();i++){ // check for an order in the right number
                if (order.charAt(i)>'6' | order.charAt(i) < '1'){
                    return_value[0] = "false";
                    return_value[1] = "The order number should be between 1 and 6";
                }else {
                    for (int j =i; j<order.length();j++){ // check for the order being repeated
                        if (order.charAt(j) == order.charAt(i) & j!=i){
                          repeated = true;
                          break;
                        }
                    }
                    if (repeated == true){
                        return_value[0] = "false";
                        return_value[1] = "you can only update a feature once in each order";
                    }else { // in this case, the valid data is true, the message is none and we parse the order to the return value
                        return_value[0] = "true";
                        for (int k = 0;k <order.length();k++){
                            return_value[k+2] = String.valueOf(order.charAt(k));
                        }
                    }
                }
            }
        }return  return_value;
    }

}

