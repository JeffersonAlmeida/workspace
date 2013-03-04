/*
 * @(#)StepId.java
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
 * wdt022   Jan 04, 2007    LIBkk11577   Initial creation.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications.
 * wdt022   Jan 12, 2007    LIBkk11577   Modifications due to inspection LX128956. 
 * dhq348   Feb 21, 2008    LIBoo89937   Updated toString() method.
 * dhq348   Feb 22, 2008    LIBoo89937   Rework after inspection LX246152.
 * mcms     Dec 15, 2009                 Added set method.
 */
package br.ufpe.cin.target.common.ucdoc;

import br.ufpe.cin.target.common.util.UseCaseUtil;

/**
 * Represents an Id of a flow step.
 * 
 * <pre>
 * CLASS:
 * It encapsulates three fields: the feature Id, the use case Id and the flow step Id.
 * The class also contains two step Id objects representing the START step and the END step.
 */
public class StepId
{
    /** Constant that means the START step */
    public static final StepId START = new StepId("START", "START", "START");

    /** Constant that means the END step */
    public static final StepId END = new StepId("END", "END", "END");

    /** Feature Id */
    private String featureId;

    /** Use Case Id */
    private String useCaseId;

    /** Flow step Id */
    private String stepId;

    /**
     * Builds a StepId object from the given ids (feature, use case and step).
     * 
     * @param featureId The feature Id
     * @param useCaseId The use case Id
     * @param stepId The step Id
     */
    public StepId(String featureId, String useCaseId, String stepId)
    {
        this.featureId = featureId.trim();
        this.useCaseId = useCaseId.trim();
        this.stepId = stepId.trim();
    }

    /**
     * Get method for <code>featureId</code> attribute.
     * 
     * @return The feature Id
     */
    public String getFeatureId()
    {
        return this.featureId;
    }

    /**
     * Get method for <code>stepId</code> attribute.
     * 
     * @return The step Id
     */
    public String getStepId()
    {
        return this.stepId;
    }

    /**
     * Get method for <code>useCaseId</code> attribute.
     * 
     * @return The use case Id
     */
    public String getUseCaseId()
    {
        return this.useCaseId;
    }

    /**
     * Returns the concatenated Ids: <feature_id>#<use_case_id>#<step_id>. In case of START or END
     * step ids, the returned string is only <step_id>.
     */
    public String toString()
    {
        return UseCaseUtil.getStepIdAsString(this);
    }

    /**
     * Returns the string id according to the context. The string is built based on the location of
     * the step in the document. So, it counts on the local feature id and use case id.
     * 
     * @param featureId The local feature id.
     * @param useCaseId The local use case id.
     * @return The step id string according to the context.
     */
    public String getContextString(String featureId, String useCaseId)
    {
        String result = null;

        if (this == START || this == END)
        {
            result = this.stepId;
        }
        else if (this.featureId.equals(featureId))
        {
            if (this.useCaseId.equals(useCaseId))
            {
                result = this.stepId;
            }
            else
            {
                result = this.useCaseId + "#" + this.stepId;
            }
        }
        else
        {
            result = this.featureId + "#" + this.useCaseId + "#" + this.stepId;
        }

        return result;
    }

    /**
     * Compares this object with another <code>StepId</code> object. Each field must be equal.
     */
    
    public boolean equals(Object obj)
    {
        boolean equal = false;
        if (obj instanceof StepId)
        {
            equal = this.toString().equals(obj.toString());
        }
        return equal;
    }

    /**
     * Returns the hash code for the step id. The hash code is obtained from the
     * <code>toString()</code> string.
     */
    
    public int hashCode()
    {
        return this.toString().hashCode();
    }
    
    //INSPECT mcms method added to be used at class TableEditingSupport
    public void setStepId(String id){
    	this.stepId = id;
    }

}
