package com.codegen.jet.core.tool;

import com.codegen.jet.dolphins.tools.FileHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

/**
 * Created by RoyChan on 2018/6/6.
 */
public class XMLParseHelper {

    private Document doc;

    private  File fXmlFile;

    public XMLParseHelper(String filePath) throws IOException {
        this.fXmlFile = FileHelper.getFileByClassLoader(filePath);
    }

    public Document initDoc() throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public NodeList getNodeList(String tagName) {
        NodeList nList = null;
        try {
            initDoc();
            nList = doc.getElementsByTagName(tagName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nList;
    }

    public static void main(String argv[]) {

        try {
            XMLParseHelper xmlParseHelper = new XMLParseHelper("unresolveTable.xml");

            System.out.println("Root element :" + xmlParseHelper.initDoc().getDocumentElement().getNodeName());

            NodeList nList = xmlParseHelper.getNodeList("table");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("table : " + eElement.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
