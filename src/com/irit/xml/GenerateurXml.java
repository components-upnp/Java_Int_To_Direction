package com.irit.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by mkostiuk on 19/06/2017.
 */
public class GenerateurXml {

    public String getDocXml(HashMap<String,String> args) throws ParserConfigurationException, TransformerException {

        String namespace = "";
        Document doc;
        DocumentBuilderFactory db = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        builder = db.newDocumentBuilder();
        doc = builder.newDocument();
        Element root = doc.createElementNS(namespace, "TelecommandeEleve");
        doc.appendChild(root);

        Element nbQuestion = doc.createElementNS(namespace, "UDN");
        root.appendChild(nbQuestion);
        nbQuestion.appendChild(doc.createTextNode(args.get("UDN")));

        Element command = doc.createElementNS(namespace, "Commande");
        root.appendChild(command);
        command.appendChild(doc.createTextNode(args.get("COMMANDE")));

        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(source, result);

        return writer.toString();
    }
}
