/*
 * @(#)TestSuiteXMLGenerator.java
 *
 * Copyright (c) <2007-2009> <Research Project Team – CIn-UFPE>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE
 *
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * lmn3      17/11/2009      LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.tcg.extensions.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InformationTestCase;
import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.TCGUtil;
import br.ufpe.cin.target.tcg.util.TraceabilityMatrixGenerator;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * This class is responsible for generating a XML file from a given test suite.
 *
 */
//INSPECT Lais new class
public class TestSuiteXMLGenerator
{
    /*
     * Class Constructor
     */
    public TestSuiteXMLGenerator()
    {
        
    }
    
    /**
     * 
     * Generating a XML file from a given test suite
     * @param testCases the given test suite
     * @return a XML file representing the given test suite
     * @throws IOException
     */
    //INSPECT - Lais inclusão de information test case para geração da traceability matrix
    public File generateXMLFile(List<TextualTestCase> testCases, String testSuiteName, InformationTestCase informationTestCase) throws IOException
    {
        String testSuiteDir = ProjectManagerExternalFacade.getInstance().getCurrentProject()
        .getTestSuiteDir() + FileUtil.getSeparator();
        
        File file = new File(testSuiteDir + "temp.xml");
        
        XStream xmlStream = new XStream();

        xmlStream.alias("testCase",
                br.ufpe.cin.target.tcg.extractor.TextualTestCase.class);
        xmlStream.alias("step", br.ufpe.cin.target.tcg.extractor.TextualStep.class);
        xmlStream.alias("testCases", List.class);
        
        this.replaceNotUnicodeCharacters(testCases);
        String xmlContent = xmlStream.toXML(testCases);
        
        StringBuffer traceabilityMatrixes = new StringBuffer();
        
        String rtmRequirements = this.generateMatrix("rtmRequirement",TraceabilityMatrixGenerator.getInstance().createRTMRequirements(),
                "rtmRequirements", "requirement", "useCase", "REQ", "UC");
        String rtmSystemTest = this.generateMatrix("rtmSystemTest", TraceabilityMatrixGenerator.getInstance().createRTMSystemTest(testCases, informationTestCase), "rtmSystemTests",
                "requirement", "systemTestCase", "REQ", "TES");
        String rtmUseCase = this.generateMatrix("rtmUseCase", TraceabilityMatrixGenerator.getInstance().createRTMUseCase(testCases, informationTestCase), "rtmUseCases", "useCase",
                "systemTestCase", "UC", "TES");
        
        traceabilityMatrixes.append(rtmRequirements);
        traceabilityMatrixes.append(rtmSystemTest);
        traceabilityMatrixes.append(rtmUseCase);
        
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n");

        fileWriter.write("<testSuite name=\"" + testSuiteName + "\">\n");
        fileWriter.write(xmlContent);
        fileWriter.write(traceabilityMatrixes.toString());
        fileWriter.write("\n</testSuite>");

        fileWriter.close();
        
        return file;
        
        
    }
    
    /**
     * Creates a traceability matrix that links use cases to the system test cases.
     */
    protected String generateMatrix(String tagName, HashMap<String, List<String>> columns, String title,
            String column1Title, String column2Title, String column1Tag, String column2Tag)
    {
        StringBuffer xmlFile = new StringBuffer();
        xmlFile.append("\n<" + title + ">");

        for (String string : TCGUtil.sort(columns.keySet()))
        {
            String openTag = "\n\t<" + tagName + ">";
            
            String nameTag = "\n\t\t<" + column1Title + ">" + column1Tag + " - " + string + "</"
                    + column1Title + ">";
            String contentTag = this.mountContentTag(columns.get(string));

            if (contentTag.length() > 0)
            {
                contentTag = column2Tag + " (" + contentTag + ")";
            }

            contentTag = "\n\t\t<" + column2Title + ">" + contentTag + "</" + column2Title + ">";
            
            String endTag = "\n\t</" + tagName + ">";

            xmlFile.append(openTag);
            xmlFile.append(nameTag);
            xmlFile.append(contentTag);
            xmlFile.append(endTag);
        }

        xmlFile.append("\n</" + title + ">");

        return xmlFile.toString();
    }

    /**
     * Mount the content tag in the traceability matrix.
     * 
     * @param content The content of the cell.
     * @return A string representing the second cell.
     */
    private String mountContentTag(List<String> content)
    {
        String result = "";

        for (String string : content)
        {
            result += string + ", ";
        }
        if (result.length() > 0)
        {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }
    
    
    /**
     * 
     * Replaces characters in the test case that are not Unicode standard.
     * @param testCases the test cases to be modified
     */
    private void replaceNotUnicodeCharacters(List<TextualTestCase> testCases){
        for (TextualTestCase textualTestCase : testCases)
        {
            textualTestCase.setExecutionType(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getExecutionType()));
            textualTestCase.setRegressionLevel(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getRegressionLevel()));
            textualTestCase.setDescription(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getDescription()));
            textualTestCase.setObjective(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getObjective()));
            textualTestCase.setRequirements(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getRequirements()));
            textualTestCase.setUseCaseReferences(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getUseCaseReferences()));
            
            textualTestCase.setSetups(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getSetups()));
            textualTestCase.setInitialConditions(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getInitialConditions()));
            textualTestCase.setNote(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getNote()));
            textualTestCase.setFinalConditions(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getFinalConditions()));
            textualTestCase.setCleanup(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getCleanup()));
            textualTestCase.setStatus(TCGUtil.replaceNotUnicodeCharacters(textualTestCase.getStatus()));
            
            for (TextualStep textualStep : textualTestCase.getSteps())
            {
                textualStep.setAction(TCGUtil.replaceNotUnicodeCharacters(textualStep.getAction()));
                textualStep.setSystemState(TCGUtil.replaceNotUnicodeCharacters(textualStep.getSystemState()));
                textualStep.setResponse(TCGUtil.replaceNotUnicodeCharacters(textualStep.getResponse()));
            }
        }
    }
}
