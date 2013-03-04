/*
 * @(#)Flow.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Aug 15, 2006    none  		 Initial creation.
 * wdt022   Nov 25, 2006    LIBkk11577   Refatoring due to new functionalities.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications.
 * wdt022   Jan 12, 2007    LIBkk11577   Modifications due to inspection LX128956.
 * dhq348   Aug 02, 2007    LIBmm42774   Added method getFlowStepById.
 * dwvm83	Sep 18, 2008	LIBqq51204	 Added method equals	
 */
package com.motorola.btc.research.target.common.ucdoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * CLASS:
 * Represents a use case flow. 
 *
 * RESPONSIBILITIES:
 * Encapsulates a list of <code>FlowStep</code> objects (representing the steps), the 
 * flow description, and the set of <i>from</i> and <i>to</i> steps related to the flow.
 * 
 * USAGE:
 * </pre>
 */
public class Flow
{
    /** List of steps */
    private List<FlowStep> steps;

    /** List of step ids which the flow comes from */
    private Set<StepId> fromSteps;

    /** List of step ids which the flow goes to */
    private Set<StepId> toSteps;

    /** Flow Description */
    private String description;

    /**
     * This constructor initiates the step list, the from/to step references and the flow
     * description.
     * 
     * @param steps The list of steps that composes the flow.
     * @param fromSteps The set of references to the <i>from</i> steps.
     * @param toSteps The set of references to the <i>to</i> steps.
     * @param description The description of the flow.
     */
    public Flow(List<FlowStep> steps, Set<StepId> fromSteps, Set<StepId> toSteps, String description)
    {
        this.steps = new ArrayList<FlowStep>(steps);
        this.fromSteps = new LinkedHashSet<StepId>(fromSteps);
        this.toSteps = new LinkedHashSet<StepId>(toSteps);
        this.description = description;
    }

    /**
     * Get method for <code>fromSteps</code> attribute.
     * 
     * @return Returns the fromSteps set.
     */
    public Set<StepId> getFromSteps()
    {
        return new LinkedHashSet<StepId>(fromSteps);
    }

    /**
     * Get method for <code>steps</code> attribute.
     * 
     * @return Returns the step list.
     */
    public List<FlowStep> getSteps()
    {
        return new ArrayList<FlowStep>(steps);
    }

    /**
     * Get method for <code>toSteps</code> attribute.
     * 
     * @return Returns the toSteps set.
     */
    public Set<StepId> getToSteps()
    {
        return new LinkedHashSet<StepId>(toSteps);
    }

    /**
     * Get method for <code>description</code> attribute.
     * 
     * @return Returns the flow description.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the set of all requirements related to each flow step.
     * 
     * @return The set of all related requirements.
     */
    public Set<String> getAllRelatedRequirements()
    {
        Set<String> result = new HashSet<String>();
        for (FlowStep step : this.steps)
        {
            result.addAll(step.getRelatedRequirements());
        }
        return result;
    }

    /**
     * Returns the ids of all steps that are contained in the flow.
     * 
     * @return A list with all step ids.
     */
    public List<StepId> getActualStepIds()
    {
        List<StepId> result = new ArrayList<StepId>();

        for (FlowStep step : this.steps)
        {
            result.add(step.getId());
        }

        return result;
    }

    /**
     * Returns the ids of all steps that are contained in the flow.
     * 
     * @return A list with all step ids.
     */
    public Set<StepId> getReferredStepIds()
    {
        Set<StepId> result = new LinkedHashSet<StepId>();

        result.addAll(this.fromSteps);
        result.addAll(this.toSteps);

        return result;
    }

    /**
     * Check if the given step does exist in the flow.
     * 
     * @param step The step to be searched inside the flow.
     * @return <i>True</i> if the flow contains the given step.
     */
    public boolean hasStepWithId(StepId stepId)
    {
        boolean found = false;

        for (Iterator iter = this.getSteps().iterator(); iter.hasNext() && !found;)
        {
            FlowStep stepMember = (FlowStep) iter.next();

            if (stepMember.getId().equals(stepId))
            {
                found = true;
            }

        }

        return found;
    }

    /**
     * Iterates over the list of flow steps and verifies if it contains the given
     * <code>stepId</code>.
     * 
     * @param stepId The id to be searched.
     * @return The found flow step or <code>null</code> if none is found.
     */
    public FlowStep getFlowStepById(StepId stepId)
    {
        FlowStep result = null;

        for (FlowStep flowStep : this.steps)
        {
            if (flowStep.getId().equals(stepId))
            {
                result = flowStep;
                break;
            }
        }

        return result;
    }
    
    /**
     * Compares this object with another <code>Flow</code> object. The fromSteps and toSteps must be equal
     */
    @Override
    
    public boolean equals(Object obj)
    {
    	if (obj != null && obj instanceof Flow)
    		return equalsTo(obj) && equalsFrom(obj);
    	else
    		return false;
    }
    
   
    private boolean equalsTo(Object obj)
    {
    	Set<StepId> toStepsObj = ((Flow)obj).getToSteps();
        if (this.toSteps.size() == toStepsObj.size()) {
        	Iterator iteratorObject = toStepsObj.iterator();
        	Iterator iteratorThisFlow = this.toSteps.iterator();
	        while(iteratorObject.hasNext()){
	        	if(!iteratorObject.next().equals(iteratorThisFlow.next()))
	        		return false;
	        	}
	        return true;
	    } else
	    	return false;
    }
    
  
    private boolean equalsFrom(Object obj)
    {
    	Set<StepId> fromStepsObj = ((Flow)obj).getFromSteps();
        if (this.fromSteps.size() == fromStepsObj.size()) {
        	Iterator iteratorObject = fromStepsObj.iterator();
        	Iterator iteratorThisFlow = this.fromSteps.iterator();
	        while(iteratorObject.hasNext()){
	        	if(!iteratorObject.next().equals(iteratorThisFlow.next()))
	        		return false;
	        	}
	        return true;
	    } else
	    	return false;
    }
}
