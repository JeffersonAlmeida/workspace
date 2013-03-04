/*
 * @(#)CNLProperties.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   Mar 12, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains the main configuration properties of the system.
 * @author jrm687
 *
 */
public class CNLProperties
{

    private static final String PROPERTIES_FILE = "properties.txt";

    private static final String ACTION_FILE = "action.files";

    private static final String CONDITION_FILE = "condition.files";

    private static final String RESPONSE_FILE = "response.files";

    private static final String LEXICON_FILE = "lexicon.files";
    
    private static final String FAULT_REPORT_FILE = "fault_report.file";

    private Properties cnlProperties;

    private static CNLProperties instance;
    
    /**
     * This method returns a CNLProperties instance. 
     * @return a CNLProperties instance
     */
    public static CNLProperties getInstance()
    {
        if (instance == null)
        {
            instance = new CNLProperties();
        }
        return instance;
    }
    
    /**
     * Class constructor.
     */
    private CNLProperties()
    {
        this.cnlProperties = new Properties();

        try
        {
            File file = new File(PROPERTIES_FILE);
            this.cnlProperties.load(new FileInputStream(file));
        }
        catch (IOException ioe)
        {
            throw new RuntimeException("The file '" + PROPERTIES_FILE + "' could not be loaded.",
                    ioe);
        }
    }
    
    /**
     * Returns the property value.
     * @param property property to be loaded 
     * @return the property value
     */
    private String getProperty(String property)
    {
        String result = this.cnlProperties.getProperty(property);

        if (result == null)
        {
            throw new RuntimeException("The property '" + property
                    + "' could not be loaded from the properties file. Please, verify the '"
                    + PROPERTIES_FILE + "' file.");
        }
        return result;
    }
    
    /**
     * This method returns the action grammar files.
     * @return the action grammar files
     */
    public String[] getActionGrammarFiles()
    {
        return this.getFilesFromProperty(this.getProperty(ACTION_FILE));
    }
    
    /**
     * This method returns the condition grammar files.
     * @return the condition grammar files
     */
    public String[] getConditionGrammarFiles()
    {
        return this.getFilesFromProperty(this.getProperty(CONDITION_FILE));
    }
    
    /**
     * This method returns the response grammar files.
     * @return the response grammar files
     */
    public String[] getResponseGrammarFiles()
    {
        return this.getFilesFromProperty(this.getProperty(RESPONSE_FILE));
    }
    
    /**
     * This method returns the lexicon files.
     * @return the lexicon files
     */
    public String[] getLexiconFiles()
    {
        return this.getFilesFromProperty(this.getProperty(LEXICON_FILE));
    }
    
    /**
     * This method returns the fault report files.
     * @return the fault report files
     */
    public String getFaultReportFile(){
        return this.getFilesFromProperty(this.getProperty(FAULT_REPORT_FILE))[0];
    }
    
    /**
     * This method returns the files that match the specified property.
     * @param property the property to be loaded.
     * @return the files that match the specified property
     */
    private String[] getFilesFromProperty(String property)
    {
        return property.trim().split("\\p{Blank}*\\u002C+\\p{Blank}*");
    }
    
    public static void main(String[] args)
    {
        char ch = ',';
        int chInt = ch;
        
        System.out.println(chInt);
    }
}
