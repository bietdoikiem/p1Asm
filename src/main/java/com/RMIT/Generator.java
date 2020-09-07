package com.RMIT;


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
        ArrayList<String> myMonths = new ArrayList<>();
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

//        Iterator<Interaction> iterator_1 = myFilteredInters.iterator();
//        while(iterator_1.hasNext()) {
//            Interaction inter = iterator_1.next();
//
//        }

    }

    public static ArrayList<Interaction> filterInterByDate(Date begin, Date end) {
        ArrayList<Interaction> myInters = csvManager.getInterAll();
        ArrayList<Interaction> myFilteredInters = new ArrayList<>();
        Iterator<Interaction> iterator_1 = myInters.iterator();
        while(iterator_1.hasNext()) {
            Interaction inter_1 = iterator_1.next();
            if(inter_1.getDOI().getTime() >= begin.getTime() && inter_1.getDOI().getTime() <= end.getTime()) {
                myFilteredInters.add(inter_1);
            }
        }
        return myFilteredInters;
    }
}
