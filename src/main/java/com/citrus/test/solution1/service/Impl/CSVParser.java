package com.citrus.test.solution1.service.Impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.citrus.test.solution1.bean.Record;
import com.citrus.test.solution1.service.GenericParser;

/**
 * This class will parse CSV File.
 * 
 * @author raghunandanG
 */
public class CSVParser implements GenericParser {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CSVParser.class);

    public Set<Record> parse(String filePath) {
        logger.info("File Path : " + filePath);
        Set<Record> uniqueRecordSet = null;
        Record record = null;
        BufferedReader bufferedReader = null;
        try {
            if (filePath != null && filePath.length() > 0) {
                bufferedReader = new BufferedReader(new FileReader(filePath));
                String header = bufferedReader.readLine().trim();
                String line = bufferedReader.readLine();
                String str[] = null;
                uniqueRecordSet = new HashSet<Record>();

                while (line != null && filePath.length() > 0) {
                    str = line.split(",");

                    // Set Data in Record Bean
                    record = new Record();
                    record.setId(Long.parseLong(str[0]));
                    record.setRef(str[1].trim());
                    record.setCleared(Boolean.valueOf(str[2].trim()));
                    uniqueRecordSet.add(record);

                    // Read next line
                    line = bufferedReader.readLine();
                }

                logger.info("Total Records in "+filePath+" : " + uniqueRecordSet.size() + " Header : " + header);
            } else {
                logger.error("File Name is not valid.");
            }
        } catch (Exception exception) {
            logger.error("Exception ocured while reading file : " + exception.getMessage(), exception);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    logger.error("Exception occured while closing stream : " + exception.getMessage(), exception);
                }
            }
        }
        return uniqueRecordSet;
    }

}
