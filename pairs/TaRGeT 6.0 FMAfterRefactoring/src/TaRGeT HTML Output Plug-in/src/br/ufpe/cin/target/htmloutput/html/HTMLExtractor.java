/*
 * @(#)HTMLExtractor.java
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
 * lmn3      Apr 6, 2009                 Initial creation.
 * lmn3      Aug 12, 2008                Refactoring on the extraction method.
 * lmn3      Dec 15, 2009                Refactoring on the extraction method.
 * 
 */
package br.ufpe.cin.target.htmloutput.html;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.tcg.extensions.extractor.ExtractorDocumentExtension;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * Extract a test suite from an HTML file. 
 * 
 * USAGE: 
 * 
 * HTMLExtractor extractor = new HTMLExtractor();
 * List<TextualTestCase> testCases = extractor.extractTestSuite(testSuiteFile);
 */
public class HTMLExtractor implements ExtractorDocumentExtension
{
    
    private static final String IGNORED_TAGS_FILE = FileUtil.getUserDirectory() + FileUtil.getSeparator()
    + "resources" + FileUtil.getSeparator() + "htmlOutputIgnoredTags.txt";
    
    /**
     * This method extracts test cases from a given Excel file.
     * 
     * @param file The file to be read
     * @param headers
     * @return The list of TextualTestCases extracted from the file
     * @throws FileNotFoundException Exception launched in case the given file is not found
     * @throws IOException Exception launched in case something goes wrong during the file reading
     */
    public List<TextualTestCase> extractTestSuite(File file) throws FileNotFoundException,
            IOException
    {
        try
        {
            List<TextualTestCase> testCases = new ArrayList<TextualTestCase>();
            
            BufferedReader in = new BufferedReader(new FileReader(file));
            StringBuffer stringBuffer = new StringBuffer();
            
            String line = "";
            String textContent = "";
            
            List<String> ignoredTags = this.getIgnoredTags();
            
            //starts reading html file
            while (line != null)
            {
                line = in.readLine();

                if (line != null && !line.equals(""))
                {

                    Source source = new Source(line);

                    List<Segment> ignoredSegments = new ArrayList<Segment>();
                    
                    //checking if the text to be parsed contains tags that should be ignored
                    for (String ignoredTag : ignoredTags)
                    {
                        if (line.contains(ignoredTag))
                        {
                            for (int i = 0; i < line.length() && line.indexOf(ignoredTag, i) >= 0; i++)
                            {
                                int indexIgnoredTag = line.indexOf(ignoredTag, i);
                                
                                Segment ignoredSegment = new Segment(source, indexIgnoredTag, indexIgnoredTag
                                        + ignoredTag.length());
                                
                                //indicates to the parser which tags should be ignored
                                ignoredSegments.add(ignoredSegment);

                                i = indexIgnoredTag + ignoredTag.length();
                            }
                        }
                    }
                    
                    //ignoring tags when parsing the html code
                    source.ignoreWhenParsing(ignoredSegments);
                    
                    //removing html code
                    textContent = source.getTextExtractor().toString().trim();
                    if (!textContent.equals(""))
                    {
                        stringBuffer.append(textContent + "\n");
                    }
                }
            }
            
            String htmlText = stringBuffer.toString();

            // separating the test cases
            String[] testCasesText = null; 
            testCasesText =  htmlText.split("XtestCaseX");

            for (int i = 1; i < testCasesText.length - 1; i++)
            {
                // retrieving test case fields
                String[] testCaseLines = null; 
                testCaseLines = testCasesText[i].split("\n");

                String tcIdHeader = testCaseLines[1].replaceAll("Test Case ID: ", "").trim();
                String tcId = testCaseLines[2].replaceAll("ID: ", "").trim();
                String regressionLevel = testCaseLines[3].replaceAll("Regression Level: ", "").trim();
                String executionType = testCaseLines[4].replaceAll("Execution Type: ", "").trim();
                String description = testCaseLines[5].replaceAll("Description: ", "").trim();
                String objective = testCaseLines[6].replaceAll("Objective: ", "").trim();
                String useCaseReferences = testCaseLines[7].replaceAll("Use Case References: ", "").trim();
                String featureId = "";
                String useCaseId = "";     
                
                String[] temp = null; 
                temp = useCaseReferences.split("X");

                if (temp.length > 0)
                {
                    featureId = temp[0];
                    useCaseId = temp[1];
                }
                
                String requirements = testCaseLines[8].replaceAll("Requirements: ", "").trim();
                String setups = testCaseLines[9].replaceAll("Setups: ", "").trim();
                String initialConditions = testCaseLines[10].replaceAll("Initial Conditions: ", "").trim();
                String note = "";
                String finalConditions = "";
                String cleanup = "";
                String status = "";
                           
                List<TextualStep> steps = new ArrayList<TextualStep>();
                

                // retrieving test case fields
                for (int l = 12; l < testCaseLines.length; l++)
                {
                    if (testCaseLines[l].contains("XXX"))
                    {
                        String [] stepText = null; 
                        stepText = testCaseLines[l].split("XXX");
                        
                        String stepId = stepText[0].replaceAll("\\)", "").trim();
                        String action = stepText[1].trim();
                        String response = stepText[2].trim();

                        StepId id = new StepId(featureId, useCaseId, stepId);
                        TextualStep step = new TextualStep(action, response, id);
                        steps.add(step);
                    }
                                        
                    else if(testCaseLines[l].startsWith("Final Conditions:"))
                    {
                        finalConditions = testCaseLines[l].replaceAll("Final Conditions: ", "").trim();
                    }
                    else if(testCaseLines[l].startsWith("Cleanup:"))
                    {
                        finalConditions = testCaseLines[l].replaceAll("Cleanup: ", "").trim();
                    }
                    else if(testCaseLines[l].startsWith("Notes:"))
                    {
                        finalConditions = testCaseLines[l].replaceAll("Notes: ", "").trim();
                    }                                        
                    
                }

                // mounting the test case
                TextualTestCase tc = new TextualTestCase(Integer.parseInt(tcId), tcIdHeader, steps,
                        executionType, regressionLevel, description, objective, requirements,
                        setups, initialConditions, note, finalConditions, cleanup,
                        useCaseReferences, status, featureId);

                testCases.add(tc);
            }
            return testCases;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Returns a list containing html tags defined by the user that should be ignored.
     * These tags are retrieved from a resource file.
     * @return a list containing html tags that will be ignoring by the parser.
     * @throws IOException
     */
    private List<String> getIgnoredTags() throws IOException{
        List<String> ignoredTags = new ArrayList<String>();
        
        BufferedReader in;
        in = new BufferedReader(new FileReader(new File(HTMLExtractor.IGNORED_TAGS_FILE)));
        String line = in.readLine();
        
        while (line != null)
        {
            if(!line.equals("")){
                ignoredTags.add(line);
            }
            
            line = in.readLine();
        }
        
        return ignoredTags;
    }

}
