package com.citrus.test.solution1.utility;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.citrus.test.solution1.bean.Record;
import com.citrus.test.solution1.enums.DiffTypeEnum;
import com.citrus.test.solution1.enums.ParserTypeEnum;
import com.citrus.test.solution1.service.GenericParser;
import com.citrus.test.solution1.service.Impl.CSVParser;
import com.citrus.test.solution1.service.Impl.XMLParser;

/**
 * Class will generate data from Bean to XML.
 * 
 * @author raghunandanG
 */
public class ParserUtility {

    private static final MessageFormat fmt = new MessageFormat(
            "<item id=\"{0}\"><name>{1}</name><data>{2}</data></item>");

    public static String convertBeanToXML(Set<Record> recordSet) {
        StringBuilder builder = new StringBuilder();
        builder.append("<items>");
        if (recordSet != null && recordSet.size() != 0) {
            Iterator<Record> recoIterator = recordSet.iterator();
            while (recoIterator.hasNext()) {
                Record record = recoIterator.next();
                if (record.getId() % 3 == 0) {
                    builder.append(fmt.format(new Object[] { record.getId(), record.getRef(), record.getCleared() }))
                            .append("\n");
                }
            }
        }
        builder.append("</items>");
        System.out.println(builder.toString());
        return builder.toString();
    }

    public static GenericParser getParserInstance(ParserTypeEnum parserTypeEnum) {
        GenericParser genericParser = null;

        if (parserTypeEnum != null) {
            if (parserTypeEnum.equals(ParserTypeEnum.CSV)) {
                genericParser = new CSVParser();
            } else if (parserTypeEnum.equals(ParserTypeEnum.XML)) {
                genericParser = new XMLParser();
            }
        }
        return genericParser;
    }

    /**
     * Method will return either cleared or not cleared items.
     * 
     * @param allRecords
     * @param cleared
     * @return
     */
    public static Set<Record> getCSVRecords(Set<Record> allRecords, boolean cleared) {
        Set<Record> modifedSet = new HashSet<Record>();
        if (allRecords.size() > 0) {
            Iterator<Record> iterator = allRecords.iterator();
            while (iterator.hasNext()) {
                Record record = iterator.next();
                if (record.getCleared() != null) {
                    if (cleared == record.getCleared()) {
                        modifedSet.add(record);
                    }
                }
            }
        }
        return modifedSet;
    }

    /**
     * Method will return Records on the basis of DiffTypeEnum
     * 
     * @param allCSVRecords
     * @param allXMLRecords
     * @param diffTypeEnum
     * @return
     */
    public static Set<Record> getDiffRecords(Set<Record> allCSVRecords, Set<Record> allXMLRecords,
            DiffTypeEnum diffTypeEnum) {
        Set<Record> modifedSet = new HashSet<Record>();
        if (allCSVRecords.size() > 0 || allXMLRecords.size() > 0) {
            if (diffTypeEnum != null) {
                if (diffTypeEnum.equals(DiffTypeEnum.XML_CSV)) {
                    Iterator<Record> iterator = allXMLRecords.iterator();
                    while (iterator.hasNext()) {
                        Record record = iterator.next();
                        if (!allCSVRecords.contains(record)) {
                            modifedSet.add(record);
                        }
                    }
                } else if (diffTypeEnum.equals(DiffTypeEnum.CSV_XML)) {
                    Iterator<Record> iterator = allCSVRecords.iterator();
                    while (iterator.hasNext()) {
                        Record record = iterator.next();
                        if (!allXMLRecords.contains(record)) {
                            modifedSet.add(record);
                        }
                    }
                }
            }
        }
        return modifedSet;
    }
}
