package com.RMIT;

// OpenCsv module import
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

// java modules import
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class CSVManager  {
    private static CSVManager single_instance = null;
    private static final String LEAD_FILE_PATH = "src/main/resources/leads.csv";
    private static final String INTERACTION_FILE_PATH = "src/main/resources/interactions.csv";

    private CSVManager() {}
    public static CSVManager getInstance() {
        if (single_instance == null) {
            single_instance = new CSVManager();
        }
        return single_instance;
    }
    public void print() {
        System.out.println("CSVManager called");
    }
    public void addFile(String type) {
        if(type.equals("lead")) {
            try ( // Parenthesized try-with block to close stream resource after utilized
                  Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH));

                  CSVWriter csvWriter = new CSVWriter(writer,
                          CSVWriter.DEFAULT_SEPARATOR,
                          CSVWriter.NO_QUOTE_CHARACTER,
                          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                          CSVWriter.DEFAULT_LINE_END);
            ) {
                String[] headerRecord = {"id", "name", "DOB", "gender", "phone", "email", "address"};
                csvWriter.writeNext(headerRecord);
                //System.out.println("CSVManager has initialized csv file with headers");
                // csvWriter.writeNext(new String[]{"lead_001", "Minh Ng", "2001-08-24", "true", "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu"});
            } catch (IOException err) {
                err.printStackTrace();
            }
        } else if (type.equals("inter")) {
            try ( // Parenthesized try-with block to close stream resource after utilized
                  Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH));

                  CSVWriter csvWriter = new CSVWriter(writer,
                          CSVWriter.DEFAULT_SEPARATOR,
                          CSVWriter.NO_QUOTE_CHARACTER,
                          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                          CSVWriter.DEFAULT_LINE_END);
            ) {
                String[] headerRecord = {"id", "DOI", "leadId", "mean", "potential"};
                csvWriter.writeNext(headerRecord);
                //System.out.println("CSVManager has initialized csv file with headers");
                // csvWriter.writeNext(new String[]{"lead_001", "Minh Ng", "2001-08-24", "true", "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu"});
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }
    public void addLead(Lead lead)  {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ){
            StatefulBeanToCsv<Lead> beanToCsv = setupCSVBuilderWriter("lead", writer);
            beanToCsv.write(lead);
            System.out.println("Add lead successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
        catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
        }
    public Lead getLead(String id) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(LEAD_FILE_PATH));
        ){
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader);
            Lead foundLead = null;
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                }
            }
            String leadId = foundLead.getId();
            if(leadId != null) {
                System.out.println("Lead is found");
            }
        } catch(NullPointerException | IOException err) {
            System.out.println("No ID Found");
        }
        return null;
    }
    public ArrayList<Lead> getLeadAll() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(LEAD_FILE_PATH));

        ){
            // Mapping data's column to Lead's properties
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader);
            ArrayList<Lead> myLeads = new ArrayList<>();
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();

            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                myLeads.add(lead);
            }if (myLeads.isEmpty()) {
                System.out.println("No leads available");
            }
            return myLeads;
        } catch(NullPointerException | IOException err) {
            System.out.println("No leads found");
        }
        return null;
    }

    public int getLastLeadIdNumber() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(LEAD_FILE_PATH));
                ){
            // Mapping data's column to Lead's properties
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader);
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();
            String lastId = "";
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                 lastId = lead.getId();
            }
            String[] strArray = lastId.split("_");
            return Integer.parseInt(strArray[1]);
        } catch(IndexOutOfBoundsException | IOException err) {
            return 0;
        }
    }

    public void updateLead(String id, String name, Date DOB, boolean gender, String phone, String email, String address)  {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Lead> myLeads = getLeadAll();
            Lead foundLead = null;
            for (Lead lead : myLeads) {
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                    break;
                }
            }
            if (foundLead != null) {
                addFile("lead"); // Recreating CSV file for overwriting new modified data.
                // Mapping data's column to Lead's properties
                StatefulBeanToCsv<Lead> beanToCsv = setupCSVBuilderWriter("lead", writer);
                //Save any changes to the object
                foundLead.setName(name);
                foundLead.setDOB(DOB);
                foundLead.setGender(gender);
                foundLead.setPhone(phone);
                foundLead.setEmail(email);
                foundLead.setAddress(address);
                beanToCsv.write(myLeads); // Write the object to csv file
                System.out.println("Update successfully");
            } else {
                System.out.println("Lead not found. Please try again");
            }
        }  catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
        catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLead(String id)  {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                Writer interWriter = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ){
            ArrayList<Lead> myLeads = getLeadAll();
            Lead foundLead = null;
            ArrayList<Interaction> myInters = getInterAll();
            int removedInter = 0;
            Iterator<Interaction> iter = myInters.iterator();
            Interaction deletedInter = null;
            String lastId = "";
            for (Lead lead : myLeads) {
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                }
            }
            if (foundLead != null) {
                addFile("lead"); // Recreating CSV file for overwriting new modified data.
                myLeads.remove(foundLead);
                //Initialize Writer for Lead list
                StatefulBeanToCsv<Lead> beanToCsv = setupCSVBuilderWriter("lead",writer);
                beanToCsv.write(myLeads); // Write new Leads list to CSV file
                while(iter.hasNext()) {
                    Interaction inter = iter.next();
                    if(inter.getLeadId().equals(foundLead.getId())) {
                        iter.remove();
                        removedInter += 1;
                    }
                }
                if(removedInter > 0) {
                    //Initialize Writer for Inter list
                    StatefulBeanToCsv<Interaction> beanToCsvInter = setupCSVBuilderWriter("inter", interWriter);
                    addFile("inter");
                    beanToCsvInter.write(myInters); // Write new Inters list to CSV file
                }
                System.out.println("Delete successfully");
            } else {
                System.out.println("Lead not found. Please try again");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    public StatefulBeanToCsv setupCSVBuilderWriter(String data, Writer writer) {
        if(data.equals("lead")) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            StatefulBeanToCsv<Lead> beanToCsv = new StatefulBeanToCsvBuilder<Lead>(writer)
                    .withMappingStrategy(strategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            return beanToCsv;
        }
         else if (data.equals("inter")) {
                ColumnPositionMappingStrategy<Interaction> strategy = new ColumnPositionMappingStrategy<Interaction>();
                strategy.setType(Interaction.class);
                String[] memberFieldsToBindTo = {"id", "DOI", "leadId", "mean", "potential"};
                strategy.setColumnMapping(memberFieldsToBindTo);
                StatefulBeanToCsv<Interaction> beanToCsv = new StatefulBeanToCsvBuilder<Interaction>(writer)
                        .withMappingStrategy(strategy)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .build();
                return beanToCsv;
        }
        return null;
    }
    public CsvToBean setupCSVBuilderReader(String data, Reader reader) {
        if (data.equals("lead")) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean;
        } else if (data.equals("inter")) {
                ColumnPositionMappingStrategy<Interaction> strategy = new ColumnPositionMappingStrategy<>();
                strategy.setType(Interaction.class);
                String[] memberFieldsToBindTo = {"id", "DOI", "leadId", "mean", "potential"};
                strategy.setColumnMapping(memberFieldsToBindTo);
                CsvToBean<Interaction> csvToBean = new CsvToBeanBuilder<Interaction>(reader)
                        .withMappingStrategy(strategy)
                        .withSkipLines(1)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                return csvToBean;
        }
        return null;
        }

    public void addInter(Interaction inter)  {
            try (
                    Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            ) {
                    String leadId = getLead(inter.getLeadId()).getId();
                    StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter", writer);
                    beanToCsv.write(inter);
                    System.out.println("Add interaction with " + leadId + " successfully");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            } catch (CsvDataTypeMismatchException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Lead ID of interaction is not valid");
            }
    }


    public Interaction getInter(String id) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
        ) {
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader);
            Interaction foundInter = null;
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator();
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next();
                if (inter.getId().equals(id)) {
                    foundInter = inter;
                }
            }
            String interId = foundInter.getId();
            if(interId != null) {
                System.out.println("Lead is found");
            }
            return foundInter;
        } catch(NullPointerException | IOException err) {
            System.out.println("No ID Found");
        }
        return null;
    }
    public ArrayList<Interaction> getInterAll() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
        ){
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader);
            ArrayList<Interaction> myInters = new ArrayList<>();
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator();
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next();
                myInters.add(inter);
            } if (myInters.isEmpty()) {
                System.out.println("No interactions available");
            }
            return myInters;
        } catch(NullPointerException | IOException err) {
            System.out.println("No interactions found");
        }
        return null;
    }

    public int getLastInterIdNumber() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
                ){
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader);
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator();
            String lastId = "";
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next();
                lastId = inter.getId();
            }
            String[] strArray = lastId.split("_");
            return Integer.parseInt(strArray[1]);
        } catch(IndexOutOfBoundsException | IOException err) {
            return 0;
        }
    }

    public void updateInter(String id, Date DOI, String leadId, String mean, String potential) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Interaction> myInters = getInterAll();
            Interaction foundInter = null;
            for (Interaction inter : myInters) {
                if (inter.getId().equals(id)) {
                    foundInter = inter;
                    break;
                }
            }
            if (foundInter != null) {
                addFile("inter"); // Recreating CSV file for overwriting new modified data.
                // Mapping data's column to Lead's properties
                StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter",writer);
                //Save any changes to the object
                foundInter.setDOI(DOI);
                foundInter.setLeadId(leadId);
                foundInter.setMean(mean);
                foundInter.setPotential(potential);
                beanToCsv.write(myInters); // Write the object to csv file
                System.out.println("Update successfully");

            } else {
                System.out.println("Interaction not found. Please try again");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    public void deleteInter(String id){
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Interaction> myInters = getInterAll();
            Interaction foundInter = null;
            String lastId = "";
            for (Interaction inter : myInters) {
                if (inter.getId().equals(id)) {
                    foundInter = inter;
                }
            }
            if (foundInter != null) {
                addFile("inter"); // Recreating CSV file for overwriting new modified data.
                myInters.remove(foundInter);
                // Mapping data's column to Lead's properties
                StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter", writer);
                beanToCsv.write(myInters);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Interaction not found. Please try again");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }
}

