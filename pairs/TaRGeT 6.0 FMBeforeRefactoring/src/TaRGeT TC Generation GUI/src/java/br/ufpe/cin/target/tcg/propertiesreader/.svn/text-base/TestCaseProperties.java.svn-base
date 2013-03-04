/*
 * @(#)InterruptionAssignmentLabelProvider.java
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
 * dhq348    Nov 26, 2007   LIBoo10574   Initial creation.
 * dhq348    Jan 22, 2008   LIBoo10574   Rework after inspection LX229632.
 * tnd783    Apr 07, 2008   LIBpp71785   Added new properties (PRINT_HIDDEN_TCID and KEEP_REQUIREMENTS) according to CR
 * tnd783    Jul 21, 2008   LIBpp71785   Rework after inspection LX285039. 
 * tnd783    Sep 12, 2008   LIBqq51204	 New properties added.
 * dwvm83	 Sep 30, 2008	LIBqq51204	 Rework after inspection LX302177.
 */

package br.ufpe.cin.target.tcg.propertiesreader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import br.ufpe.cin.target.common.util.FileUtil;

/**
 * Class that reads and edits some properties of <code>testcase.properties</code> file.
 * 
 * <pre>
 * CLASS:
 * This class reads and edits some properties of <code>testcase.properties</code> file.

 * RESPONSIBILITIES:
 * 1) Provide the properties set in the properties file regarding test cases generation
 * 2) Edits the properties set in the properties file  
 *
 * USAGE:
 * TestCaseProperties testCaseProperties = TestCaseProperties.getInstance();
 */
public class TestCaseProperties
{
    /**
     * The test case properties.
     */

    private Properties properties;


    /**
     * The path of the properties file.
     */
    private String filename;

    /**
     * Empty field property id.
     */
    private static final String EMPTY_FIELD = "tcg.empty_field";

    /**
     * The test case id property.
     */
    private static final String TESCASE_ID = "tcg.tescase_id";

    /**
     * The objective prefix property id.
     */
    private static final String OBJECTIVE_PREFIX = "tcg.objective_prefix";

    /**
     * The keep requirements property id.
     */
    private static final String KEEP_REQUIREMENTS = "tcg.keep_requirements";

    /**
     * The print hidden test case id property id.
     */
    private static final String PRINT_HIDDEN_TCID = "tcg.print_hidden_tcid";

    /**
     * The print use case description property id.
     */
    
    private static final String PRINT_USE_CASE_DESCRIPTION = "tcg.print_use_case_description";

    /**
     * The print flow description property id.
     */
    
    private static final String PRINT_FLOW_DESCRIPTION = "tcg.print_flow_description";

    /**
     * The test case initial id property.
     */
    
    private static final String TESCASE_INITIAL_ID = "tcg.tescase_initial_id";
    
    /**
     * The test case initial id property.
     */
    
    //INSPECT - Lais new attributte
    private static final String TESCASE_COMPONENT_KEYWORD = "tcg.componentKeyword";
    
    /**
     * The test case initial id property.
     */
    
    //INSPECT - Lais new attributte
    /**
     * The name of the requirements document to be set in test suites compatible to TestLink tool.
     */
    private static final String TESCASE_REQUIREMENTS_SPECIFICATION_TITLE = "tcg.reqSpecTitle";

    /**
     * The single instance of <code>TestCaseProperties</code>.
     */
    private static TestCaseProperties instance = null;

