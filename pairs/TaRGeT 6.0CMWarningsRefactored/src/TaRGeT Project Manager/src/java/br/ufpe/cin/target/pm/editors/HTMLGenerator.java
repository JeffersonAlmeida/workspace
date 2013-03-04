/*
 * @(#)HTMLGenerator.java
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
 * wra050   Aug 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * wdt022   Jan 16, 2007    LIBkk11577   Javadoc and some corrections.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * wdt022   Feb 23, 2007    LIBkk16317   Fix error of special character (quotation marks, etc.).
 * dhq348   May 23, 2007    LIBmm25975   Changed the EditorInput from PathEditorInput to MultiEditorInput.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348   Jun 27, 2007    LIBmm63120   Added the highlight of errors.
 * dhq348   Jul 04, 2007    LIBmm63120   Rework after inspection LX188519.
 * dhq348   Aug 17, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 * dhq348   Oct 15, 2007    LIBnn34008   Added methods openInterruptionInsideTool() and openInterruptionInBrowser().
 * dhq348   Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 * tnd783   Apr 07, 2008    LIBpp71785   Changed generated html file name and added open editor check
 * tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039. 
 * dwvm83	Nov 21, 2008				 Added method createSetup.	
 */

package br.ufpe.cin.target.pm.editors;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiEditorInput;

import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.errors.DuplicatedStepIdError;
import br.ufpe.cin.target.pm.errors.EmptyUseCaseFieldError;
import br.ufpe.cin.target.pm.errors.Error;
import br.ufpe.cin.target.pm.errors.InvalidStepIdReferenceError;


/**
 * This class abstracts over the process used to create an use case HTML representation and to open
 * it in the default browser or in the main view.
 */
public abstract class HTMLGenerator
{

    /** The location of the HTML file */
    protected static final String HTML_OUTPUT_FOLDER = FileUtil.getUserDirectory()
            + FileUtil.getSeparator() + "temp";

