package com.RMIT;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.text.SimpleDateFormat;
import java.util.*;

public class Generator {
    private static final CSVManager csvManager = CSVManager.getInstance();
    public static String generateLeadIdNumber() {
        int leadId = csvManager.getLastLeadIdNumber();
        if (leadId >= 1) {
            return String.format("%03d", leadId+1);
        }
        return "001";
    }
    public static String generateInterIdNumber() {
        int interId = csvManager.getLastInterIdNumber();
        if (interId >= 1) {
            return String.format("%03d", interId+1);
        }
        return "001";
    }
    public static void generateLeadReport() {
//        Date current = Date.from(LocalDate.now().atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
        ArrayList<Lead> LeadArr = CSVManager.getInstance().getLeadAll();
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int DayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int leKid = 0;
        int leTeen = 0;
        int leGrowth = 0;
        int leAge = 0;
        int yearTeen = curYear - 10;
        int yearAging = curYear - 60;
        int yearGrowth = curYear - 20;
        Date Kid = new GregorianCalendar(yearTeen, curMonth, DayOfMonth).getTime();
        Date Teenage = new GregorianCalendar(yearGrowth, curMonth, DayOfMonth).getTime();
        Date Growth = new GregorianCalendar(yearAging, curMonth, DayOfMonth).getTime();
//        Date Te
        for (int i = 0; i < LeadArr.size(); i++) {
            Lead thisLead = LeadArr.get(i);
            Date DoB = thisLead.getDOB();
//            System.out.println(" Kid: " + Converter.DateToStr(Kid));
//            System.out.println(" Teenage: " + Converter.DateToStr(Teenage));
//            System.out.println(" Growth: " + Converter.DateToStr(Growth));
//            System.out.println(String.valueOf(Kid.after(DoB)));
//            System.out.println(String.valueOf(DoB.after(Teenage)));
//            System.out.println(String.valueOf(DoB.after(Growth)));
            if (DoB.after(Kid)) {
                leKid++;

            }
            if (Kid.after(DoB) & DoB.after(Teenage)) {
                leTeen++;
            }
            if (Teenage.after(DoB) & DoB.after(Growth)) {
                leGrowth++;
            }
            if (Growth.after(DoB)) {
                leAge++;
            }

        }
        System.out.println("0-10 (years old)| 10-20(years old)| 20-60(years old)| >60(years old)");
        System.out.println(leKid + "               | " + leTeen + "               | " + leGrowth + "               |  " + leAge);
        InputGetter.Continue();
    }
//        System.out.println(curMonth);

    public static void generatePotentialReport(Date begin, Date end) {
        ArrayList<Interaction> myFilteredInters = filterInterByDate(begin, end);
        long countPositive = 0;
        long countNeutral = 0;
        long countNegative = 0;
        Iterator<Interaction> iterator_1 = myFilteredInters.iterator();
        while(iterator_1.hasNext()) {
            Interaction inter = iterator_1.next();
            if(inter.getPotential().equals("positive")) {
                countPositive++;
            }
            if(inter.getPotential().equals("neutral")) {
                countNeutral++;
            }
            if(inter.getPotential().equals("negative")) {
                countNegative++;
            }
        }
        System.out.println("Positive \t Neutral \t Negative");
        System.out.println(countPositive + " " + "\t \t \t" + "    " + countNeutral + "\t \t \t " + countNegative);
    }

    public static void generateInteractionReport(Date begin, Date end) {
        ArrayList<Interaction> myFilteredInters = filterInterByDate(begin, end);
        LinkedHashMap<String, String> interByMonth = new LinkedHashMap<>();
        //Date configuration for time range
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(begin);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(end);
        //
        while(startDate.before(endDate)) {
            long count = 0;
            String date = Converter.StrToMonthYear(startDate.getTime());
            for(Interaction inter: myFilteredInters) {
                Calendar interDate = Calendar.getInstance();
                interDate.setTime(inter.getDOI());
                if(interDate.get(Calendar.MONTH) == startDate.get(Calendar.MONTH) && interDate.get(Calendar.YEAR) == interDate.get(Calendar.YEAR)) {
                    count += 1;
                }
            }
            String countStr = Long.toString(count);
            interByMonth.put(date, countStr);
            //System.out.println(date);
            startDate.add(Calendar.MONTH, 1);
        }

        for (String i: interByMonth.keySet()) {
            System.out.println(i + " " + interByMonth.get(i));
        }
    }

    public static ArrayList<Interaction> filterInterByDate(Date begin, Date end) {
        ArrayList<Interaction> myInters = csvManager.getInterAll();
        ArrayList<Interaction> myFilteredInters = new ArrayList<>();
        Iterator<Interaction> iterator_1 = myInters.iterator();
        while(iterator_1.hasNext()) {
            Interaction inter_1 = iterator_1.next();
            //System.out.println(inter_1.getDOI().getTime() + " " + begin.getTime() + " " + end.getTime());
            if(inter_1.getDOI().getTime() >= begin.getTime() && inter_1.getDOI().getTime() <= end.getTime()) {
                myFilteredInters.add(inter_1);
                //System.out.println(inter_1.getId());
            }
        }
        return myFilteredInters;
    }
}
