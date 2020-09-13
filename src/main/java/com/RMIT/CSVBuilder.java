package com.RMIT;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;

import java.io.Reader;
import java.io.Writer;

public class CSVBuilder {
    private static CSVBuilder single_instance = null;
    private CSVBuilder() {}
    public static CSVBuilder getInstance() {
        if (single_instance == null) {
            single_instance = new CSVBuilder();
        }
        return single_instance;
    }
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
}
