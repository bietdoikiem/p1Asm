package com.RMIT;

import java.util.ArrayList;
import java.util.Date;

public interface InterManagement {
    public void addInter(Interaction inter);

    public Interaction getInter(String id);

    public ArrayList<Interaction> getInterAll();

    public int getLastInterIdNumber();

    public void updateInter(String id, Date DOI, String leadId, String mean, String potential);

    public void deleteInter(String id);

}
