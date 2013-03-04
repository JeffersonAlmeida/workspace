/*
 * @(#)EmptyUseCaseFieldError.java
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
 * wdt022    Mar 6, 2007    LIBll29572   Initial creation.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Jul 03, 2007    LIBmm63120   Modification according to CR.
 * dhq348   Jul 03, 2007    LIBmm63120   Rework after inspection LX188519.
 * dwvm83	Nov 25, 2008				 Changes to include empty To/From steps error.	
 */
package br.ufpe.cin.target.pm.errors;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;

/**
 * This class represents an error of empty use case field. The fields that are covered by this error
 * are step id, step action and step response.
 * 
 * <pre>
 * CLASS:
 * 
 *  This class extends the <code>UseCaseError</code>, so encapsulates
 *  all its attributes. It also has the attribute <code>fieldName</code>
 *  that represents the field that is empty.
 */
public class EmptyUseCaseFieldError extends UseCaseError
{
	
	/** Name of the use case id field */
    public static final String USECASE_ID = "use case id";
    
	
	/** Name of the step id field */
    public static final String TO_STEP = "to step";
    
    /** Name of the step id field */
    public static final String FROM_STEP = "from step";
    
    /** Name of the step id field */
    public static final String STEP_ID = "step id";

    /** Name of the step action field */
    public static final String STEP_ACTION = "step action";

    /** Name of the step response field */
    public static final String STEP_RESPONSE = "step response";

    /** The name of the field that is empty */
    private String fieldName;

    /** The flow step with the empty field */
    private FlowStep flowStep;

    /** This is the index of the flow where the error occurred */
    private int flowIndex;

    /** This is the index of the step where the error occurred */
    private int stepIndex;

    /**
     * Constructor. It builds the feature and the path of the document that contains the use case
     * with empty field. It also receives the name of the empty field and the flow where the empty
     * field is located.
     * 
     * @param feature The feature that contains the use case with error.
     * @param useCase The use case with error.
     * @param flowStep The flow with the empty step id.
     * @param flowIndex The flow index where the error occurred.
     * @param stepIndex The step index where the error occurred.
     * @param documentName The path of the document that contains the use case.
     * @param field The name of the field that is empty.
     */
    public EmptyUseCaseFieldError(Feature feature, UseCase useCase, FlowStep flowStep,
            int flowIndex, int stepIndex, String documentName, String field)
    {

        super(feature, useCase, documentName);

        this.fieldName = field;
        this.flowStep = flowStep;
        this.flowIndex = flowIndex;
        this.stepIndex = stepIndex;
    }

    /**
     * Returns the description of the empty use case field error.
     * 
     * @return The error description.
     */
    public String getDescription()
    {
        return "There is an empty " + this.fieldName + " field in the use case \""
                + this.useCase.getId() + " - " + this.useCase.getName() + "\".";
    }

    /**
     * Returns the id of the feature concatenated with its name.
     * 
     * @return The error location inside the resource.
     */
    public String getLocation()
    {
        return this.feature.getId() + " - " + this.feature.getName();
    }

    /**
     * Returns The path of the resource that contains the error.
     * 
     * @return The document path.
     */
    public String getPath()
    {
        return this.documentName;
    }

    /**
     * Returns the name of the resource that contains the error.
     * 
     * @return The name of the document.
     */
    public String getResource()
    {
        return FileUtil.getFileName(this.documentName);
    }

    /**
     * Gets the type of the error.
     * 
     * @return Returns the value <code>Error.ERROR</code>.
     */
    public int getType()
    {
        return Error.ERROR;
    }

    /**
     * Gets the missing field name.
     * 
     * @return Returns the value of attribute <code>fieldName</code>.
     */
    public String getFieldName()
    {
        return this.fieldName;
    }

    /**
     * Gets the flow step where the missing field is.
     * 
     * @return Returns the value of attribute <code>flowStep</code>.
     */
    public FlowStep getFlowStep()
    {
        return flowStep;
    }

    /**
     * Gets the flow index.
     * 
     * @return Returns the value of attribute <code>flowIndex</code>.
     */
    public int getFlowIndex()
    {
        return flowIndex;
    }

    /**
     * Gets the step index.
     * 
     * @return Returns the value of attribute <code>stepIndex</code>.
     */
    public int getStepIndex()
    {
        return stepIndex;
    }

}
