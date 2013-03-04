/*
 * @(#)HTMLUseCaseGenerator.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jan 15, 2008   LIBnn34008   Initial creation.
 * dhq348    Jan 24, 2008   LIBnn34008   Rework after inspection LX229625.
 * tnd783    Apr 07, 2008   LIBpp71785   Added refresh capability.
 * tnd783    Jul 21, 2008   LIBpp71785   Rework after inspection LX285039.  
 * dwvm83	 Nov 21, 2008				 Change in method createContents to include the TC Setup.
 */
package com.motorola.btc.research.target.pm.editors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiEditorInput;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.Flow;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.StepId;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.errors.EmptyUseCaseFieldError;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * This class was created in order to simplify the class HTMLGenerator. It is used only for
 * generating HTML code for UseCases. The created HTML also highlights the errors that occurs in the
 * use case.
 */
public class UseCaseHTMLGenerator extends HTMLGenerator
{

    /**
     * The color of the highlighted errors.
     */
    private static final String HIGHLIGHT_ERROR_COLOR = "FFA0A0";

    /**
     * This method is used to create and open (in the default browser) the use case representation
     * as HTML.
     * 
     * @param usecase The use case to be presented
     * @param feature The feature that contains <code>usecase</code>.
     * @param errors The list of errors found in the project. These errors are used to highlight the
     * use case.
     * @throws IOException The exception is launched if an error occur while creating the HTML file
     */
    public void openUseCaseInBrowser(UseCase usecase, Feature feature, List<Error> errors)
            throws IOException
    {
        File file = createHTMLFromUseCase(usecase, feature, errors);
        openFileInBrowser(file);
    }

    /**
     * Opens <code>selectedUseCase</code> inside the tool. This method works well on WinXP and its
     * behavior is not stable in other OS.
     * 
     * @param selectedUseCase The user selected use case.
     * @param selectedFeature The feature that contains the selected use case.
     * @param errors The list of errors found in the project. These errors are used to highlight the
     * use case.
     * @throws IOException Thrown when it is not possible to read the HTML file.
     * @throws PartInitException Thrown when it is not possible to view the use case.
     */
    public void openUseCaseInsideTool(UseCase selectedUseCase, Feature selectedFeature,
            List<Error> errors) throws IOException, PartInitException
    {
        File file = createHTMLFromUseCase(selectedUseCase, selectedFeature, errors);
        PathEditorInput pathEditorInput = new PathEditorInput(
                new Path("file:///" + file.getAbsolutePath()));
        
        pathEditorInput.setUseCase(selectedUseCase);
        pathEditorInput.setFeature(selectedFeature);
        
        IEditorInput[] editorInput = new IEditorInput[] {pathEditorInput};
        MultiEditorInput input = new MultiEditorInput(new String[]{selectedUseCase.getName()}, editorInput);
    
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        
        openHTMLEditor(input, page);
    }


    /**
     * Creates a new HTML file with the information retrieved from the <code>UseCase</code> object
     * passed as parameter.
     * 
     * @param usecase The <code>UseCase</code> object.
     * @param feature The feature that contains <code>usecase</code>.
     * @param errors The list of errors found in the project. These errors are used to highlight the
     * use case.
     * @return The created file.
     * @throws IOException In case of any error during the creation.
     */
    public File createHTMLFromUseCase(UseCase usecase, Feature feature, List<Error> errors)
            throws IOException
    {
        File file = createOutputFile(feature.getId() + "_"+ usecase.getId());
        createContents(file, usecase, feature, errors);
        return file;
    }

    /**
     * Method responsible for create the entire HTML file.
     * 
     * @param file The file where the HTML content is written.
     * @param usecase The use case object that contains the content to be read.
     * @param feature The feature that contains <code>usecase</code>.
     * @param errors The list of errors found in the project. These errors are used to highlight the
     * use case.
     * @throws IOException In case of any problem during the file writing.
     */
    private void createContents(File file, UseCase usecase, Feature feature, List<Error> errors)
            throws IOException
    {
        FileOutputStream out = new FileOutputStream(file);

        out.write(createHeader("Use Case Preview").getBytes());
        out.write(createElementDescription(usecase.getId(), usecase.getName(),
                usecase.getDescription()).getBytes());
        out.write(createRequirements(usecase).getBytes());
        out.write(createSetup(usecase.getSetup()).getBytes());
        out.write(createFlows(usecase, feature, errors).getBytes());
        out.write(createTail().getBytes());
        out.close();
    }

    /**
     * Creates the HTML table that lists the related requirements.
     * 
     * @param usecase The use case from which the requirements are retrieved.
     * @return A string containing the requirements section in HTML.
     */
    private String createRequirements(UseCase usecase)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<br><h3>Related requirement(s)</h3><br>\n");
        buffer
                .append("<table align=\"center\" border=\"1\" width=\"40%\" cellspacing=\"0\" cellpadding=\"3\"\n");
        buffer.append("<tr><td align=\"center\"><b>Requirements Codes</b></td></tr>\n");

