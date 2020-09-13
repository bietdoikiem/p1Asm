package com.RMIT;

import java.util.ArrayList;
import java.util.Date;

public interface LeadManagement {
    public void addLead(Lead lead);

    public Lead getLead(String id);

    public ArrayList<Lead> getLeadAll();

    public int getLastLeadIdNumber();

    public void updateLead(String id, String name, Date DOB, boolean gender, String phone, String email, String address);

    public void deleteLead(String id);

}
