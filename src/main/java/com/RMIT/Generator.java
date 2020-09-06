package com.RMIT;

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
}
