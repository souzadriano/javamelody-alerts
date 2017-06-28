package com.souzadriano.javamelodyalert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class LoadConfig {

    static Config getConfig(String directory) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(directory + "/javamelodyalert_config.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        Config config = new Config();
        config.setEmails(getEmails(doc));
        config.setConditions(getConditions(doc));
        return config;
    }

    private static Collection<Condition> getConditions(Document doc) {
        Collection<Condition> conditions = new ArrayList<Condition>();
        NodeList nodeConditions = doc.getElementsByTagName("condition");
        for (int i = 0; i < nodeConditions.getLength(); i++) {
            Element elementCondition = (Element) nodeConditions.item(i);
            Condition condition = new Condition();
            condition.setPeriod(Integer.parseInt(elementCondition.getElementsByTagName("period").item(0).getTextContent()));
            condition.setVariable(VariableEnum.parseVariable(elementCondition.getElementsByTagName("variable").item(0).getTextContent()));
            condition.setSign(SignEnum.parseSign(elementCondition.getElementsByTagName("sign").item(0).getTextContent()));
            condition.setValue(new BigDecimal(elementCondition.getElementsByTagName("value").item(0).getTextContent()));
            conditions.add(condition);
        }
        return conditions;
    }

    private static Collection<String> getEmails(Document doc) {
        Collection<String> emails = new HashSet<String>();

        NodeList nodeEmails = doc.getElementsByTagName("email");
        for (int i = 0; i < nodeEmails.getLength(); i++) {
            emails.add(nodeEmails.item(i).getTextContent());
        }
        return emails;
    }
}
