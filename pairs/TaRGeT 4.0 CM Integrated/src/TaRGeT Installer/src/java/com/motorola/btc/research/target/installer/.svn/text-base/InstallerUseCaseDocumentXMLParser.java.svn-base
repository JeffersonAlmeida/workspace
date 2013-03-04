/*
 * @(#)InstallerUseCaseDocumentXMLParser.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  dhq348   Mar 1, 2007    LIBll54505   Initial creation.
 */
package com.motorola.btc.research.target.installer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.xml.UseCaseDocumentXMLParser;
import com.motorola.btc.research.target.common.ucdoc.xml.XMLErrorHandler;

/**
 * <pre>
 * CLASS:
 * This class is a subclass of UseCaseDocumentXMLParser. It is necessary 
 * due to the need of customizing the paths used to retrieve the schema.
 * </pre>
 */
public class InstallerUseCaseDocumentXMLParser extends UseCaseDocumentXMLParser
{
    /** The schema path */
    private static final String SCHEMA_SOURCE = "schema_use_cases-user_view.xsd";

    /**
     * Constructs a InstallerUseCaseDocumentXMLParser object.
     * 
     * @param xmlFile The XML file
     * @param path The path that will be used to locate the schema.
     * @throws UseCaseDocumentXMLException It is thrown in case of any error on the configuration
     * of <code>DocumentBuilderFactory</code> or during the XML parsing.
     */
    public InstallerUseCaseDocumentXMLParser(File xmlFile, String path)
            throws UseCaseDocumentXMLException
    {
        super();
        XMLErrorHandler handler = new XMLErrorHandler();
        try
        {
            DocumentBuilder docBuilder = this.getConfiguredDocumentBuilder(path);
            docBuilder.setErrorHandler(handler);
            this.xmlUseCaseDocument = docBuilder.parse(xmlFile);
        }
        catch (ParserConfigurationException pce)
        {
            pce.printStackTrace();
            throw new UseCaseDocumentXMLException(pce);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        this.handleErrors(handler);
    }

    /**
     * This method returns the <code>DocumentBuilder</code> instance, configured to the document
     * schema.
     * 
     * @param path The path that will be used to locate the schema.
     * @return The cofigured DocumentBuilder
     * @throws ParserConfigurationException This exception is thrown in case of any error during the
     * configuration.
     */
    private DocumentBuilder getConfiguredDocumentBuilder(String path)
            throws ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        File file = new File(path + SCHEMA_SOURCE);
        factory.setAttribute(JAXP_SCHEMA_SOURCE, file);
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        return docBuilder;
    }

}
