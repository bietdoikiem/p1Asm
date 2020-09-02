package com.RMIT;

// OpenCsv module import
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

// java modules import
import javax.management.StandardMBean;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class CSVManager  {
    private static CSVManager single_instance = null;
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
    public void addFile() {
        try ( // Parenthesis try block to close stream resource after utilized
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/leads.csv"));

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
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }
    public void addLead(Lead lead) {
        try ( //
              Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/leads.csv"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        ) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            StatefulBeanToCsv<Lead> beanToCsv = new StatefulBeanToCsvBuilder<Lead>(writer)
                    .withMappingStrategy(strategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            //List<Lead> myLead = new ArrayList<>();
            //myLead.add(lead);
            beanToCsv.write(lead);
        }
        catch (IOException err) {
            err.printStackTrace();
        } catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }
    public Lead getLead(String id) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/leads.csv"))
        ) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Lead foundLead = null;
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                }
            }
            return foundLead;
        } catch(IOException err) {
            err.printStackTrace();
        } catch(NullPointerException err) {
            System.out.println("No ID Found");
        }
        return null;
    }
    public ArrayList<Lead> getLeadAll() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/leads.csv"))
                ) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            ArrayList<Lead> myLeads = new ArrayList<>();
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();

            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                myLeads.add(lead);
                /*
                System.out.println("ID: " + lead.getId());
                System.out.println("Name : " + lead.getName());
                System.out.println("Email : " + lead.getEmail());
                System.out.println("PhoneNo : " + lead.getPhone());
                System.out.println("DOB: " + lead.getDOBString());
                System.out.println("Gender: " + lead.getGender());
                System.out.println("==========================");
                */
            }
            return myLeads;
        } catch(IOException err) {
            err.printStackTrace();
        } catch(NullPointerException err) {
            System.out.println("No leads found");
        }
        return null;
    }

    public int getLastLeadId() {
        try (
                Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/leads.csv"))
        ) {
            ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
            strategy.setType(Lead.class);
            String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
            strategy.setColumnMapping(memberFieldsToBindTo);
            CsvToBean<Lead> csvToBean = new CsvToBeanBuilder<Lead>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<Lead> csvLeadIterator = csvToBean.iterator();
            String lastId = "";
            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                 lastId = lead.getId();
            }
            String[] strArray = lastId.split("_");
            return Integer.parseInt(strArray[1]);
        } catch(IOException err) {
            err.printStackTrace();
        } catch(IndexOutOfBoundsException err) {
            return 0;
        }
        return 0;
    }

    public void updateLead(String id, String name, Date DOB, boolean gender, String phone, String email, String address) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/leads.csv"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ) {

            ArrayList<Lead> myLeads = getLeadAll();
            Lead foundLead = null;
            for (Lead lead: myLeads) {
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                    System.out.println("Found already");
                }
            }
            if (foundLead != null) {
                addFile(); // Recreating CSV file for overwriting new modified data.
                ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
                strategy.setType(Lead.class);
                String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
                strategy.setColumnMapping(memberFieldsToBindTo);
                StatefulBeanToCsv<Lead> beanToCsv = new StatefulBeanToCsvBuilder<Lead>(writer)
                        .withMappingStrategy(strategy)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .build();

                foundLead.setName(name);
                foundLead.setDOB(DOB);
                foundLead.setGender(gender);
                foundLead.setPhone(phone);
                foundLead.setEmail(email);
                foundLead.setAddress(address);
                beanToCsv.write(myLeads);
                System.out.println("Update successfully");

            } else {
                System.out.println("Lead not found. Please try again");
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
        catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }

    public void deleteLead(String id) {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/leads.csv"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        ) {
            ArrayList<Lead> myLeads = getLeadAll();
            Lead foundLead = null;
            for (Lead lead: myLeads) {
                if (lead.getId().equals(id)) {
                    foundLead = lead;
                    System.out.println("Found already");
                }
            }
            if (foundLead != null) {
                addFile(); // Recreating CSV file for overwriting new modified data.
                myLeads.remove(foundLead);
                ColumnPositionMappingStrategy<Lead> strategy = new ColumnPositionMappingStrategy<Lead>();
                strategy.setType(Lead.class);
                String[] memberFieldsToBindTo = {"id", "name", "DOB", "gender", "phone", "email", "address"};
                strategy.setColumnMapping(memberFieldsToBindTo);
                StatefulBeanToCsv<Lead> beanToCsv = new StatefulBeanToCsvBuilder<Lead>(writer)
                        .withMappingStrategy(strategy)
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                        .build();
                beanToCsv.write(myLeads);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Lead not found. Please try again");
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
        catch (CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        } catch (CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }
    }


}

