/*
 * @(#)XMLErrorHandler.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 15, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 16, 2007    LIBkk11577   Added javadoc.
 * dhq348   Jan 18, 2007    LIBkk11577   Reowrk of inspection LX135556.
 */
package com.motorola.btc.research.target.common.ucdoc.xml;

import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;

/**
 * This class handles all kind of problems found during the parse of a xml file. It must be
 * instantiated and set as the handler of a DocumentBuilder.
 * 
 * <pre>
 * CLASS:
 * This class handles in a proper manner all the problems found during the xml file parsing. 
 * It distinguishes the problems in warnings, errors and fatal errors in a way that it has
 * an appropriate method for each of them. Every method that handles a parse problem receives
 * a <code>SAXParseException</code> thrown by the <code>DocumentBuilder</code> operation.
 * 
 * RESPONSIBILITIES:
 * 1) Handles all XML problems found during a parsing operation.
 *
 * USAGE:
 * 
 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 * DocumentBuilder docBuilder = factory.newDocumentBuilder();
 * <b>XMLErrorHandler handler = new XMLErrorHandler();
 * docBuilder.setErrorHandler(handler);</b>
 * Document document = docBuilder.parse(xmlFile);
 */
public class XMLErrorHandler implements ErrorHandler
{
    /**
     * Indicates that an error was found during an XML file parsing
     */
    private boolean error;

    /**
     * Indicates that a fatal error was found during an XML file parsing
     */
    private boolean fatalError;

    /**
     * Indicates that a warning was found during an XML file parsing
     */
    private boolean warning;

    /**
     * The list of found warnings
     */
    private Collection<UseCaseDocumentXMLException> warnings;

    /**
     * The list of found errors
     */
    private Collection<UseCaseDocumentXMLException> errors;

    /**
     * The list of found fatal errors
     */
    private Collection<UseCaseDocumentXMLException> fatalErrors;

    /**
     * This constructor sets the existence of errors or warning to false and instantiates the
     * collection of warnings and errors.
     */
    public XMLErrorHandler()
    {
        this.error = false;
        this.fatalError = false;
        this.warning = false;
        this.warnings = new ArrayList<UseCaseDocumentXMLException>();
        this.errors = new ArrayList<UseCaseDocumentXMLException>();
        this.fatalErrors = new ArrayList<UseCaseDocumentXMLException>();
    }

    /**
     * Handles any error found when parsing an XML file. This method captures all thrown
     * <code>SAXParseException</code> exceptions.
     * 
     * @param exception The <code>SAXParseException</code> thrown.
     */
    public void error(SAXParseException exception)
    {
        this.errors.add(new UseCaseDocumentXMLException(exception));
        this.error = true;
        System.out.println("error " + exception.getMessage());
    }

    /**
     * Handles any fatal error found when parsing a XML file. This method captures all thrown
     * <code>SAXParseException<code> exceptions.
     * 
     * @param exception The <code>SAXParseException</code> thrown.
     */
    public void fatalError(SAXParseException exception)
    {
        this.fatalErrors.add(new UseCaseDocumentXMLException(exception));
        this.fatalError = true;
        System.out.println("fatal error " + exception.getMessage());
    }

    /**
     * Handles any warning found when parsing an XML file. This method captures all thrown
     * <code>SAXParseException</code> exceptions.
     * 
     * @param exception The <code>SAXParseException</code> thrown.
     */
    public void warning(SAXParseException exception)
    {
        this.warnings.add(new UseCaseDocumentXMLException(exception));
        this.warning = true;
        System.out.println("warning " + exception.getMessage());
    }

    /**
     * Get method for <code>error</code> attribute.
     * 
     * @return <i>True</i> an error was found. <i>False</i> otherwise.
     */
    public boolean isError()
    {
        return error;
    }

    /**
     * Get method for <code>fatalError</code> attribute.
     * 
     * @return <i>True</i> a fatal error was found. <i>False</i> otherwise.
     */
    public boolean isFatalError()
    {
        return fatalError;
    }

    /**
     * Get method for <code>warning</code> attribute.
     * 
     * @return <i>True</i> a warning was found. <i>False</i> otherwise.
     */
    public boolean isWarning()
    {
        return warning;
    }

    /**
     * Get method for <code>errors</code> attribute.
     * 
     * @return The found errors.
     */
    public Collection<UseCaseDocumentXMLException> getErrors()
    {
        return errors;
    }

    /**
     * Get method for <code>fatalErrors</code> attribute.
     * 
     * @return The found fatal errors.
     */
    public Collection<UseCaseDocumentXMLException> getFatalErrors()
    {
        return fatalErrors;
    }

    /**
     * Get method for <code>warnings</code> attribute.
     * 
     * @return The found warnings.
     */
    public Collection<UseCaseDocumentXMLException> getWarnings()
    {
        return warnings;
    }

}