    /**
     * Executes the default browser to open the given <code>file</code>.
     * 
     * @param file The file to be displayed.
     * @throws IOException Thrown if it is not possible to execute the browser.
     */
    protected void openFileInBrowser(File file) throws IOException
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().open(file);
        }
        else
        {
            throw new UnsupportedOperationException(
                    "Desktop API not supported on the current platform");
        }
    }

    /**
     * Opens a given file in the default editor. The method creates a <code>MultiEditorInput</code>
     * with the file to be displayed and its title (editor id).
     * 
     * @param file The HTML file to be displayed.
     * @param editorId The id of the editors (the title to be displayed in the editor).
     * @throws PartInitException Thrown if it is not possible to open the DefaultEditor.
     */
    protected void openElementInsideTool(File file, String[] editorId) throws PartInitException
    {

        IEditorInput[] editorInput = new IEditorInput[] { new PathEditorInput(new Path("file:///"
                + file.getAbsolutePath())) };
        IEditorInput input = new MultiEditorInput(editorId, editorInput);

        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

        openHTMLEditor(input, page);
    }

    /**
     * Opens a given input in the work bench page. The method checks if there is any editor opened
     * with the given input. If so, it only activates the editor.
     * 
     * @param input The editor input to be displayed.
     * @param page The page in which the new editor will be opened.
     * @throws PartInitException Thrown if it is not possible to open the editor.
     */
    protected void openHTMLEditor(IEditorInput input, IWorkbenchPage page) throws PartInitException
    {
        boolean editorAlreadyOpen = false;
        IEditorReference[] editors = page.getEditorReferences();
        for (IEditorReference editor : editors)
        {
            if (editor.getName().equals(input.getName()))
            {
                editorAlreadyOpen = true;
                page.activate(editor.getEditor(false));
                break;
            }
        }

        if (!editorAlreadyOpen)
        {
            page.openEditor(input, UseCaseViewer.ID);
        }
    }

    /**
     * Creates the HTML file header.
     * 
     * @param title The title to be set in the page.
     * @return The HTML header.
     */
    protected String createHeader(String title)
    {
        return "<html>\n<head>\n<title>" + title + "</title>\n</head>\n<body>";
    }

    /**
     * Creates the end of the HTML document.
     * 
     * @return The last section of the HTML document.
     */
    protected String createTail()
    {
        return "</body>\n</html>";
    }

    /**
     * Appends in <code>buffer</code> the HTML code related to the from steps and to steps table.
     * 
     * @param artifacts The artifacts which can be UseCase/Features.
     * @param errors Any existing errors in the artifacts.
     * @param buffer The buffer where the HTML code will be appended.
     * @param flow The flow which description, from steps and to steps will be retrieved.
     */
    protected void createFromToStepsTable(Object[] artifacts, List<Error> errors,
            StringBuffer buffer, Flow flow)
    {
        String[] steps = createStepsContent(artifacts, errors, flow);
        String fromSteps = steps[0];
        String toSteps = steps[1];
        buffer.append("<br>Description: " + flow.getDescription());
        buffer.append("<table width=\"100%\" cellpadding=0 cellspacing=0><tr><td valign=top>");
        buffer.append(fromSteps);
        buffer.append("</tr><tr><td>");
        buffer.append(toSteps);
        buffer.append("</tr></table>");
    }

    /**
     * Creates the from and to steps content.
     * 
     * @param artifacts The artifacts which can be UseCase/Features.
     * @param errors Any existing errors in the artifacts.
     * @param buffer The buffer where the HTML code will be appended.
     * @param flow The flow which description, from steps and to steps will be retrieved.
     * @return A string array where the element at 0 is the content of the from steps and the
     * element at 1 is the content of the element to steps.
     */
    protected abstract String[] createStepsContent(Object[] artifacts, List<Error> errors, Flow flow);

    /**
     * Appends a flow table header to the given <code>buffer</code>.
     * 
     * @param buffer The buffer where the HTML code will be appended.
     */
    protected void createFlowTableHeader(StringBuffer buffer)
    {
        /* Flow Table */
        buffer
                .append("<table align=\"center\" border=\"1\" width=\"75%\" cellspacing=\"0\" cellpadding=\"3\"\n");
        buffer.append("<tr>\n" + "<td align=\"center\"><b>Step Id</b></td>\n"
                + "<td width=\"30%\" align=\"center\"><b>User Action</b></td>\n"
                + "<td width=\"30%\" align=\"center\"><b>System State</b></td>\n"
                + "<td width=\"30%\" align=\"center\"><b>System Response</b></td>\n" + "</tr>\n");
    }

    /**
     * Creates the flow table tail.
     * 
     * @param buffer The buffer where the HTML code will be appended.
     */
    protected void createFlowTableTail(StringBuffer buffer)
    {
        buffer.append("</table><br>");
    }

    /**
     * Verifies if there is an InvalidStepIdReferenceError in <code>errors</code> which contains
     * <code>stepId</code>.
     * 
     * @param stepId The step id to be searched.
     * @param errors The errors in which <code>stepId</code> will be searched.
     * @return <code>true</code> if <code>errors</code> contain a InvalidStepIdReferenceError
     * with <code>stepId</code> or <code>false</code> otherwise.
     */
    public boolean hasFromOrToStepError(StepId stepId, List<Error> errors)
    {
        boolean result = false;

        for (Error error : errors)
        {
            if (error instanceof InvalidStepIdReferenceError)
            {
                InvalidStepIdReferenceError invError = (InvalidStepIdReferenceError) error;
                if (invError.getStepId().equals(stepId))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Verifies if there is a DuplicatedStepIdError in <code>errors</code> which contains
     * <code>stepId</code>.
     * 
     * @param stepId The step id to be searched.
     * @param errors The errors in which <code>stepId</code> will be searched.
     * @return <code>true</code> if <code>errors</code> contain a DuplicatedStepIdError with
     * <code>stepId</code> or <code>false</code> otherwise.
     */
    public boolean hasDuplicatedStepIdError(StepId stepId, List<Error> errors)
    {
        boolean result = false;
        for (Error error : errors)
        {
            if (error instanceof DuplicatedStepIdError)
            {
                DuplicatedStepIdError duperror = (DuplicatedStepIdError) error;
                if (duperror.getStepId().equals(stepId))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Verifies if there is a EmptyUseCaseFieldError in <code>errors</code> which contains
     * <code>stepId</code>.
     * 
     * @param stepId The step id to be searched.
     * @param errors The errors in which <code>stepId</code> will be searched.
     * @param field The field being verified.
     * @param stepIndex The step index used to located the specific step.
     * @param flowIndex The flow index used to located the specific step.
     * @return <code>true</code> if <code>errors</code> contain a EmptyUseCaseFieldError with
     * <code>stepId</code> or <code>false</code> otherwise.
     */
    public boolean hasEmptyUseCaseFieldError(StepId stepId, List<Error> errors, String field,
            int stepIndex, int flowIndex)
    {
        boolean result = false;

        for (Error error : errors)
        {
            if (error instanceof EmptyUseCaseFieldError)
            {
                EmptyUseCaseFieldError emptyError = (EmptyUseCaseFieldError) error;
                StepId flowStepId = null;
                if (emptyError.getFlowStep()!= null) {
                	flowStepId = emptyError.getFlowStep().getId();
                	if (flowStepId.equals(stepId))
                	{
                		if (field.equals(emptyError.getFieldName())
                				&& stepIndex == emptyError.getStepIndex()
                				&& flowIndex == emptyError.getFlowIndex())
                		{
                			result = true;
                			break;
                		}
                	}
                }
            }

        }
        return result;
    }

    /**
     * Creates the HTML that contains the element id, name and its description.
     * 
     * @param elementId The id of the element.
     * @param elementName The name of the element.
     * @param elementDescription The description of the element.
     * @return A string containing an HTML for the title and description of the given element.
     */
    protected String createElementDescription(String elementId, String elementName,
            String elementDescription)
    {
        String result = "<br>\n" + "<h2>" + elementId + " - " + elementName + "</h2>\n";

        result += "<h3>Description:</h3> <br>" + elementDescription + "<p>";

        return result;
    }

    /**
     * Creates the steps table.
     * 
     * @param tableTitle The title of the table.
     * @param tableContent The content of the table.
     * @return A string with the HTML code of the table.
     */
    protected String createStepsTable(String tableTitle, String tableContent)
    {
        String result = "<table cellpadding=0 cellspacing=0 height=\"100%\"><tr><td>" + tableTitle
                + ":&nbsp;</td>" + tableContent + "</tr></table>";
        return result;
    }

    /**
     * This method is used when creating the HTML files. It formats the name of the file to be
     * created.
     * 
     * @param name The name that will be formatted.
     * @return The recently created file.
     * @throws IOException Thrown when it is not possible to create the HTML file.
     */
    protected File createOutputFile(String name) throws IOException
    {
        // Replaces all punctuation and white spaces by '_'
        name = name.replaceAll("[\\p{Punct}\\p{Blank}]+", "_");
        String fileName = name + ".html";

        File dir = new File(HTML_OUTPUT_FOLDER);
        File file = new File(HTML_OUTPUT_FOLDER + FileUtil.getSeparator() + fileName);

        dir.mkdir();
        file.createNewFile();

        return file;
    }
    
    /**
     * Creates the HTML that contains the use case Setup.
     * 
     * @param setup The use case setup.
     * @return A string containing an HTML for the setup.
     */
    protected String createSetup(String setup)
    {
        String result = "<h3>Setup:</h3> <br>" + setup + "<p>";

        return result;
    }

}
