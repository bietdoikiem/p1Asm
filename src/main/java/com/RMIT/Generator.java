package com.RMIT;

import java.util.ArrayList;
import java.util.Calendar;

public class Generator {
    public static String generateLeadId() {
        CSVManager csvManager = CSVManager.getInstance();
        int leadId = csvManager.getLastLeadId();
        if (leadId >= 1) {
            return String.format("%03d", leadId+1);
        }
        return "001";
    }
    public static void LeadReport(){
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<Lead> leadIterate = CSVManager.getInstance().getLeadAll();
        int LeTen =0;
        int LeTeen = 0;
        int LeHealth =0;
        int LeAge = 0;
        for(int i =0; i < leadIterate.size();i++){
            Lead leadn = leadIterate.get(i);
            String DoB = Converter.DateToStr(leadn.getDOB());
            String DateCal = DoB.substring(0,4);
            System.out.println(DateCal);
            System.out.println(curYear);
            int age = curYear - Integer.parseInt(DateCal);
            if (age <= 10){
                LeTen +=1;
            }
            if (age <= 20 & age >10){
                LeTeen++;
            }
            if (age <=60 & age>20){
                LeAge++;
            }
            if(age>60){
                LeAge ++;
            }
        }
        System.out.println("0-10(years old)  10-20(years old)  20-60(years old)  >60(years old)");

        System.out.println(LeTen+"          " + LeTeen+"          " + LeHealth+"          " + LeAge +"          ");
    }
}
