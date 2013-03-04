/*
 * @(#)InputDocumentTemplateExtension.java
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
 * fsf2      Mar 27, 2009                Initial creation.
 */
package br.ufpe.cin.target.tc4input;

import java.io.File;
import java.text.DecimalFormat;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.exception.TargetImportTemplateException;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InformationTestCase;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InputDocumentTemplateExtension;
import br.ufpe.cin.target.tcg.util.ExcelExtractor;


/**
 * This class implements the Input Document Template Extension methods.
 * 
 * <pre>
 * CLASS:
 * The class receives a list of test cases and writes a excel file according to the Test central standard.
 * 
 * RESPONSIBILITIES:
 * Imports the header information from a Test Central 4 suite test. 
 * 
 * COLABORATORS:
 * 
 * USAGE:
 * InputDocumentTemplateExtension inputDocumentTemplateExtension = new InputDocumentTemplateExtension();
 * inputDocumentTemplateExtension.getInformationTestCase();
 */
public class InputTemplateTestCentral4 extends InputDocumentTemplateExtension
{
    public InputTemplateTestCentral4()
    {
    }

    
    public InformationTestCase getInformationTestCase() throws TargetImportTemplateException
    {
        InformationTestCase informationTestCase = new InformationTestCase();
        
        String filePath = this.getFilePath();
        
        if(filePath == null)
        {
            informationTestCase = new InformationTestCase("", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "");
        }
        else
        {
            try
            {
                File file = new File(filePath);
                
                String resultFileName = this.getResultFileName(file.getName());
                
                informationTestCase.setFileName(resultFileName);
                
                ExcelExtractor excelExtractor = new ExcelExtractor();

                excelExtractor.inicializeExcelFile(file);

                excelExtractor.setSheet(0);

                excelExtractor.nextRow();

                informationTestCase.setSuiteName(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setDateExported(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setDescription(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setCreatedBy(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setLastModifiedBy(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setSuiteStatus(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setOwnershipGroup(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setReadPermissions(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setWritePermissions(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setAttachedFileDesc(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setProcedure(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                informationTestCase.setNotes(excelExtractor.getCellString(4));

                excelExtractor.nextRow();
                excelExtractor.nextRow();
                excelExtractor.nextRow();
                excelExtractor.nextRow();

                informationTestCase.setStatus(excelExtractor.getCellString(2));
                informationTestCase.setRegressionLevel(excelExtractor.getCellString(3));
                informationTestCase.setExecutionMethod(excelExtractor.getCellString(4));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                
                throw new TargetImportTemplateException();
            }
        }

        return informationTestCase;
    }

    //INSPECT: Felype - Adicionado método para resolver o nome do arquivo caso já exista um com mesmo nome.
    /**
     * Comment for method.
     * @param fileName 
     * @return
     */
    private String getResultFileName(String fileName)
    {
        int possibleSuite = 2;
        
        String testSuiteDir = ProjectManagerExternalFacade.getInstance().getCurrentProject()
        .getTestSuiteDir();

        testSuiteDir += FileUtil.getSeparator();

        if (TestCaseGeneratorController.getInstance().getOutputDocumentExtension() == null)
        {
            TestCaseGeneratorController.getInstance().initOutputExtensionsList();
        }

        File file = new File(testSuiteDir + fileName);
        
        if (file.exists())
        {
            fileName = fileName.substring(0, fileName.length()-4);
        }

        // look for a test suite name that is different from the existent ones
        while (file.exists())
        {
            file = new File(testSuiteDir + fileName + "[" + possibleSuite++ + "]"
                    + TestCaseGeneratorController.getInstance().getOutputDocumentExtension().getExtensionFile());
        }
        
        return file.getName();
    }

}
