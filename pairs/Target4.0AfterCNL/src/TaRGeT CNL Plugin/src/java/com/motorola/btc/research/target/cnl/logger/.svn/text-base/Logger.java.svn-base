/*
 * @(#)Logger.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   24/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.motorola.btc.research.target.cnl.controller.CNLFault;
import com.motorola.btc.research.target.cnl.controller.CNLGrammarFault;
import com.motorola.btc.research.target.cnl.controller.CNLLexionFault;
import com.motorola.btc.research.target.cnl.controller.CNLProperties;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;

/**
 * This class represents a text process faults logger.
 * @author 
 * 
 */
public class Logger
{
    private static Logger instance;
    
    /**
     * Class constructor.
     */
    private Logger(){
    }
    
    /**
     * This method returns a Logger instance.
     * @return a Logger instance.
     */
    public static Logger getInstance(){
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }
    
    /**
     * This method is responsible to print the faults in the log file.
     * @param faults a list with the faults
     * @param phoneDocuments a collection of phone documents
     */
    public void printFaults(List<CNLFault> faults, Collection<PhoneDocument> phoneDocuments){ 
        CNLProperties properties = CNLProperties.getInstance();
        String faultReportFile = properties.getFaultReportFile();
        try
        {
            FileWriter fileWriter = new FileWriter(new File(faultReportFile));
            
            fileWriter.write("STEP" + "\t" + "LOCATION" + "\t" + "SENTENCE" + "\t" + "FAULT TYPE" + "\t" + "DETAILS");
            fileWriter.write("\n");
            
            for (CNLFault fault : faults){
                String step = fault.getStep();
                String location = fault.getField().toString();
                String sentence = fault.getSentence();
                String faultType = "";
                if (fault instanceof CNLGrammarFault){
                    faultType = "GRAMMAR";
                }else if (fault instanceof CNLLexionFault){
                    faultType = "LEXICON";
                }
                String details = fault.getDetails();
                
                fileWriter.write(step + "\t" + location + "\t" + sentence + "\t" + faultType + "\t" + details);
                fileWriter.write("\n");
            }
            fileWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
}
