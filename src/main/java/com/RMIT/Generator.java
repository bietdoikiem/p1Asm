package com.RMIT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Generator {
    public static String generateLeadIdNumber() {
        CSVManager csvManager = CSVManager.getInstance();
        int leadId = csvManager.getLastLeadIdNumber();
        if (leadId >= 1) {
            return String.format("%03d", leadId+1);
        }
        return "001";
    }
    public static String generateInterIdNumber() {
        CSVManager csvManager = CSVManager.getInstance();
        int interId = csvManager.getLastInterIdNumber();
        if (interId >= 1) {
            return String.format("%03d", interId+1);
        }
        return "001";
    }
    public static void generateLeadReport(){
//        Date current = Date.from(LocalDate.now().atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
        ArrayList <Lead> LeadArr = CSVManager.getInstance().getLeadAll();
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int DayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int leKid = 0;
        int leTeen = 0;
        int leGrowth =0;
        int leAge = 0;
        int yearTeen = curYear - 10;
        int yearAging = curYear - 60;
        int yearGrowth = curYear - 20;
        Date Kid = new GregorianCalendar(yearTeen,curMonth,DayOfMonth).getTime();
        Date Teenage = new GregorianCalendar(yearGrowth,curMonth,DayOfMonth).getTime();
        Date Growth = new GregorianCalendar(yearAging,curMonth,DayOfMonth).getTime();
//        Date Te
        for (int i =0;i<LeadArr.size();i++){
            Lead thisLead = LeadArr.get(i);
            Date DoB = thisLead.getDOB();
            System.out.println(" Kid: "+Converter.DateToStr(Kid));
            System.out.println(" Teenage: "+Converter.DateToStr(Teenage));
            System.out.println(" Growth: "+Converter.DateToStr(Growth));
            System.out.println(String.valueOf(Kid.after(DoB)));
            System.out.println(String.valueOf(DoB.after(Teenage)));
            System.out.println(String.valueOf(DoB.after(Growth)));
            if (DoB.after(Kid)){
                leKid++;

            }
            if (Kid.after(DoB) & DoB.after(Teenage)){
                leTeen ++;
            }
            if (Teenage.after(DoB) & DoB.after(Growth)){
                leGrowth++;
            }
            if (Growth.after(DoB)){
                leAge ++;
            }

        }
        System.out.println("0-10 (years old)| 10-20(years old)| 20-60(years old)| >60(years old)");
        System.out.println(leKid+"               | "+leTeen+"               | "+leGrowth+"               |  "+leAge);
//        System.out.println(curMonth);

    }
}
