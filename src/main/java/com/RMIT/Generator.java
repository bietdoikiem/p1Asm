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
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RESET = "\u001B[0m";
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

    // Generate lead report by categories of age brackets.
    public static void generateLeadReport() {
//        Date current = Date.from(LocalDate.now().atStartOfDay().toInstant(OffsetDateTime.now().getOffset()));
        ArrayList<Lead> LeadArr = CSVManager.getInstance().getLeadAll();
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int DayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int leKid = 0; // number of Lead younger than 10
        int leTeen = 0; // number of lead between 10 and 20 years old
        int leGrowth = 0; // number of lead bet ween 20 and 60 years old
        int leAge = 0; // number of people at the age of beyond 60
        int yearTeen = curYear - 10;
        int yearAging = curYear - 60;
        int yearGrowth = curYear - 20;
        Date Kid = new GregorianCalendar(yearTeen, curMonth, DayOfMonth).getTime(); // get the date to identify if someone is at 10 years old
        Date Teenage = new GregorianCalendar(yearGrowth, curMonth, DayOfMonth).getTime();
        Date Growth = new GregorianCalendar(yearAging, curMonth, DayOfMonth).getTime();
//        Date Te
        for (int i = 0; i < LeadArr.size(); i++) {
            Lead thisLead = LeadArr.get(i);
            Date DoB = thisLead.getDOB();

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
        generateLoading(30);
        //System.out.println("0-10 (years old)| 10-20(years old)| 20-60(years old)| >60(years old)");
        // Align format of string to matched columns and rows
        System.out.format("%-20s", "0-10 (years old)");
        System.out.format("%-20s", "10-20 (years old)");
        System.out.format("%-20s", "20-60 (years old)");
        System.out.format("%-20s", ">60 (years old)");
        System.out.println(" ");
        //System.out.println(leKid + "               | " + leTeen + "               | " + leGrowth + "               |  " + leAge);
        System.out.format("%-20s", "      " + leKid);
        System.out.format("%-20s", "      " + leTeen);
        System.out.format("%-20s", "      " + leGrowth);
        System.out.format("%-20s", "      " + leAge);
        System.out.println(" ");
        InputGetter.Continue();
    }
//        System.out.println(curMonth);

    // Generate number of potential attitudes (Pos, Neu, Neg) in a time range
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
        generateLoading(30);
        // Align format of string to matched columns and rows
        System.out.format("%-18s", "Positive");
        System.out.format("%-18s", "Neutral");
        System.out.format("%-18s", "Negative");
        System.out.println(" ");
        System.out.format("%-18s", "   "+countPositive);
        System.out.format("%-18s", "   "+countNeutral);
        System.out.format("%-18s", "   "+countNegative);
        System.out.println(" ");
    }

    // Generate number of interactions monthly report
    public static void generateInteractionReport(Date begin, Date end)  {
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
        generateLoading(50);
        // Align format of string to matched columns and rows
        for (String i: interByMonth.keySet()) {
            System.out.format("%-18s", i);
        }
        System.out.println(" ");
        for (String i: interByMonth.keySet()) {
            System.out.format("%-18s", "    " + interByMonth.get(i));
        }
        System.out.println(" ");
    }

    // Methods to filter interactions by date range
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
    public static void generateLoading(int time) {
        try {
            System.out.print(ANSI_GREEN + "[" + ANSI_RESET + "                    ]");
            System.out.flush(); // the flush method prints it to the screen

            // 11 '\b' chars: 1 for the ']', the rest are for the spaces
            System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
            System.out.flush();
            Thread.sleep(time); // just to make it easy to see the changes

            for (int i = 0; i < 20; i++) {
                System.out.print(ANSI_PURPLE + "#" + ANSI_RESET); //overwrites a space
                System.out.flush();
                Thread.sleep(time);
            }
            System.out.print(ANSI_GREEN + "] 100% Done!\n" + ANSI_RESET); //overwrites the ']' + adds chars
            Thread.sleep(400);
            System.out.flush();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