        for (String requirement : usecase.getAllRelatedRequirements())
        {
            buffer.append("<tr><td>" + ((requirement == null) ? "<center>-</center>" : requirement)
                    + "</td></tr>\n");
        }
        buffer.append("</table>\n");
        return buffer.toString();
    }

    /**
     * Create the flows section. It names the first flow as the main flow and the remaining as
     * alternatives.
     * 
     * @param usecase The use case that contains the flows to be displayed.
     * @param feature The feature that contains <code>usecase</code>
     * @param errors The list of errors found in the project. These errors are used to highlight the
     * use case.
     * @return A string containing the HTML flows section.
     */
    private String createFlows(UseCase usecase, Feature feature, List<Error> errors)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<br><h3>Main Flow</h3>");
        int flowIndex = 0;
        for (Flow flow : usecase.getFlows())
        {
            if (flowIndex == 1)
            {
                buffer.append("<br><h3>Alternative Flow(s)</h3>");
            }

            createFromToStepsTable(new Object[] { usecase, feature }, errors, buffer, flow);
            createFlowTableHeader(buffer);

            int stepIndex = 0;
            for (FlowStep step : flow.getSteps())
            {
                StepId stepId = step.getId();
                String tmp = stepId.getStepId();
                String app = "<tr>\n";

                /* THE STEP ID */
                if (hasDuplicatedStepIdError(stepId, errors))
                {
                    app += "<td bgcolor=\"" + HIGHLIGHT_ERROR_COLOR + "\">";
                }
                else if (hasEmptyUseCaseFieldError(stepId, errors, EmptyUseCaseFieldError.STEP_ID,
                        stepIndex, flowIndex))
                {
                    app += "<td bgcolor=\"" + HIGHLIGHT_ERROR_COLOR + "\">";
                }
                else
                {
                    app += "<td>";
                }
                app += ((tmp.length() == 0) ? "&nbsp;" : tmp) + "</td>\n";

                /* THE USER ACTION */
                tmp = step.getUserAction();
                if (hasEmptyUseCaseFieldError(stepId, errors, EmptyUseCaseFieldError.STEP_ACTION,
                        stepIndex, flowIndex))
                {
                    app += "<td bgcolor=\"" + HIGHLIGHT_ERROR_COLOR + "\">";
                }
                else
                {
                    app += "<td>";
                }
                app += ((tmp.length() == 0) ? "&nbsp;" : tmp) + "</td>\n";

                tmp = step.getSystemCondition();
                app += "<td>" + ((tmp.length() == 0) ? "&nbsp;" : tmp) + "</td>\n";

                /* THE SYSTEM RESPONSE */
                tmp = step.getSystemResponse();
                if (hasEmptyUseCaseFieldError(stepId, errors, EmptyUseCaseFieldError.STEP_RESPONSE,
                        stepIndex, flowIndex))
                {
                    app += "<td bgcolor=\"" + HIGHLIGHT_ERROR_COLOR + "\">";
                }
                else
                {
                    app += "<td>";
                }
                app += ((tmp.length() == 0) ? "&nbsp;" : tmp) + "</td>\n" + "</tr>\n";
                buffer.append(app);
                stepIndex++;
            }
            createFlowTableTail(buffer);
            flowIndex++;
        }
        return buffer.toString();
    }

    /**
     * Creates a table with the steps (from/to).
     * 
     * @param feature The feature that contains the steps.
     * @param usecase The use case that contains the steps.
     * @param steps The steps to be displayed.
     * @param errors The list of errors in the project.
     * @param tableTitle The title of the table (from/to).
     * @return A string containing the table.
     */
    private String createStepsTable(Feature feature, UseCase usecase, Set<StepId> steps,
            List<Error> errors, String tableTitle)
    {
        String result = "";
        for (StepId fromStepId : steps)
        {
            if (hasFromOrToStepError(fromStepId, errors))
            {
                result += "<td>,&nbsp;</td><td bgcolor=\"" + HIGHLIGHT_ERROR_COLOR + "\">";
            }
            else
            {
                result += "<td>,&nbsp;</td><td>";
            }
            result += fromStepId.getContextString(feature.getId(), usecase.getId()) + "</td>";
        }
        if (result.length() > 0)
        {
            result = result.substring(16);
        }
        result = "<table cellpadding=0 cellspacing=0 height=\"100%\"><tr><td>" + tableTitle
                + ":&nbsp;</td>" + result;
        result += "</tr></table>";

        return result;
    }

    /**
     * @see com.motorola.btc.research.target.pm.editors.HTMLGenerator#createStepsContent(java.lang.Object[],
     * java.util.List, com.motorola.btc.research.target.common.ucdoc.Flow)
     */
    @Override
    protected String[] createStepsContent(Object[] artifacts, List<Error> errors, Flow flow)
    {
        String[] result = new String[] { "", "" };

        if (artifacts.length == 2 && (artifacts[0] instanceof UseCase)
                && (artifacts[1] instanceof Feature))
        {
            UseCase usecase = (UseCase) artifacts[0];
            Feature feature = (Feature) artifacts[1];
            result[0] = createStepsTable(feature, usecase, flow.getFromSteps(), errors,
                    "From Steps");
            result[1] = createStepsTable(feature, usecase, flow.getToSteps(), errors, "To Steps");
        }

        return result;
    }
}
