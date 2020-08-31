package com.RMIT;

// OpenCsv module import
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.commons.beanutils.Converter;

// java modules import
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.StandardOpenOption;
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
    public void writeFile() {
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
            System.out.println("CSVManager has initialized csv file with headers");
            // csvWriter.writeNext(new String[]{"lead_001", "Minh Ng", "2001-08-24", "true", "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu"});
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }
    public void writeLead() {
        try ( //
              Writer writer = Files.newBufferedWriter(Paths.get("src/main/resources/leads.csv"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

              CSVWriter csvWriter = new CSVWriter(writer,
                      CSVWriter.DEFAULT_SEPARATOR,
                      CSVWriter.NO_QUOTE_CHARACTER,
                      CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                      CSVWriter.DEFAULT_LINE_END);
        ) {
            csvWriter.writeNext(new String[]{"lead_001", "Minh Ng", "2001-08-24", "true", "0934717924", "minhlucky2408@gmail.com", "1002 Ta Quang Buu"});
            System.out.println("successfully added");
        }
        catch (IOException err) {
            err.printStackTrace();
        }
    }
    public void readLead() {
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

            while (csvLeadIterator.hasNext()) {
                Lead lead = csvLeadIterator.next();
                System.out.println("ID: " + lead.getId());
                System.out.println("Name : " + lead.getName());
                System.out.println("Email : " + lead.getEmail());
                System.out.println("PhoneNo : " + lead.getPhone());
                System.out.println("DOB: " + lead.getDOBString());
                System.out.println("Gender: " + lead.getGender());
                System.out.println("==========================");
            }
        } catch(IOException err) {
            err.printStackTrace();
        }
    }
    public void readLastId() {
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
            System.out.println("The last Id: " + lastId);
        } catch(IOException err) {
            err.printStackTrace();
        }
    }

}