    /**
     * Private constructor.
     */
    private TestCaseProperties()
    {
        try
        {
            String separator = FileUtil.getSeparator();
            filename = FileUtil.getUserDirectory() + separator + "resources" + separator + "properties" + separator
                    + "testcase.properties";
            properties = new Properties();
            properties.load(new FileInputStream(filename));
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

    /**
     * Returns a reference to the singleton of this class.
     * 
     * @return The single instance of <code>TestCaseProperties</code>. If it was not instantiated
     * then instantiates it.
     */
    public static TestCaseProperties getInstance()
    {
        if (instance == null)
        {
            instance = new TestCaseProperties();
        }
        return instance;
    }
    
    /**
     * Returns a string to replace empty fields. The string is defined in the properties file.
     * 
     * @return A string to replace empty fields.
     */
    public String getEmptyField()
    {
        String result = "";
        try
        {
            result = properties.getProperty(EMPTY_FIELD);
        }
        catch (Exception e)
        {
            result = "";
        }
        return result;
    }

    /**
     * Sets a string to replace empty fields. The string is defined in the properties file.
     * 
     * @param emptyField The string to replace blank fields.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setEmptyField(String emptyField) throws IOException
    {
        properties.setProperty(EMPTY_FIELD, emptyField);
    }

    /**
     * Returns a string to replace test case IDs. The string is defined in the properties file.
     * 
     * @return A string to replace test case IDs.
     */
    public String getTestCaseId()
    {
        String result = "";
        try
        {
            result = properties.getProperty(TESCASE_ID);
        }
        catch (Exception e)
        {
            result = "TC_<tc_id>";
        }
        return result;
    }

    /**
     * Sets a string to replace test case IDs. The string is defined in the properties file.
     * 
     * @param testCaseId The string to replace test case IDs.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setTestcaseId(String testCaseId) throws IOException
    {
        properties.setProperty(TESCASE_ID, testCaseId);
    }

    /**
     * Returns a string to replace the objective prefix. The string is defined in the properties
     * file.
     * 
     * @return A string to replace the objective prefix.
     */
    public String getObjectivePrefix()
    {
        String result = "";
        try
        {
            result = properties.getProperty(OBJECTIVE_PREFIX);
        }
        catch (Exception e)
        {
            result = "";
        }
        return result;
    }

    /**
     * Sets a string to replace the objective prefix. The string is defined in the properties file.
     * 
     * @param objectivePrefix The string to replace the objective prefix.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setObjectivePrefix(String objectivePrefix) throws IOException
    {
        properties.setProperty(OBJECTIVE_PREFIX, objectivePrefix);
    }

    /**
     * Returns a boolean indicating the keep requirements property. This property is defined in the
     * properties file.
     * 
     * @return A boolean indicating the keep requirements property.
     */
    public boolean isKeepingRequirements()
    {
        boolean result;
        try
        {
            String value = properties.getProperty(KEEP_REQUIREMENTS);
            result = Boolean.parseBoolean(value.trim());

        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Sets a boolean indicating the keep requirements property. This property is defined in the
     * properties file.
     * 
     * @param objectivePrefix The boolean indicating the keep requirements property.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setKeepingRequirements(boolean keepingRequirements) throws IOException
    {
        properties.setProperty(KEEP_REQUIREMENTS, String.valueOf(keepingRequirements));
        
    }

    /**
     * Returns a boolean indicating the print hidden test case id property. This property is defined
     * in the properties file.
     * 
     * @return A boolean indicating the print hidden test case id property.
     */
    public boolean isPrintingHiddenTestCaseId()
    {
        boolean result;
        try
        {
            String value = properties.getProperty(PRINT_HIDDEN_TCID);
            result = Boolean.parseBoolean(value.trim());
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

    /**
     * Sets a boolean indicating the print hidden test case id property. This property is defined in
     * the properties file.
     * 
     * @param printingHiddenTestCaseId The boolean indicating the print hidden test case id property.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setPrintingHiddenTestCaseId(boolean printingHiddenTestCaseId) throws IOException
    {
        properties.setProperty(PRINT_HIDDEN_TCID, String.valueOf(printingHiddenTestCaseId));
    }

    /**
     * Returns a PrintingDescription enum indicating the print use case description property. This
     * property is defined in the properties file.
     * 
     * @return A PrintingDescription indicating the print use case description property.
     */
    
    public PrintingDescription getPrintUseCaseDescription()
    {
        PrintingDescription result = null;
        try
        {
            int value = Integer.parseInt(properties.getProperty(PRINT_USE_CASE_DESCRIPTION));
            
            switch (value)
            {
                case 0:
                    result = PrintingDescription.LAST;
                    break;
                case 1:
                    result = PrintingDescription.ALL;
                    break;
                case 2:
                    result = PrintingDescription.NONE;
                    break;
                default:
                    result = PrintingDescription.NONE;
                    break;
            }
        }
        catch (Exception e)
        {
            result = PrintingDescription.NONE;
        }
        return result;
    }

    /**
     * Sets a PrintingDescription enum indicating the print use case description property. This
     * property is defined in the properties file.
     * 
     * @param printUseCaseDescription The PrintingDescription enum indicating the print use case description
     * property.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setPrintUseCaseDescription(int selectionIndex)
            throws IOException
    {
        properties.setProperty(PRINT_USE_CASE_DESCRIPTION, Integer.toString(selectionIndex));
    }

    /**
     * Returns a PrintingDescription enum indicating the print flow description property. This
     * property is defined in the properties file.
     * 
     * @return A PrintingDescription indicating the print flow description property.
     */
    
    public PrintingDescription getPrintFlowDescription()
    {
        PrintingDescription result = null;
        try
        {
            int value = Integer.parseInt(properties.getProperty(PRINT_FLOW_DESCRIPTION));
            
            switch (value)
            {
                case 0:
                    result = PrintingDescription.LAST;
                    break;
                case 1:
                    result = PrintingDescription.ALL;
                    break;
                case 2:
                    result = PrintingDescription.NONE;
                    break;
                default:
                    result = PrintingDescription.NONE;
                    break;
            }
        }
        catch (Exception e)
        {
            result = PrintingDescription.NONE;
        }
        return result;
    }

    /**
     * Sets a PrintingDescription enum indicating the print flow description property. This property
     * is defined in the properties file.
     * 
     * @param printFlowDescription The PrintingDescription enum indicating the print flow description
     * property.
     * @throws IOException In case something goes wrong during the file writing.
     */
   
    public void setPrintFlowDescription(int selectionIndex)
            throws IOException
    {
        properties.setProperty(PRINT_FLOW_DESCRIPTION, Integer.toString(selectionIndex));
    }

    /**
     * Enumeration that represents the different values of the print use case description property.
     */
    
    public enum PrintingDescription {
        NONE, ALL, LAST;
    }

    /**
     * Returns an integer indicating the first id of the test cases to be generated. The integer is
     * defined in the properties file.
     * 
     * @return The initial id of the test cases to be generated.
     */
    
    public int getTestCaseInitialId()
    {
        int result =1;
        try
        {
            result = new Integer(properties.getProperty(TESCASE_INITIAL_ID));
            
        }
        catch (Exception e)
        {
            result = 1;
        }
        return result;
    }

    /**
     * Sets an integer indicating the first id of the test cases to be generated. This property is
     * defined in the properties file.
     * 
     * @param initialId The integer indicating the first id of the test cases to be generated.
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void setTestCaseInitialId(int initialId) throws IOException
    {
        properties.setProperty(TESCASE_INITIAL_ID, String.valueOf(initialId));
        
    }
    
    //INSPECT - Lais new method
    public String getComponentKeywordValue()
    {
        return properties.getProperty(TESCASE_COMPONENT_KEYWORD);
    }
    
    //INSPECT - Lais new method
    public void setComponentKeywordValue(String componentKeyword) throws IOException
    {
        properties.setProperty(TESCASE_COMPONENT_KEYWORD, String.valueOf(componentKeyword));
        
    }
    
    //INSPECT - Lais new method
    public String getRequirementsSpecificationTitle()
    {
        return properties.getProperty(TESCASE_REQUIREMENTS_SPECIFICATION_TITLE);
    }
    
    //INSPECT - Lais new method
    public void setRequirementsSpecificationTitle(String requirementsSpecificationTitle) throws IOException
    {
        properties.setProperty(TESCASE_REQUIREMENTS_SPECIFICATION_TITLE, String.valueOf(requirementsSpecificationTitle));
        
    }  
    

    /**
     * Updates the property files with the properties set by the clients of this class.
     * 
     * @throws IOException In case something goes wrong during the file writing.
     */
    
    public void store() throws IOException
    {
        FileOutputStream fos = new FileOutputStream(filename);
        properties.store(fos, "");
    }
}
