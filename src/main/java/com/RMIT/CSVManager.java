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
    // initialize singleton pattern
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
                  // create buffer stream of csv file with "write" mode
                  Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH));
                  //Initialize writer with specified attributes
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
                  // create buffer stream of csv file with "write" mode
                  Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH));
                  //Initialize writer with specified attributes
                  CSVWriter csvWriter = new CSVWriter(writer,
                          CSVWriter.DEFAULT_SEPARATOR,
                          CSVWriter.NO_QUOTE_CHARACTER,
                          CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                          CSVWriter.DEFAULT_LINE_END);
            ) {
                String[] headerRecord = {"id", "DOI", "leadId", "mean", "potential"};
                csvWriter.writeNext(headerRecord);
                //System.out.println("CSVManager has initialized csv file with headers");
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }
    public void addLead(Lead lead)  {
        try ( // create buffer stream of csv file with "write" mode
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ){
            StatefulBeanToCsv<Lead> beanToCsv = setupCSVBuilderWriter("lead", writer); // call Builder for writing data
            beanToCsv.write(lead); // write data using builder
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
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader); // create builder to read data
            Lead foundLead = null; // placeholder for found lead
            Iterator<Lead> csvLeadIterator = csvToBean.iterator(); // create Iterator to traverse through list of Lead data
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next(); // acquire the next object in the list
                if (lead.getId().equals(id)) {
                    foundLead = lead; // assign the found lead to the placeholder object
                    break;
                }
            }
            String leadId = foundLead.getId(); // assign lead Id variable to make sure no null object has been found
            if(leadId != null) {
                System.out.println("Lead is found");
                return foundLead;
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
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader); // create builder to read data
            ArrayList<Lead> myLeads = new ArrayList<>(); // placeholder array list to store retrieved data
            Iterator<Lead> csvLeadIterator = csvToBean.iterator(); // create Iterator to traverse list data

            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next(); // acquire the next Lead object in the list
                myLeads.add(lead); // append found object to the list
            }if (myLeads.isEmpty()) { // check if no leads have been found
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
            CsvToBean<Lead> csvToBean = setupCSVBuilderReader("lead", reader); // create builder to read data
            Iterator<Lead> csvLeadIterator = csvToBean.iterator(); // create Iterator to traverse data
            String lastId = ""; // placeholder for last ID of lead's list
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next(); // acquire the next object in the list
                 lastId = lead.getId(); // store the lead's ID until it reaches the end in order to acquire the final one
            }
            String[] strArray = lastId.split("_"); // split ID string into array strings with "_" separators
            return Integer.parseInt(strArray[1]); // return the number part of the array string
        } catch(IndexOutOfBoundsException | IOException err) {
            return 0;
        }
    }

    public void updateLead(String id, String name, Date DOB, boolean gender, String phone, String email, String address)  {
        try ( // create buffer stream of csv file with "write" mode
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Lead> myLeads = getLeadAll(); // retrieve list of all leads
            Lead foundLead = null; // placeholder object for storing found lead
            for (Lead lead : myLeads) {
                if (lead.getId().equals(id)) {
                    foundLead = lead; // set foundLead to the reference of matched ID lead
                    break;
                }
            }
            if (foundLead != null) {
                addFile("lead"); // Recreating CSV file for overwriting new modified data.
                StatefulBeanToCsv<Lead> beanToCsv = setupCSVBuilderWriter("lead", writer);
                //Save any changes to the object if has one or many
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

    //Delete lead by ID
    public void deleteLead(String id)  {
        // create buffer stream of csv file with "write" mode
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(LEAD_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND); // Writer for lead csv file
                Writer interWriter = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND); // Writer for interaction csv file
        ){
            ArrayList<Lead> myLeads = getLeadAll(); // retrieve list of leads
            Lead foundLead = null; // create placeholder object
            ArrayList<Interaction> myInters = getInterAll(); // retrive list of interactions
            int removedInter = 0; // setup count int variable for updating interaction file
            Iterator<Interaction> iter = myInters.iterator();
            // iterate through the list to acquire needed lead
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
                // loop through lists to acquire interactions involved with the found lead.
                while(iter.hasNext()) {
                    Interaction inter = iter.next();
                    if(inter.getLeadId().equals(foundLead.getId())) {
                        iter.remove();
                        removedInter += 1; // add to number of deleted interaction
                    }
                }
                if(removedInter > 0) { // check if if there are any deleted interactions for rewriting purpose
                    //Initialize Writer for Inter list
                    StatefulBeanToCsv<Interaction> beanToCsvInter = setupCSVBuilderWriter("inter", interWriter);
                    addFile("inter"); // rewrite interaction csv file
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

    //Create OpenCSV Builder to write data to specific csv file
    public StatefulBeanToCsv setupCSVBuilderWriter(String data, Writer writer) {
        if(data.equals("lead")) { // Check for kind of data (lead) to be written
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>(); // Initialize mapping strategy for data columns
            strategy.setType(Lead.class); // Set strategy mapping to be matched with properties of class Lead
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"}; // String array to store data columns
            strategy.setColumnMapping(memberFieldsToBindTo); // set column mapping to match data columns
            StatefulBeanToCsv<Lead> beanToCsv = new StatefulBeanToCsvBuilder<Lead>(writer) // call Csv Builder object from OpenCSV library
                    .withMappingStrategy(strategy) // set strategy mapping for builder
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER) // ignore quote characters when writing
                    .build(); // successfully build the object
            return beanToCsv;
        }
         else if (data.equals("inter")) { // Check for kind of data (interaction) to be written
                ColumnPositionMappingStrategy<Interaction> strategy = new ColumnPositionMappingStrategy<Interaction>(); // Initialize mapping strategy for data columns
                strategy.setType(Interaction.class);  // Set strategy mapping to be matched with properties of class Interaction
                String[] memberFieldsToBindTo = {"id", "DOI", "leadId", "mean", "potential"}; // String array to store data columns
                strategy.setColumnMapping(memberFieldsToBindTo); // set column mapping to match data columns
                StatefulBeanToCsv<Interaction> beanToCsv = new StatefulBeanToCsvBuilder<Interaction>(writer) // call Csv Builder object from OpenCSV library
                        .withMappingStrategy(strategy) // set strategy mapping for builder
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER) // ignore quote characters when writing
                        .build(); // successfully build the object
                return beanToCsv;
        }
        return null;
    }
    //Create OpenCSV Reader to read data from specific csv file
    public CsvToBean setupCSVBuilderReader(String data, Reader reader) {
        if (data.equals("lead")) { // Check for kind of data (Lead) to be read
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<>(); // Initialize mapping strategy for data columns
            strategy.setType(Lead.class); // Set strategy mapping to be matched with properties of class Lead
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"}; // String array to store data columns
            strategy.setColumnMapping(memberFieldsToBindTo); // set column mapping to match data columns
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy) // set strategy mapping for builder
                    .withSkipLines(1) // ignore empty lines
                    .withIgnoreLeadingWhiteSpace(true) // ignore leading white space
                    .build(); // successfully build the object
            return csvToBean;
        } else if (data.equals("inter")) { // Check for kind of data (Interaction) to be read
                ColumnPositionMappingStrategy<Interaction> strategy = new ColumnPositionMappingStrategy<>(); // Initialize mapping strategy for data columns
                strategy.setType(Interaction.class);  // Set strategy mapping to be matched with properties of class Interaction
                String[] memberFieldsToBindTo = {"id", "DOI", "leadId", "mean", "potential"}; // String array to store data columns
                strategy.setColumnMapping(memberFieldsToBindTo); // set column mapping to match data columns
                CsvToBean<Interaction> csvToBean = new CsvToBeanBuilder<Interaction>(reader) // call Csv Builder object from OpenCSV library
                        .withMappingStrategy(strategy) // set strategy mapping for builder
                        .withSkipLines(1) // ignore empty lines
                        .withIgnoreLeadingWhiteSpace(true) // ignore leading white space
                        .build(); // successfully build the object
                return csvToBean;
        }
        return null;
        }

    public void addInter(Interaction inter)  {
            try ( // create buffer stream of csv file with "write" mode
                    Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            ) {
                    Lead getLead = getLead(inter.getLeadId()); // retrieve valid lead through entered lead's id.
                    String leadId = getLead.getId(); // retrieve lead id from lead csv file
                    StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter", writer); // create builder for writing interaction csv file
                    beanToCsv.write(inter); // write added interaction to the csv file
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
        try ( // create buffer stream of csv file with "read" mode
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
        ) {
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader); // create builder to read data for interaction file
            Interaction foundInter = null; // create placeholder object
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator(); // create Iterator object for traversing list data purpose
            // iterate through list of interactions
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next(); // call next object in the list
                if (inter.getId().equals(id)) {
                    foundInter = inter; // set found-interaction reference to the null object
                }
            }
            String interId = foundInter.getId();
            if(interId != null) { // check if null object is being returned
                System.out.println("Interaction is found");
                return foundInter;
            }
            return foundInter;
        } catch(NullPointerException | IOException err) {
            System.out.println("No ID Found");
        }
        return null;
    }
    public ArrayList<Interaction> getInterAll() {
        try ( // create buffer stream of csv file with "read" mode
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
        ){
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader); // create builder to read data
            ArrayList<Interaction> myInters = new ArrayList<>(); // placeholder array list for storing later retrieving data
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator(); // setup iterator for traversing data
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next(); // acquire the next object in the data list
                myInters.add(inter); // add data object to placeholder array list
            } if (myInters.isEmpty()) {
                System.out.println("No interactions available");
            }
            return myInters; // return list of interactions
        } catch(NullPointerException | IOException err) {
            System.out.println("No interactions found");
        }
        return null;
    }

    public int getLastInterIdNumber() {
        try ( // create buffer stream of csv file with "read" mode
                Reader reader = Files.newBufferedReader(Paths.get(INTERACTION_FILE_PATH));
                ){
            CsvToBean<Interaction> csvToBean = setupCSVBuilderReader("inter", reader); // create builder to read data
            Iterator<Interaction> csvLeadIterator = csvToBean.iterator(); // create iterator for traversing data
            String lastId = ""; // placeholder for the last id string
            while (csvLeadIterator.hasNext()) {
                Interaction inter = csvLeadIterator.next(); // acquire the next object in the list
                lastId = inter.getId(); // store the Id of that object until it reaches the end of the loop we would got the final ID.
            }
            String[] strArray = lastId.split("_"); // split string with regex "_" into array
            return Integer.parseInt(strArray[1]); // return the number part of the ID only
        } catch(IndexOutOfBoundsException | IOException err) {
            return 0;
        }
    }

    public void updateInter(String id, Date DOI, String leadId, String mean, String potential) {
        try ( // create buffer stream of csv file with "write" mode
                Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Interaction> myInters = getInterAll(); // retrieve list of interactions
            Interaction foundInter = null; // placeholder for later found interaction object
            for (Interaction inter : myInters) {
                if (inter.getId().equals(id)) {
                    foundInter = inter; // assign the found interaction to the placeholder
                    break;
                }
            }
            if (foundInter != null) {
                addFile("inter"); // Recreating CSV file for overwriting new modified data.
                // Mapping data's column to Lead's properties
                StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter",writer); // create builder to write data
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
        try ( // create buffer stream of csv file with "write" mode
                Writer writer = Files.newBufferedWriter(Paths.get(INTERACTION_FILE_PATH), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                ) {
            ArrayList<Interaction> myInters = getInterAll(); // retrieve list of interactions
            Interaction foundInter = null; // placeholder for interaction object
            String lastId = "";
            for (Interaction inter : myInters) {
                if (inter.getId().equals(id)) {
                    foundInter = inter; // assign the found interaction to the placeholder
                }
            }
            if (foundInter != null) {
                addFile("inter"); // Recreating CSV file for overwriting new modified data.
                myInters.remove(foundInter);
                // Mapping data's column to Lead's properties
                StatefulBeanToCsv<Interaction> beanToCsv = setupCSVBuilderWriter("inter", writer); // create builder to write data
                beanToCsv.write(myInters); // write new data after deleting one
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

