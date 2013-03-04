/*
 * @(#)UseCase.java
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
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 02, 2006    LIBkk11577   Added id checking.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications.
 * dhq348   Aug 02, 2007    LIBmm42774   Added method getFlowStepById.
 * dhq348   Nov 26, 2007    LIBoo10574   Added the setup attribute.
 * dhq348   Feb 2,  2009    LIBoo10574   Added the equals method.
 */
package br.ufpe.cin.target.common.ucdoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.target.common.util.StringsUtil;

/**
 * <pre>
 * CLASS:
 * Represents a use case requirement artifact.
 *
 * RESPONSIBILITIES:
 * Encapsulates the set of events flow of specific use case.
 * 
 * USAGE:
 * </pre>
 */
public class UseCase implements Comparable<UseCase>
{
    /** The use case id */
    private String id;

    /** The use case name */
    private String name;

    /** The use case description */
    private String description;

    /** The flows that compose the use case */
    private List<Flow> flows;

    /**
     * The use case setup
     */
    private String setup;

    /**
     * This constructor initiates all use case attributes.
     * 
     * @param id The use case id
     * @param name The use case name
     * @param description The use case description
     * @param flowList The flows that compose the use case
     * @param setup The use case setup.
     */
    public UseCase(String id, String name, String description, List<Flow> flowList, String setup)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.flows = new ArrayList<Flow>(flowList);
        this.setup = setup;
    }

    /**
     * Get method for <code>flows</code> attribute.
     * 
     * @return The flow list.
     */
    public List<Flow> getFlows()
    {
        return new ArrayList<Flow>(flows);
    }

    /**
     * It returns a set containing of all requirements related to the use case flows.
     * 
     * @return A set containing all related requirements.
     */
    public Set<String> getAllRelatedRequirements()
    {
        Set<String> result = new HashSet<String>();
        for (Flow flow : this.getFlows())
        {
            result.addAll(flow.getAllRelatedRequirements());
        }
        return result;
    }

    /**
     * Get method for <code>description</code> attribute.
     * 
     * @return The artifact description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get method for <code>name</code> attribute.
     * 
     * @return the artifact name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get method for <code>id</code> attribute.
     * 
     * @return Returns the id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns the ids of all steps that are contained in the use case.
     * 
     * @return A list with all step ids.
     */
    public List<StepId> getActualStepIds()
    {
        List<StepId> result = new ArrayList<StepId>();

        for (Flow flow : this.flows)
        {
            result.addAll(flow.getActualStepIds());
        }

        return result;
    }

    /**
     * Returns the ids of all steps that are referred in the use case.
     * 
     * @return A list with all referred step ids.
     */
    public Set<StepId> getReferredStepIds()
    {
        Set<StepId> result = new HashSet<StepId>();

        for (Flow flow : this.flows)
        {
            result.addAll(flow.getReferredStepIds());
        }

        return result;
    }

    /**
     * Returns the use case ID and the name.
     */
    
    public String toString()
    {
        return this.id + " - " + this.name;
    }

    /**
     * Iterates over the flows checking if a flow step with the given <code>stepId</code> exists.
     * 
     * @param stepId The id been searched.
     * @return The found flow step or <code>null</code> if none exists.
     */
    public FlowStep getFlowStepById(StepId stepId)
    {
        FlowStep result = null;

        for (Flow flow : this.flows)
        {
            result = flow.getFlowStepById(stepId);
            if (result != null)
            {
                break;
            }
        }

        return result;
    }

    /**
     * @return The setup of the current use case.
     */
    public String getSetup()
    {
        return setup;
    }
    
    //INSPECT - Lais: Adição do método compare
    
    public int compareTo(UseCase uc)
    {
        if (!(uc instanceof UseCase))
        {
            throw new ClassCastException("A Feature object is expected.");
        }

        // this.uc < uc
        int result = -1;
        
        // this.uc == uc
        if (StringsUtil.compareNatural(this.id, uc.id) == 0)
        {
            result = 0;
        }

        // this.uc > uc
        else if (StringsUtil.compareNatural(this.id, uc.id) > 0)
        {
            result = 1;
        }

        return result;
    }

    /**
     * Sets the id value.
     *
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Sets the name value.
     *
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the description value.
     *
     * @param description The description to set.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Sets the flows value.
     *
     * @param flows The flows to set.
     */
    public void setFlows(List<Flow> flows)
    {
        this.flows = flows;
    }

    /**
     * Sets the setup value.
     *
     * @param setup The setup to set.
     */
    public void setSetup(String setup)
    {
        this.setup = setup;
    }
    
    //INSPECT mcms this method was created to allow adding a flow in the list of flows from 
    //Use Case to be used in the class SaveAction 
    public void addFlow(Flow flow)
    {
        this.flows.add(flow);
    }
    
    //INSPECT mcms this method was created to allow adding a flow in the list of flows from 
    //Use Case to be used in the class SaveAction 
    public void removeFlow(Flow flow)
    {
        this.flows.remove(flow);
    }
    
    
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof UseCase)
        {
            result = this.id.equals(((UseCase) obj).id);
        }
        return result;
        
    }
}
