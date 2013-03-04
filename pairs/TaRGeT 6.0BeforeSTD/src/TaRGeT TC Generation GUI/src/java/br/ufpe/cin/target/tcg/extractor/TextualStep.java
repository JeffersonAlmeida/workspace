/*
 * @(#)TextualStep.java
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
 * wdt022    Jan 8, 2007    LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 */
package br.ufpe.cin.target.tcg.extractor;

import br.ufpe.cin.target.common.ucdoc.StepId;

/**
 * Represents a textual test case step.
 * 
 * <pre>
 * CLASS:
 * Comprehends the step actions and its respective expect results.
 * 
 * RESPONSIBILITIES:
 * 1) Store the information of a unique test case step.
 * 
 * COLABORATORS:
 * 1) It is used by the TextualTestCase class
 * 
 * USAGE:
 * not applicable
 */
public class TextualStep
{

    /** Represents the value of a system state that is undefined */
    public static final String UNDEFINED_STATE = "";

    /** Represents the system state that allows the user to perform the action */
    private String systemState;

    /** Represents the user action */
    private String action;

    /** Represents the system response to the user action */
    private String response;

    /** Represents the id of the step */
    private StepId id;

    /**
     * Builds a textual step from an action and its response.
     * 
     * @param action The user action that stimulates the system under test.
     * @param response The system response to the user action.
     * @param id The step id.
     */
    public TextualStep(String action, String response, StepId id)
    {

        this.systemState = UNDEFINED_STATE;
        this.action = action;
        this.response = response;
        this.id = id;
    }

    /**
     * Builds a textual step from a system state, an action and its response.
     * 
     * @param systemState The system condition that allows the user to perform the given action of
     * the test step.
     * @param action The user action that stimulates the system under test.
     * @param response The system response to the user action. *
     * @param id The step id.
     */
    public TextualStep(String systemState, String action, String response, StepId id)
    {
        this.systemState = systemState;
        this.action = action;
        this.response = response;
        this.id = id;
    }

    /**
     * Get method for <code>action</code> attribute
     * 
     * @return The user action
     */
    public String getAction()
    {
        return this.action;
    }

    /**
     * Get method for <code>response</code> attribute
     * 
     * @return The system response to the user action
     */
    public String getResponse()
    {
        return this.response;
    }

    /**
     * Get method for the <code>systemState</code> attribute.
     * 
     * @return Returns the system state.
     */
    public String getSystemState()
    {
        return systemState;
    }

    /**
     * Get method for the <code>id</code> attribute.
     * 
     * @return Returns the id.
     */
    public StepId getId()
    {
        return id;
    }
    //INSPECT Lais - Adição de metodos set para atributos.
    /**
     * Sets the systemState value.
     *
     * @param systemState The systemState to set.
     */
    public void setSystemState(String systemState)
    {
        this.systemState = systemState;
    }

    /**
     * Sets the action value.
     *
     * @param action The action to set.
     */
    public void setAction(String action)
    {
        this.action = action;
    }

    /**
     * Sets the response value.
     *
     * @param response The response to set.
     */
    public void setResponse(String response)
    {
        this.response = response;
    }

    /**
     * Sets the id value.
     *
     * @param id The id to set.
     */
    public void setId(StepId id)
    {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    
    public boolean equals(Object obj)
    {
        boolean eq = false;
        
        if (obj instanceof TextualStep)
        {
            TextualStep textualStep = (TextualStep) obj;
            
            if(isEquals(textualStep.action, this.action) &&
                    isEquals(textualStep.id, this.id) &&
                    isEquals(textualStep.response, this.response) &&
                    isEquals(textualStep.systemState, this.systemState))
            {
                eq = true;
            }
        }
        
        return eq;
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
