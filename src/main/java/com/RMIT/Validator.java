package com.RMIT;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Validator {
    private static Validator single_instance = null;
    public Validator(){}
    public static Validator getInstance() {
        if (single_instance == null) {
            single_instance = new Validator();
        }
        return single_instance;
    }
    public boolean validate_email  (String input_mail){
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
}

