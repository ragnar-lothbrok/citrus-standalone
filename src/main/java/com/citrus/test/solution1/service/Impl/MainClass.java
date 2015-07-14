package com.citrus.test.solution1.service.Impl;

import java.util.Set;

import org.apache.log4j.Logger;

import com.citrus.test.solution1.bean.Record;
import com.citrus.test.solution1.enums.DiffTypeEnum;
import com.citrus.test.solution1.enums.ParserTypeEnum;
import com.citrus.test.solution1.service.GenericParser;
import com.citrus.test.solution1.utility.ParserUtility;

public class MainClass {
    private static final Logger logger = Logger.getLogger(MainClass.class);
    
    public static void main(String[] args) {

        GenericParser csvParser = ParserUtility.getParserInstance(ParserTypeEnum.CSV);
        GenericParser xmlParser = ParserUtility.getParserInstance(ParserTypeEnum.XML);

        Set<Record> csvRecordList = csvParser.parse("Temp_Data.csv");
        Set<Record> xmlRecordList = xmlParser.parse("temp.xml");
        
        logger.info("Operations Started......");
        
        logger.info("Total Records in CSV File :"+csvRecordList.size());
        logger.info("Total Records in XML File :"+xmlRecordList.size());
        
        logger.info("Records count with Cleared false : "+ParserUtility.getCSVRecords(csvRecordList, false).size());
        
        logger.info("Records count with Cleared true : "+ParserUtility.getCSVRecords(csvRecordList, true).size());
        
        logger.info("Records which are present in XML but not in CSV : "+ParserUtility.getDiffRecords(csvRecordList, xmlRecordList, DiffTypeEnum.XML_CSV).size());
        
        logger.info("Records which are present in CSV but not in XML : "+ParserUtility.getDiffRecords(csvRecordList, xmlRecordList, DiffTypeEnum.CSV_XML).size());
        
logger.info("Records which are present in XML but not in CSV : "+ParserUtility.getDiffRecordsAlternate(csvRecordList, xmlRecordList, DiffTypeEnum.XML_CSV).size());
        
        logger.info("Records which are present in CSV but not in XML : "+ParserUtility.getDiffRecordsAlternate(csvRecordList, xmlRecordList, DiffTypeEnum.CSV_XML).size());
        
        logger.info("Operations finished....");
        
    }
}
