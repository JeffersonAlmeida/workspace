/*
 * @(#)InputExtension.java
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
 * fsf2      Feb 27, 2009                Initial creation.
 * fsf2      Apr 01, 2009                Implementation of different output plugins.
 * lmn3		 May 14, 2009				 Changes due code inspection
 */
package br.ufpe.cin.target.tcg.extensions.output;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InformationTestCase;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InputDocumentTemplateExtension;
import br.ufpe.cin.target.tcg.extensions.inputTemplate.InputDocumentTemplateExtensionFactory;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * This class represents the extension point for different kinds of generation output documents. This way, 
 * this class must be extended by clients that need to implement a new output document extension to the
 * TaRGeT system.
 * 
 * USAGE:
 * Generates the test suite output file.
 * </pre>
 */
//INSPECT - Lais Extração dos metodos de geração de traceability matrixes para a classe TraceabilityMatrixGenerator
public abstract class OutputDocumentExtension
{

    /** The test case list */
    protected List<TextualTestCase> testCases;

    // INSPECT: Adicionei atributos para o import template.
    /**
     * The test suite imported information.
     */
    protected InformationTestCase informationTestCase;

    /**
     * The import template extension.
     */
    private InputDocumentTemplateExtension inputTemplateExtension;

    /**
     * Method that should be used to write the output file.
     * 
     * @param file The file where the information will be written.
     * @throws IOException Exception launched in case of something goes wrong during the file
     * writing
     * @throws TargetException
     */
    public File writeTestCaseDataInFile(File file) throws IOException
    {
        try
        {
            this.importDocumentTemplate();
        }
        catch (TargetException e)
        {
            MessageDialog.openError(
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), Properties
                            .getProperty("error"), Properties.getProperty("error_import_template"));

            this.informationTestCase = new InformationTestCase("", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "");
        }
        
        return file;
    }

    private void importDocumentTemplate() throws TargetException
    {
        Collection<InputDocumentTemplateExtension> inputExtensionsList = InputDocumentTemplateExtensionFactory
                .inputDocumentTemplatetExtensions();

        if (this.inputTemplateExtension == null)
        {
            for (InputDocumentTemplateExtension inputTemplateExtension : inputExtensionsList)
            {
                this.inputTemplateExtension = inputTemplateExtension;
            }
        }

        this.informationTestCase = new InformationTestCase("", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "");

        if (this.inputTemplateExtension != null)
        {
            if (this.inputTemplateExtension.openDialog())
            {
                this.informationTestCase = this.inputTemplateExtension.getInformationTestCase();
            }
        }
    }

    /**
     * @param testCases
     */
    public void setTestCases(List<TextualTestCase> testCases)
    {
        this.testCases = testCases;
    }

    /**
     * Gets the file extension of the plugin output format
     * 
     * @return the file extension
     */
    public abstract String getExtensionFile();
    
    /**
     * Returns the test cases list
     * 
     * @return the test cases list
     */
    public List<TextualTestCase> getTestCases()
    {
        return this.testCases;
    }

    /**
     * Gets the informationTestCase value.
     *
     * @return Returns the informationTestCase.
     */
    public InformationTestCase getInformationTestCase()
    {
        return informationTestCase;
    }

    /**
     * Sets the informationTestCase value.
     *
     * @param informationTestCase The informationTestCase to set.
     */
    public void setInformationTestCase(InformationTestCase informationTestCase)
    {
        this.informationTestCase = informationTestCase;
    }
}
