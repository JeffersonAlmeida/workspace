/*
 * @(#)Purpose.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wfo007    Jun 04, 2007    LIBmm42774   Initial creation.
 * wfo007    Aug 16, 2007    LIBmm42774   Added the method getValidFlowSteps.
 * wfo007    Aug 24, 2007    LIBmm42774   Modifications after inspection LX201876.
 * wln013    Mar 20, 2008    LIBpp56482   Changes the class name and concept. Now it is not an iterator.
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.StepId;

/**
 * <pre>
 * CLASS:
 * A class that represents a Test Purpose. A Test Purpose establishes a 
 * 	criteria in order to obtain a test objective. When using a test purpose in a model,
 * 	the purpose is used to prune the model in order to generate a suite 
 * 	containing all the specified FlowSteps.
 *
 * RESPONSIBILITIES:
 *  
 *
 * COLABORATORS: 
 *	1) Uses FlowStep.
 *
 * USAGE:
 *	TestPurpose purpose = new TestPurpose(new List<FlowStep>())
 *
 * </pre>
 */

public class TestPurpose
{
    /**
     * FlowStep that represents any other transition in an LTS (or combination of transitions) used
     * in the purpose matching with paths from an LTS. Has the same function as the symbol '*' in
     * Regular expressions.
     */
    public final static FlowStep STAR_STEP = new FlowStep(new StepId("*", "*", "*"), "*", "*", "*",
            new HashSet<String>());

    /**
     * The list of FlowSteps that represent the Test Purpose.
     */
    private List<FlowStep> testPurpose;

    /**
     * Creates a test purpose with a list of flow steps.
     * 
     * @param purpose A list of FlowSteps used to specify the test purpose.
     */
    public TestPurpose(List<FlowStep> purpose)
    {
        this.testPurpose = new ArrayList<FlowStep>();
        if (purpose.size() > 1)
        {
            removeDuplicatedStarSteps(purpose);
        }
        else
        {
            testPurpose.add(purpose.get(0));
        }
    }

    /**
     * Verifies if there are duplicated star steps into the test purpose and remove them.
     * 
     * @param purpose The test purpose that will be verified.
     */
    private void removeDuplicatedStarSteps(List<FlowStep> purpose)
    {
        Iterator<FlowStep> it = purpose.iterator();
        FlowStep previousFlowStep = null;
        FlowStep flowStep = null;
        do
        {
            previousFlowStep = flowStep;
            flowStep = it.next();
            if (STAR_STEP.equals(flowStep))
            {
                if (!flowStep.equals(previousFlowStep))
                {
                    testPurpose.add(flowStep);
                }
            }
            else
            {
                testPurpose.add(flowStep);
            }

        } while (it.hasNext());

    }

    /**
     * Gets the steps from the test purpose.
     * 
     * @return The test purpose steps.
     */
    public List<FlowStep> getSteps()
    {
        return testPurpose;
    }

    /**
     * Retrieves an string representation of the test purpose.
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (FlowStep value : testPurpose)
        {
            sb.append("; ");
            if (value.equals(STAR_STEP))
            {
                sb.append("*");
            }
            else
            {
                sb.append(value);
            }
        }
        if (testPurpose.size() > 0)
        {
            sb = new StringBuffer(sb.substring(2));
        }
        return sb.toString();
    }
}
