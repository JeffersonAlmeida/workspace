/*
 * @(#)FlowStep.java
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
 * wra050   Aug 15, 2006    none  		 Initial creation.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications and refactorings.
 * dhq348   Aug 23, 2007    LIBmm42774   Added method equals().
 * dhq348   Nov 26, 2007    LIBoo10574   Added the setup attribute.
 * dhq348   Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 * fsf2     Jun 29, 2009                 Changed method equals().
 * mcms     Dec 15, 2009                 Added set methods.
 */
package br.ufpe.cin.target.common.ucdoc;

import java.util.HashSet;
import java.util.Set;

/**
 * <pre>
 * CLASS:
 * Represents a step in use case flow.
 *
 * RESPONSIBILITIES:
 * Encapsulates a single step in use case flow
 * 
 * </pre>
 */
//INSPECT - Laís. Exclusão do atributo setup
public class FlowStep
{

    /** The id of the step. */
    private StepId id;

    /** The user action. */
    private String userAction;

    /** The system condition that allows the user action to be perfomed. */
    private String systemCondition;

    /** The system response to the user action. */
    private String systemResponse;

    /** A string containing related requirements. */
    private Set<String> relatedRequirements;
    
    /**
     * Constructor that does not receive any setup data.
     * 
     * @param id Step id
     * @param action Step user action
     * @param condition Step system condition
     * @param response Step system responses
     * @param relatedRequirements The requirements that are related to this step
     */
    public FlowStep(StepId id, String action, String condition, String response,
            Set<String> relatedRequirements)
    {
        this.id = id;
        this.userAction = action;
        this.systemCondition = condition;
        this.systemResponse = response;
        this.relatedRequirements = new HashSet<String>(relatedRequirements);
    }

    /**
     * Get method for <code>id</code> attribute.
     * 
     * @return Returns the flow step id.
     */
    public StepId getId()
    {
        return id;
    }

    /**
     * Get method for <code>systemCondition</code> attribute.
     * 
     * @return Returns the condition string.
     */
    public String getSystemCondition()
    {
        return systemCondition;
    }

    /**
     * Get method for <code>systemResponse</code> attribute.
     * 
     * @return Returns the system response string.
     */
    public String getSystemResponse()
    {
        return systemResponse;
    }

    /**
     * Get method for <code>userAction</code> attribute.
     * 
     * @return Returns the user action string.
     */
    public String getUserAction()
    {
        return userAction;
    }

    /**
     * Get method for <code>relatedRequirements</code> attribute.
     * 
     * @return Returns the the requirements related to the step.
     */
    public Set<String> getRelatedRequirements()
    {
        return new HashSet<String>(relatedRequirements);
    }

    
    //INSPECT mcms method added to be used at class TableEditingSupport
    public void setUserAction(String userAction){
    	this.userAction = userAction;
    }

    //INSPECT mcms method added to be used at class TableEditingSupport
    public void setSystemCondition(String systemCondition){
    	this.systemCondition = systemCondition;
    }
    
    //INSPECT mcms method added to be used at class TableEditingSupport
    public void setSystemResponse(String systemResponse){
    	this.systemResponse = systemResponse;
    }
    
    /**
     * It calls the id attribute toString.
     */
    public String toString()
    {
        return this.getId().toString();
    }

	/**
     * Compares two steps based on their ids.
     */
    
    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof FlowStep)
        {
            FlowStep newFlowStep = (FlowStep) obj;
            
            if(isEquals(this.id, newFlowStep.id) &&
                    isEquals(this.userAction, newFlowStep.userAction) &&
                    isEquals(this.systemCondition, newFlowStep.systemCondition) &&
                    isEquals(this.systemResponse, newFlowStep.systemResponse) &&
                    isEquals(this.relatedRequirements, newFlowStep.relatedRequirements))
            {
                result = true;
            }
        }

        return result;
    }
    
    private boolean isEquals(Object obj1, Object obj2)
    {
        boolean retorno = true;
        
        if(obj1 == null ^ obj2 == null)
        {
            retorno = false;
        }
        else
        {
            retorno = (obj1 != null) ? obj1.equals(obj2) : true;
        }
        
        return retorno;
    }
    
}
