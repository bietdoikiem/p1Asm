package com.RMIT;

public class Generator {
    public static String generateLeadId() {
        CSVManager csvManager = CSVManager.getInstance();
        int leadId = csvManager.getLastLeadId();
        if (leadId >= 1) {
            return String.format("%03d", leadId+1);
        }
        return "001";
    }
}
