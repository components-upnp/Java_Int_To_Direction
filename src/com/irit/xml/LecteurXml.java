package com.irit.xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by mkostiuk on 19/06/2017.
 */
public class LecteurXml {

    private String udn;
    private String intVal;

    public LecteurXml(String xml) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();

        DefaultHandler handler = new DefaultHandler() {

            boolean isUdn = false;
            boolean isIntVal = false;

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equalsIgnoreCase("UDN"))
                    isUdn = true;
                if (qName.equalsIgnoreCase("INTVAL"))
                    isIntVal = true;
            }

            @Override
            public void characters(char ch[], int start, int length) {
                if (isIntVal) {
                    isIntVal = false;
                    intVal = new String(ch, start, length);
                    System.err.println(intVal);
                }
                if (isUdn) {
                    isUdn = false;
                    udn = new String(ch, start, length);
                }
            }
        };
        sp.parse(new InputSource(new StringReader(xml)), handler);
    }

    public String getUdn() {
        return udn;
    }

    public String getIntVal() {
        return intVal;
    }
}
