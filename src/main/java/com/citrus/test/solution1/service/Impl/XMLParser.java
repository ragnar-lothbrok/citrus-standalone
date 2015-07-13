package com.citrus.test.solution1.service.Impl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.citrus.test.solution1.bean.Record;
import com.citrus.test.solution1.service.GenericParser;

/**
 * This class will parse XML file.
 * 
 * @author raghunandanG
 *
 */
public class XMLParser implements GenericParser {

    private static Logger logger = Logger.getLogger(XMLParser.class);

    private static final long serialVersionUID = 1L;

    public Set<Record> parse(String filePath) {
        Set<Record> recordSet = null;
        Record record = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            if (filePath != null && filePath.length() > 0) {
                builder = factory.newDocumentBuilder();
                File file = new File(filePath);
                Document document = builder.parse(file);
                // Parse Node by node
                document.getDocumentElement().normalize();
                Element element = document.getDocumentElement();
                NodeList list = element.getChildNodes();
                recordSet = new HashSet<Record>();
                for (int i = 0; i < list.getLength(); i++) {
                    NodeList subNodeList = list.item(i).getChildNodes();
                    for (int j = 0; j < subNodeList.getLength(); j++) {
                        // If id attribute is not present then ignore the
                        // record.
                        if (list.item(i).getAttributes() != null
                                && list.item(i).getAttributes().getNamedItem("id").getTextContent() != null) {
                            record = new Record();
                            record.setId(Long.parseLong(list.item(i).getAttributes().getNamedItem("id")
                                    .getTextContent()));
                            if (subNodeList.item(j) != null && "data".equals(subNodeList.item(j).getNodeName())) {
                                record.setRef(subNodeList.item(j).getTextContent());
                            }
                            if (subNodeList.item(j) != null && "name".equals(subNodeList.item(j).getNodeName())) {
                                record.setRef(subNodeList.item(j).getTextContent());
                            }
                            recordSet.add(record);
                        }
                    }
                }
                logger.info("Total Recordsin "+filePath+" file : " + recordSet.size());
            } else {
                logger.error("File Name is not valid.");
            }

        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage(), e);
        } catch (SAXException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return recordSet;
    }
}
