package com.RMIT;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {
//    private static Converter single_instance = null; // make this class singleton for easier to manage
//    public Converter(){}
//    public static Converter getInstance() {
//        if (single_instance == null) {
//            single_instance = new Converter();
//        }
//        return single_instance;
//    }
    public static Date StrToDate(String input) {
        // listing the available date format

        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter3=new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat formatter4=new SimpleDateFormat("dd/MM/yyyy");
        // create array of format
        SimpleDateFormat[] formats = new SimpleDateFormat[4];
        formats[0] = formatter1;
        formats[1] =formatter2;
        formats[2] = formatter3;
        formats[3] = formatter4;
        Date data = null;
        Date data2 =null;
        // Iterate through Date format, match input with the right one and convert it to Date
        for (int i=0; i<formats.length;i++){
            try{
                data2 = data;
                data = formats[i].parse(input);
            }catch (ParseException exception){
                data = data2;
            }
        }
        return data;
    }
    public static String DateToStr(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(date);
        return strDate;
    }

    public static String StrToMonthYear(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
        String strDate = formatter.format(date);
        return strDate;
    }
}
