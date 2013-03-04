/*
 * @(#)Logger.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   24/07/2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import br.ufpe.cin.target.cnl.controller.CNLFault;
import br.ufpe.cin.target.cnl.controller.CNLGrammarFault;
import br.ufpe.cin.target.cnl.controller.CNLLexionFault;
import br.ufpe.cin.target.cnl.controller.CNLProperties;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;


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
     * @param useCaseDocuments a collection of use case documents
     */
    public void printFaults(List<CNLFault> faults, Collection<UseCaseDocument> useCaseDocuments){ 
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
