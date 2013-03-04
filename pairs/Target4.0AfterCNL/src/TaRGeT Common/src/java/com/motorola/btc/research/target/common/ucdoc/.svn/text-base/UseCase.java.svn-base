/*
 * @(#)UseCase.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 02, 2006    LIBkk11577   Added id checking.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications.
 * dhq348   Aug 02, 2007    LIBmm42774   Added method getFlowStepById.
 * dhq348   Nov 26, 2007    LIBoo10574   Added the setup attribute.
 */
package com.motorola.btc.research.target.common.ucdoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class UseCase
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
}
